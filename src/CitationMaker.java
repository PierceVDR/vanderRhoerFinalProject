import org.json.JSONObject;
import org.json.JSONArray;

import java.util.Scanner;

public class CitationMaker {
    private static final Scanner SCAN = new Scanner(System.in);

    public void start() {
        System.out.print("Enter ISBN: ");
        String isbn = SCAN.nextLine();
        JSONObject searchData = DataGetter.getData("isbn",isbn);
        JSONArray books = searchData.getJSONArray("items");
        JSONObject book = (JSONObject) books.get(0);


    }

}
