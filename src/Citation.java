import org.json.JSONObject;
import org.json.JSONArray;

import java.util.Arrays;
import java.util.Scanner;

public class Citation {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final String[] MONTHS = {"Jan.", "Feb.", "Mar.", "Apr.", "May", "June", "July", "Sept.", "Oct.", "Nov.", "Dec."};


    // API Instance Variables:
    private String query;
    private String search;


    // Data Instance Variable:
    private JSONObject bookInfo;


    // Citation Instance Variables:
    private String formattedAuthor;
    private String title;
    private String container;
    private String otherContributors;
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
    public String getOtherContributors(){return otherContributors;}
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
        search = "9780205297665"; // The example for Two Authors
        //search = "9780323857024"; // The example for Three Authors
        //search = "9780738536668"; // Should have series
        query="isbn";

        JSONObject searchData = DataGetter.getSearch(query,search);
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
        // No generateContainer
        // No generateOtherContributors
        // No generateVersion
        // No generateNumber
        generatePublisher();
        generateDateOfPublication();
        // No generateLocationOfPublisher
        // Do not generateDateOfAccess
    }

    public String generateAuthor(){
        JSONArray authors = bookInfo.getJSONArray("authors");
        int numAuthors = authors.length();


        if (numAuthors>0) {
            String authorOne = formatAuthor( (String) authors.get(0) );

            if (numAuthors==1) { // If there is one author...
                formattedAuthor = formatAuthor( authorOne );
            } else {
                String unformattedAuthorTwo = (String) authors.get(1);

                if (numAuthors==2) {  // If there are two authors...
                    formattedAuthor = authorOne +", and "+ unformattedAuthorTwo; // This is NOT a mistake: You are not supposed to put the second author's name in LastName, FirstName format
                } else { // If there are three or more authors...
                    formattedAuthor = authorOne + ", et al";
                }

            }
        }

        return getAuthor();
    }

    public String generateTitle(){
        title=bookInfo.getString("title");
        return getTitle();
    }

    public String generatePublisher(){
        publisher=bookInfo.getString("publisher");
        return getPublisher();
    }

    public String generateDateOfPublication(){
        dateOfPublication=bookInfo.getString("publishedDate");
        return getDateOfPublication();
    }

    public String generateDateOfAccess(){
        // https://www.javatpoint.com/java-get-current-date
        java.util.Date dateObj = new java.util.Date();

        String day = Integer.toString( dateObj.getDate() );
        String month = MONTHS[ dateObj.getMonth() ];
        String year = Integer.toString( 1900+dateObj.getYear() );

        return day +" "+ month +" "+ year;
    }



    private String formatAuthor(String rawAuthor) {
        // Used the following stackoverflow questions to find an easier way of doing this
        // https://stackoverflow.com/questions/12656203/how-to-add-spaces-only-between-catenated-values-using-java
        // https://stackoverflow.com/questions/11001720/get-only-part-of-an-array-in-java

        String[] words = rawAuthor.split(" ");
        int len = words.length;

        String firstNamePart = String.join(" ", Arrays.copyOfRange(words, 0, (len+1)/2));
        String lastNamePart = String.join(" ", Arrays.copyOfRange(words, (len+1)/2, len));

        return lastNamePart +", "+ firstNamePart;
    }

    public void printInfo() {
        System.out.println("BOOK INFO:");

        System.out.println("       (FORMATTED) AUTHOR: " + formattedAuthor);
        System.out.println("       TITLE: " + title);
        System.out.println("       CONTAINER " + container);
        System.out.println("       OTHER CONTRIBUTORS: " + otherContributors);
        System.out.println("       VERSION: " + version);
        System.out.println("       NUMBER: " + number);
        System.out.println("       PUBLISHER: " + publisher);
        System.out.println("       DATE OF PUBLICATION: " + dateOfPublication);
        System.out.println("       LOCATION OF PUBLISHER: " + locationOfPublisher);
        System.out.println("       DATE OF ACCESS: " + dateOfAccess);
    }

}
