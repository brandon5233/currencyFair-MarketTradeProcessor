/*
Tests the application by sending both valid and invalid JSON data.
Currently, JSON data must be supplied as a JSON array
even if it contains a single JSON object (as seen in the below).
This is done for simplicity only.
Adapting to handle both objects and arrays is pretty straightforward and can be implemented easily.
 */

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class jsonPostTests {

    @BeforeClass
    public static void beforeClass() {
        Main.main(null);
    }

    @AfterClass
    public static void afterClass() {
        Spark.stop();
    }

    /*
    Sends valid JSON data, and expects a 200 HTTP response
    NOTE: Currently, data should be a JSONArray even if it is a single object
     */
    @Test
    public void validJsonArrayRequest() throws IOException {
        String validJson = "[{\"userId\": \"134256\", \"currencyFrom\": \"EUR\", \"currencyTo\": \"GBP\"," +
                "\"amountSell\": 1000, \"amountBuy\": 747.10, \"rate\": 0.7471,\"timePlaced\" : \"24-JAN-18 10:27:44\", \"originatingCountry\" : \"FR\"}]";
        ConnectionResponse response = doRequest("POST", "/api_test", validJson);
        assertEquals(response.getResponseCode(), 200);
    }

    /*
    Sends invalid JSON data, and expects a 415 HTTP Error response (Client Error) from server
    Assertion checks if the error code was actually 415
    NOTE: Currently, data should be a JSONArray even if it is a single object
     */
    @Test
    public void invalidJsonArrayRequest() throws IOException {
        String invalidJson = "this is invalid data";

        try {
            ConnectionResponse response = doRequest("POST", "/api_test", invalidJson);
        } catch (Exception e) {
            assertEquals(e.getMessage().contains("415"), true);
        }
    }


    public ConnectionResponse doRequest(String method, String path, String params) throws IOException {
        URL url = new URL("http://0.0.0.0:4567" + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setDoOutput(true);
        connection.connect();
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(params.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        String response = IOUtils.toString(connection.getInputStream());
        int responseCode = connection.getResponseCode();
        return new ConnectionResponse(response, responseCode);
    }

}
