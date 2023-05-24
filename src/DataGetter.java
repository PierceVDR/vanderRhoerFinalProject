import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DataGetter {

    private static final String KEY = "AIzaSyBao4Y8SpShFYHJuzgBU_rPPZHlA-JSbeE";
    private static final String BASE = "https://www.googleapis.com/books/v1";
    public static JSONObject getData(String query, String search) {
        System.out.println("Getting book info...");

       String url =  "https://www.googleapis.com/books/v1/volumes?q=+"+query+":"+search+"&key="+KEY;
       //System.out.println("URL:\n" + url);

       String urlResponse = "sample text";

        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            urlResponse = response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //System.out.println();
        System.out.println(urlResponse);

        return new JSONObject(urlResponse);
    }

}