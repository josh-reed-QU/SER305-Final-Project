import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ResultWindow extends JPanel {
	public ResultWindow(ArrayList<String> results, int newDeals, String keyword) throws InterruptedException {
		super(new BorderLayout());
		JFrame frame = new JFrame("Results");
		JPanel resultPanel = new JPanel();
		resultPanel.setBackground(Color.white);
		resultPanel.setPreferredSize(new Dimension(800, 600));
		resultPanel.setSize(new Dimension(800, 600));

		JTextArea textBox = new JTextArea();
		if (!results.isEmpty()) { // if deals were found
			textBox.append("Deals on " + keyword + " found on dealsea.com\n" +
					"------------------------------------------\n\n");

			if (results.size() != newDeals) { // if there are new deals and old deals
				textBox.append("New Deals:\n");
				for (int i = results.size() - newDeals; i < results.size(); i++) {
					textBox.append(results.get(i) + "\n"); // adds every new deal's title
				}

				textBox.append("------------------------------------------\nOld Deals:\n"); // spacer

				for (int i = 0; i < results.size() - newDeals; i++) {
					textBox.append(results.get(i) + "\n"); // adds every old deal's title
				}
			}
			else { // first run
				textBox.append("New Deals:\n");
				for(String dealTitle : results) {
					textBox.append(dealTitle + "\n"); // prints all deals
				}
			}
		}

		else { // if no deals are found
			textBox.append("No Deals on " + keyword + " found.");
		}

		JScrollPane resultPane = new JScrollPane(textBox);
		resultPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		resultPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		resultPane.setSize(new Dimension(800,530));
		resultPane.setPreferredSize(new Dimension(800,530));
		resultPanel.add(resultPane, BorderLayout.NORTH);

		JButton stopButton = new JButton("Stop Searching");
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == stopButton) {
					System.exit(0);
				}
			}
		});

		resultPanel.add(stopButton, BorderLayout.SOUTH);

		frame.add(resultPanel);
		frame.setSize(800,600);
		frame.setVisible(true);
		//System.out.println("set up done");
	}


}
