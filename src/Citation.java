import org.json.JSONObject;
import org.json.JSONArray;

import java.util.Scanner;

public class Citation {
    private static final Scanner SCAN = new Scanner(System.in);


    // API Instance Variables:
    private String query;
    private String search;


    // Data Instance Variable:
    private JSONObject bookInfo;


    // Citation Instance Variables:
    private String formattedAuthor;
    private String title;
    private String container;
    private String otherContributers;
    private String version;
    private String number;
    private String publisher;
    private String dateOfPublication;
    private String locationOfPublication;
    private String dateOfAccess;


    // Settings Instance Variables:
    private boolean includeDateOfAccess;

    public Citation() {
        includeDateOfAccess=false;
    }

    public String getAuthor(){return formattedAuthor;}

    public void start() {
        System.out.print("Enter ISBN: ");
        search = SCAN.nextLine();

        //OVERRIDES FOR TESTING:
        search="9780451524935";  // 1984
        search = "9780717802418"; // Communist Manifesto
        search = "9780323857024"; // (Has Three Authors)
        query="isbn";

        JSONObject searchData = DataGetter.getData(query,search);
        JSONArray books = searchData.getJSONArray("items");
        JSONObject book = (JSONObject) books.get(0);
        bookInfo=(JSONObject) book.get("volumeInfo");

        generateAuthor();

        //System.out.println(bookInfo);
        //System.out.println(generateAuthor());
    }

    public String generateAuthor(){
        JSONArray authors = bookInfo.getJSONArray("authors");
        int numAuthors = authors.length();


        if (numAuthors>0) {
            String authorOne = formatAuthor( (String) authors.get(0) );

            if (numAuthors==1) { // If there is one author...
                formattedAuthor = formatAuthor( authorOne );
            } else {
                String authorTwo = formatAuthor( (String) authors.get(1) );

                if (numAuthors==2) {  // If there are two authors...
                    formattedAuthor = authorOne +", and "+ authorTwo;
                } else { // If there are three or more authors...
                    formattedAuthor = authorOne +", "+ authorTwo +", et al";
                }

            }
        }

        return getAuthor();
    }


    private String formatAuthor(String rawAuthor) {
        System.out.println("//PLACEHOLDER//");
        return rawAuthor;
    }

}
