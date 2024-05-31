import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Scanner;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class FrontEnd extends JPanel {
	private JTextField textField; // Text field for user input
	private boolean showText;
	private String playerName;
	private BufferedImage teamImage; // To hold the team logo
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
			int textWidth = g2.getFontMetrics().stringWidth(playerName); // Calculate the width of the text
	        int x = (getWidth() - textWidth) / 2; // Calculate the X coordinate to center the text horizontally
	        g2.drawString(playerName, x, 100); // Draw the text centered horizontally
	       
	        //if that player exists, load their team and stats
	        if (checkPlayer(playerName)) {
	        	String team = getTeam(playerName);
               loadTeamImage(team);
               g2.drawImage(teamImage, (getWidth() - teamImage.getWidth()) / 2, 150, this);
	        }
	        //else, the player does not exist
	        else {
	        	String noPlayer = "Player does not exist.";
	        	textWidth = g2.getFontMetrics().stringWidth(noPlayer); // Calculate the width of the text
		        x = (getWidth() - textWidth) / 2;
	        	g2.drawString(noPlayer, x, 400);
	        }
	       
		}
	}
	
	private void loadTeamImage(String team) {
       try {
           teamImage = ImageIO.read(new File("src/images/" + team + ".png")); // Adjust the path and format as needed
       } catch (IOException e) {
           System.err.println("Image Unavailable");
           teamImage = null;
       }
   }
	
	public boolean checkPlayer(String playerName) {
	    try {
	        Scanner scanner = new Scanner(new File("NBA STAT REAL - Sheet1.csv"));
	        while (scanner.hasNextLine()) {
	            String[] row = scanner.nextLine().split(","); // Split by commas
	            // lowercase and trim name
	            String player = row[1].trim().toLowerCase();
	            if (player.equals(playerName.toLowerCase())) { // find the player
	                scanner.close(); // Close the scanner before returning
	                return true;
	            }
	        }
	        scanner.close(); // Close the scanner if the player is not found
	        return false;
	    } catch (FileNotFoundException e) {
	        System.err.println("Error");
	        return false;
	    }
	}
	
	public String getTeam(String playerName) {
	    try {
	        Scanner scanner = new Scanner(new File("NBA STAT REAL - Sheet1.csv"));
	        String team = null;
	        while (scanner.hasNextLine()) {
	            String[] row = scanner.nextLine().split(","); // Split by commas
	            // lowercase and trim name
	            String player = row[1].trim().toLowerCase();
	            if (player.equals(playerName.toLowerCase())) { // find the player
	                team = row[2]; //get team
	                return team;
	            }
	        }
	        scanner.close(); // Close the scanner if the player is not found
	        return team;
	    } catch (FileNotFoundException e) {
	        System.err.println("Error");
	        return null;
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
