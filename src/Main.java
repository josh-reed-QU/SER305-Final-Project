import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * SER305 - Final Project - Deals App
 * By: Josh Reed, Maddie Badalamente, Jake Conrad, Sadjell Mamon, Sammi Spinner
 *
 */

public class Main {

    public static String keyword; // user inputted search keyword
    public static int cycleTime; // user inputted cycle time
    public static List<String> dealTitles; // list to hold the new and old deal titles
    public static int numResults; // number of search results
    public static int numPages; // number of results pages

    public static void main(String[] args) throws IOException {
        // TODO: swing pop up box to receive user input

        // temporary user input
        System.out.print("Enter a keyword to search: ");
        Scanner userInput = new Scanner(System.in);
        keyword = userInput.nextLine();

        dealTitles = new ArrayList<>();
        getSearchDetails(keyword);
        System.out.println("Number of results: " + numResults +
                ", Number of Pages: " + numPages); // Test Line; compare to website
        findDeals(keyword);

        // TODO: swing pop up box with results
    }

    public static void findDeals(String keyword) {
        try {
            /*
             * TODO: loop from 0 to numPages connecting to every results page
             *
             * TODO: does it need to be on a timer??? too many connection requests too fast???
             */
            // connects to the results page
            int currPage = 0;
            while(currPage < numPages && currPage < 100) {
                Document doc = Jsoup.connect("https://dealsea.com/search?n=" + currPage + "0&q=" + keyword).get();

                Elements deals = doc.getElementsByClass("dealcontent");
                if (!deals.isEmpty()) { // if the page has results
                    for (Element deal : deals) { // loops through every deal on the results page
                        String dealText = deal.child(0).text();
                        // checks for expired label
                        Elements expiredLabels = deal.getElementsByClass("colr_red xxsmall");
                        if (expiredLabels.isEmpty()) { // if the deal is not expired
                            dealTitles.add(dealText); // stores the deal's text
                        }
                    }
                }

                currPage++;
            }
                if (!dealTitles.isEmpty()) { // if deals were found
                    System.out.println("Deals on " + keyword + " found on dealsea.com");

                    for (String text : dealTitles) {
                        System.out.println(text); //prints every deal's title
                    }
                }
                else {
                    System.out.println("No Deals on " + keyword + " found.");
                }

        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void getSearchDetails(String keyword) {
        try {
            // connects to the results page
            Document doc = Jsoup.connect("https://dealsea.com/search?n=0&q=" + keyword).get();
            // gets the number of results and pages
            Element resultsTable = doc.getElementById("fp-deals");
            // gets the number of results from the search result line; ex: "0 to 10 of 142"
            if (resultsTable != null) {
                Element searchResultDetails = resultsTable.select("table").get(0);
                Elements rows = searchResultDetails.select("tr");
                String searchResult = rows.get(0).select("td").get(1).text();
                String[] resultStrings = searchResult.split(" ");
                numResults = Integer.parseInt(resultStrings[4]);
                // calculates number of pages
                double result = numResults / 10.0;
                numPages = (int) Math.ceil(result);
            }
            else {
                numResults = 0;
                numPages = 0;
            }
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
