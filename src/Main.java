import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * SER305 - Final Project - Deals App
 * By: Josh Reed, Maddie Badalamente, Jake Conrad, Sadjell Mamon, Sammi Spinner
 *
 * HTML Parsing Test Class - Josh Reed
 * Receives search parameter from user, returns the title of the deals on the first results page
 */

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.print("Enter a keyword to search: ");
        Scanner userInput = new Scanner(System.in);
        String keyword = userInput.nextLine();

        printDeals(keyword);
    }

    public static void printDeals(String keyword) {
        try {
            Document doc = Jsoup.connect("https://dealsea.com/search?n=0&q=" + keyword).get();
            Element body = doc.body();
            Element resultsTable = body.getElementById("fp-deals");

            if(resultsTable != null) { //if the search has results
                ArrayList<Element> deals = resultsTable.getElementsByClass("dealbox");
                for (Element dealCard : deals) {
                    ArrayList<Element> dealContents = dealCard.getElementsByClass("dealcontent");
                    for (Element dealContent : dealContents) {
                        System.out.print("Deal Title:\n" + dealContent.child(0).text());
                        ArrayList<Element> expiredLabel = dealContent.getElementsByClass("colr_red xxsmall");
                        if(!expiredLabel.isEmpty())
                            System.out.println(" - Expired\n");
                        else
                            System.out.println("\n");
                    }
                }
            }
            else
                System.out.println("There are no deals on " + keyword);
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
