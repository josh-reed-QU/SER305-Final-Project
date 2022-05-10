import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SecondWindow extends JPanel {
	public SecondWindow(ArrayList<String> results ) throws InterruptedException {
		JFrame frame = new JFrame("Results");
		JPanel resultPanel = new JPanel();
		resultPanel.setBackground(Color.white);
		resultPanel.setPreferredSize(new Dimension(500, 500));
		resultPanel.setSize(new Dimension(500, 500));
		JLabel resultLabel = new JLabel(results + " searching");
		resultPanel.add(resultLabel, BorderLayout.NORTH);
		frame.add(resultPanel);

		frame.setSize(420,420);
		frame.setLayout(null);
		frame.setVisible(true);
		System.out.println("set up done");
	}



}
