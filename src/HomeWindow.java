import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeWindow extends JPanel {
	public static String keyword; // user inputted search keyword
	public static int cycleTime; // user inputted cycle time
	public static List<String> dealTitles; // list to hold the new and old deal titles
	public static int numResults; // number of search results
	public static int numPages; // number of results pages
	public static int newDeals; // number of new deals

	public HomeWindow() {
		super(new BorderLayout());
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(500, 500));
		this.setSize(new Dimension(500, 500));

		JLabel welcomeLabel = new JLabel("Welcome to Dealsea!!! Discover Amazing Deals!!!");
		this.add(welcomeLabel, BorderLayout.NORTH);
		JPanel infoPanel = new JPanel(new GridLayout(2, 2));

		JLabel keyWordLabel = new JLabel("Enter a keyword to search for:");
		JTextField wordField = new JTextField(1);
		JLabel timeLabel = new JLabel("Enter a time frequency to check for deals:");
		JTextField timeField = new JTextField(1);

		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == submitButton) {
					keyword = wordField.getText();
					//time = timeField.getText();
					try {
						run();
					} catch (InterruptedException ex) {
						throw new RuntimeException(ex);
					}
					System.out.println("in home, just passed " + keyword);
				}
			}
		});

		infoPanel.add(keyWordLabel);
		infoPanel.add(wordField);
		infoPanel.add(timeLabel);
		infoPanel.add(timeField);
		this.add(infoPanel, BorderLayout.CENTER);
		this.add(submitButton, BorderLayout.SOUTH);

	}

	public void run() throws InterruptedException {
		dealTitles = new ArrayList<>();
		int loopCount = 0;
		System.out.println("Running algorithm!, the deal is " + keyword);
		cycleTime = 1;
		while (loopCount < 2) {
			getSearchDetails(keyword);
			System.out.println("Number of results: " + numResults +
					", Number of Pages: " + numPages); // Test Line; compare to website
			//findDeals(keyword);
			new SecondWindow(keyword + " searching!!!");
			loopCount++;
		}

		// if results found - new SecondWindow(array for results
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
			newDeals = 0;
			while (currPage < numPages && currPage < 100) {
				Document doc = Jsoup.connect("https://dealsea.com/search?n=" + currPage + "0&q=" + keyword).get();

				Elements deals = doc.getElementsByClass("dealcontent");
				if (!deals.isEmpty()) { // if the page has results
					for (Element deal : deals) { // loops through every deal on the results page
						String dealText = deal.child(0).text();
						// checks for expired label
						Elements expiredLabels = deal.getElementsByClass("colr_red xxsmall");
						if (expiredLabels.isEmpty()) { // if the deal is not expired
							if (!dealTitles.contains(dealText)) {
								newDeals++;
								dealTitles.add(dealText); // stores the deal's text
							}
						}
					}
				}

				currPage++;
			}
			if (!dealTitles.isEmpty()) { // if deals were found
				System.out.println("Deals on " + keyword + " found on dealsea.com");

				if (dealTitles.size() != newDeals) {
					System.out.println("------------------------------------------");
				}

				for (int i = dealTitles.size() - newDeals; i < dealTitles.size(); i++) {
					System.out.println(dealTitles.get(i)); //prints every new deal's title
				}
			} else {
				System.out.println("No Deals on " + keyword + " found.");
			}

		} catch (IOException e) {
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
			} else {
				numResults = 0;
				numPages = 0;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
}