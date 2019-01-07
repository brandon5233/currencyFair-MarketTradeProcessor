/*
Basic Http Tests for the application.
Checks if GET request works at localhost/hello.
Checks if Invalid requests are handled.
 */

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;
import spark.utils.IOUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class basicHttpTest {
    @BeforeClass
    public static void beforeClass() {
        Main.main(null);
    }



    @AfterClass
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void httpGetRequestTest()throws IOException{
        ConnectionResponse response = doRequest("GET", "/hello");
        assertEquals(200, response.getResponseCode());
        assertEquals("Hello World", response.getResponse());
    }

    @Test(expected=java.io.FileNotFoundException.class)
    public void invalidAddressTest() throws IOException {
        ConnectionResponse response = doRequest("GET", "/randomInvalidAddress");
    }

    public ConnectionResponse doRequest(String method, String path) throws IOException {
        URL url = new URL("http://0.0.0.0:4567" + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setDoOutput(true);
        connection.connect();
        String response = IOUtils.toString(connection.getInputStream());
        int responseCode = connection.getResponseCode();
        return new ConnectionResponse(response, responseCode);
    }




}
