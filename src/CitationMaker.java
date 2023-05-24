import java.util.Scanner;

public class CitationMaker {
    private static final Scanner SCAN = new Scanner(System.in);

    public void start() {
        System.out.print("Enter ISBN: ");
        String isbn = SCAN.nextLine();
        DataGetter.getData("isbn",isbn);
    }

}
