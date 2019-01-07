/*
The Main class handles incoming requests.
Incoming requests are expected to be Arrays of JSON objects (even if it consists of a single object).
Each JSON object looks like this:

{"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP",
"amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471,
"timePlaced" : "24-JAN-18 10:27:44", "originatingCountry" : "FR"}

If the incoming array fits on the buffer, it is sent to the MessageConsumer class for consumption.
Else an error response is sent back to the client.

The /hello Get path serves as a means to check the connection to the server.
The notfound() path handles unspecified paths.
 */

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.eclipse.jetty.websocket.api.Session;
import spark.Response;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import static spark.Spark.*;

public class Main {

    private static final int bufferSize = 150;
    static CopyOnWriteArrayList<Session> sessions = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        ArrayBlockingQueue<Transaction> processorBuffer = new ArrayBlockingQueue<>(bufferSize);
        MessageConsumer messageConsumer = new MessageConsumer();
        new MessageProcessor(processorBuffer).start();

        webSocket("/infostream", WebsocketHandler.class);

        get("/hello", (req, res) -> "Hello World");

        get("/api_test", (req, res) -> "Please send JSON data via POST method to access API");

        post("/api_test", (request, response) -> {
            response.type("application/json");
            try {
                Transaction[] transactionArray = new Gson().fromJson(request.body(), Transaction[].class);
                if (processorBuffer.remainingCapacity() > transactionArray.length) {
                    response.status(200);
                    setResponse(response, "Success");
                    messageConsumer.consume(transactionArray, processorBuffer);
                } else {
                    response.status(500);
                    setResponse(response, "Server busy, please retry later. Max array size = " + bufferSize);
                }
            } catch (JsonSyntaxException ex) {
                response.status(415);
                setResponse(response, "JSONSyntaxException: Please provide an array of JSON object(s)");
            } catch (Exception e) {
                e.printStackTrace();
                response.status(500);
                setResponse(response, "Interal Server Error");
            }
            JsonObject o = new JsonParser().parse(response.body()).getAsJsonObject();
            return o;
        });

        notFound((req, res) -> {
            res.status(404);
            setResponse(res, "404 Page not found");
            res.type("text/HTML");
            return "<html><body><h1>Page not found:</h1><br><h3>Please send JSON data via POST method to access API</h3></body></html>";
        });
    }

    private static void setResponse(Response response, String message) {
        response.body("{\"data\": \"" + message + "\"}");
    }
}