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
    private String locationOfPublisher;
    private String dateOfAccess;


    // Settings Instance Variables:
    private boolean includeDateOfAccess;

    public Citation() {
        includeDateOfAccess=false;
    }

    public String getAuthor(){return formattedAuthor;}
    public String getTitle(){return title;}
    public String getContainer(){return container;}
    public String getOtherContributers(){return otherContributers;}
    public String getVersion(){return version;}
    public String getNumber(){return number;}
    public String getPublisher(){return publisher;}
    public String getDateOfPublication(){return dateOfPublication;}
    public String getLocationOfPublisher(){return locationOfPublisher;}
    public String getDateOfAccess(){return dateOfAccess;}

    public void start() {
        System.out.print("Enter ISBN: ");
        search = SCAN.nextLine();

        //OVERRIDES FOR TESTING:
        search="9780451524935";  // 1984
        search = "9780717802418"; // Communist Manifesto
        search = "9780323857024"; // (Has Three Authors)
        search = "9780738536668"; // Should have series
        query="isbn";

        JSONObject searchData = DataGetter.getData(query,search);
        JSONArray books = searchData.getJSONArray("items");
        JSONObject book = (JSONObject) books.get(0);
        bookInfo=(JSONObject) book.get("volumeInfo");

        System.out.println(book);

        generateAll();

        printInfo();
    }

    public void generateAll(){
        generateAuthor();
        generateTitle();
        // Don't generateContainer

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

    public String generateTitle(){
        title=bookInfo.getString("title");


        return title;
    }

    // Stub
    public String generateContainer(){
        container=null;

        return getContainer();
    }


    private String formatAuthor(String rawAuthor) {
        System.out.println("//PLACEHOLDER//");
        return rawAuthor;
    }

    public void printInfo() {
        System.out.println("BOOK INFO:");

        System.out.println("       (FORMATTED) AUTHOR: " + formattedAuthor);
        System.out.println("       TITLE: " + title);
        System.out.println("       CONTAINER " + container);
        System.out.println("       OTHER CONTRIBUTORS: " + otherContributers);
        System.out.println("       VERSION: " + version);
        System.out.println("       NUMBER: " + number);
        System.out.println("       PUBLISHER: " + publisher);
        System.out.println("       DATE OF PUBLICATION: " + dateOfPublication);
        System.out.println("       LOCATION OF PUBLISHER: " + locationOfPublisher);
        System.out.println("       DATE OF ACCESS: " + dateOfAccess);
    }

}
