import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class FrontEnd extends JPanel {
	private JTextField textField; // Text field for user input
	private boolean showText;
	private String playerName;
	public FrontEnd() {
		setLayout(null);
		setBackground(new Color(21, 24, 44)); // Set the background color
		textField = new JTextField();
		textField.setBounds(340, 300, 320, 40); // Set position and size of the text field
		textField.setHorizontalAlignment(JTextField.CENTER); // Center text horizontally
		// Set text color
		Color textColor = new Color(255, 255, 255);
		textField.setForeground(textColor);
		// set caret color
		textField.setCaretColor(Color.WHITE);
		// Set the background color of text box
		textField.setBackground(new Color(44, 49, 91));
		// if enter is pressed, new page
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playerName = textField.getText().trim(); // Get user input from text field
				removeAll();
				showText = true;
				repaint();
			}
		});
		add(textField);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // Call the superclass method to handle the default painting logic, including
									// clearing the background.
		Graphics2D g2 = (Graphics2D) g;
		Color c = new Color(44, 49, 91);
		g2.setColor(c);
		if (showText == false) {
			// Text
			Font font = new Font("SansSerif", Font.BOLD, 60);
			g2.setFont(font);
			g2.drawString("Enter NBA Player Here", 180, 190);
		}
		// if the user has entered a name, show it
		if (playerName != null) {
			// Text
			Font font = new Font("SansSerif", Font.BOLD, 60);
			g2.setFont(font);
			g2.drawString(playerName, 180, 100); // Display player name at the top
			// HERE WE SHOULD CENTER THE TEXT
		}
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("NBA Stat Tracker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000); // Adjust size as necessary
		FrontEnd panel = new FrontEnd();
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
}
