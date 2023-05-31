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

    public Citation(String isbn) {
        includeDateOfAccess=false;

        query="isbn";
        search=isbn;
    }

    public Citation(String isbn, boolean includeDateOfAccess) {
        this.includeDateOfAccess=includeDateOfAccess;

        query="isbn";
        search=isbn;
    }
    public void setIncludeDateOfAccess(boolean includeDateOfAccess) {
        this.includeDateOfAccess=includeDateOfAccess;
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

    public String createCitation() {
        JSONObject searchData = DataGetter.getSearch(query,search);
        JSONArray books = searchData.getJSONArray("items");
        JSONObject book = (JSONObject) books.get(0);
        bookInfo=(JSONObject) book.get("volumeInfo");

        generateAll();

        return getCitation();
    }
    public void test() {

        System.out.print("Enter ISBN: ");
        search = SCAN.nextLine();

        //OVERRIDES FOR TESTING:
        search="9780451524935";  // 1984
        search = "9780717802418"; // Communist Manifesto
        search = "9780205297665"; // The example for Two Authors
        //search = "9780323857024"; // The example for Three Authors
        //search = "9780738536668"; // Long Island, Images of America - Should have series (doesn't)
        search = "9781843172833"; // No author
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
        generateDateOfAccess();
    }

    public String generateAuthor(){
        JSONArray authors = bookInfo.getJSONArray("authors");
        int numAuthors = authors.length();


        if (numAuthors>0) {
            String authorOne = formatAuthor( (String) authors.get(0) );

            if (numAuthors==1) { // If there is one author...
                formattedAuthor = formatAuthor( authorOne );
            } else {
                if (numAuthors==2) {  // If there are two authors...
                    String unformattedAuthorTwo = (String) authors.get(1);
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
        if (bookInfo.has("publisher")) {
            publisher=bookInfo.getString("publisher");
        }
        return getPublisher();
    }

    public String generateDateOfPublication(){
        String rawDate=bookInfo.getString("publishedDate");
        int dashIdx = rawDate.indexOf("-");
        if (dashIdx!=-1) { // Date is in yyyy-mm-dd format
            dateOfPublication=rawDate.substring(0,dashIdx); // We only want year
        } else {
            dateOfPublication=rawDate;
        }
        return getDateOfPublication();
    }

    public String generateDateOfAccess(){
        // https://www.javatpoint.com/java-get-current-date
        java.util.Date dateObj = new java.util.Date();

        String day = Integer.toString( dateObj.getDate() );
        String month = MONTHS[ dateObj.getMonth() ];
        String year = Integer.toString( 1900+dateObj.getYear() );

        dateOfAccess =  day +" "+ month +" "+ year;

        return dateOfAccess;
    }



    private String formatAuthor(String rawAuthor) {
        // Used the following stackoverflow questions to find an easier way of doing this
        // https://stackoverflow.com/questions/12656203/how-to-add-spaces-only-between-catenated-values-using-java
        // https://stackoverflow.com/questions/11001720/get-only-part-of-an-array-in-java

        if (rawAuthor.contains(",") || !rawAuthor.contains(" ")) {
            return rawAuthor;
        } else {
            String[] words = rawAuthor.split(" ");
            int len = words.length;
            String firstNamePart = String.join(" ", Arrays.copyOfRange(words, 0, (len + 1) / 2));
            String lastNamePart = String.join(" ", Arrays.copyOfRange(words, (len + 1) / 2, len));

            return lastNamePart + ", " + firstNamePart;
        }
    }

    public String getCitation(){
        String txt = "";

        if (formattedAuthor!=null) {txt += formattedAuthor+". ";}

        if (title!=null) {txt += title+". ";}

        if (container!=null) {txt += container+", ";}

        if (otherContributors!=null) {txt += otherContributors+", ";}

        if (version!=null) {txt += version+", ";}

        if (number!=null) {txt += number+", ";}

        if (publisher!=null) {txt += publisher+", ";}

        if (dateOfPublication!=null) {txt += dateOfPublication+", ";}

        if (locationOfPublisher!=null) {txt += locationOfPublisher+", ";}

        txt = txt.substring(0, txt.length()-2) + "."; // Replace last comma (and space) with period.

        if (dateOfAccess!=null && includeDateOfAccess) {txt += " Accessed " + dateOfAccess + ".";}

        return txt;
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
