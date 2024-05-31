import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	private String mainStat = "STAT";
	private BufferedImage fImage; // To hold the image
	private HashMap<String, Rectangle> buttons; // Store buttons with their bounds
	private HashMap<String, Integer> nums;
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
		// Initialize buttons and their bounds
		buttons = new HashMap<>();
		buttons.put("m1n", new Rectangle(60, 305, 155, 58));
		buttons.put("pts", new Rectangle(60, 380, 155, 58));
		buttons.put("fgm", new Rectangle(60, 455, 155, 58));
		buttons.put("fga", new Rectangle(60, 530, 155, 58));
		buttons.put("fg_", new Rectangle(60, 605, 155, 58));
		buttons.put("3pm", new Rectangle(60, 680, 155, 58));
		buttons.put("3pa", new Rectangle(60, 755, 155, 58));
		buttons.put("3p_", new Rectangle(240, 680, 155, 58));
		buttons.put("ftm", new Rectangle(240, 755, 155, 58));
		buttons.put("fta", new Rectangle(420, 680, 155, 58));
		buttons.put("ft_", new Rectangle(420, 755, 155, 58));
		buttons.put("gp", new Rectangle(600, 680, 155, 58));
		buttons.put("reb", new Rectangle(600, 755, 155, 58));
		buttons.put("ast", new Rectangle(780, 305, 155, 58));
		buttons.put("stl", new Rectangle(780, 380, 155, 58));
		buttons.put("blk", new Rectangle(780, 455, 155, 58));
		buttons.put("tov", new Rectangle(780, 530, 155, 58));
		buttons.put("eff", new Rectangle(780, 605, 155, 58));
		buttons.put("oreb", new Rectangle(780, 680, 155, 58));
		buttons.put("dreb", new Rectangle(780, 755, 155, 58));
		buttons.put("return", new Rectangle(20, 30, 180, 100));
		nums = new HashMap<>();
		nums.put("m1n", 4);
		nums.put("pts", 5);
		nums.put("fgm", 6);
		nums.put("fga", 7);
		nums.put("fg_", 8);
		nums.put("3pm", 9);
		nums.put("3pa", 10);
		nums.put("3p_", 11);
		nums.put("ftm", 12);
		nums.put("fta", 13);
		nums.put("ft_", 14);
		nums.put("gp", 3);
		nums.put("reb", 17);
		nums.put("ast", 18);
		nums.put("stl", 19);
		nums.put("blk", 20);
		nums.put("tov", 21);
		nums.put("eff", 22);
		nums.put("oreb", 15);
		nums.put("dreb", 16);
		// Add mouse listener to handle button clicks
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Check if the mouse click falls within any button bounds
				for (String button : buttons.keySet()) {
					Rectangle bounds = buttons.get(button);
					if (bounds.contains(e.getPoint())) {
						// display the stat corresponding to the clicked button
						if (button.equals("return")) {
							playerName = null;
							removeAll();
							repaint();
							showText = false;
							add(textField);
							mainStat = "STAT";
						} else {
							displayStat(button);
							break;
						}
					}
				}
			}
		});
	}
	// Method to display stat corresponding to the clicked button
	private void displayStat(String button) {
		String a = getStat(playerName, nums.get(button));
		mainStat = a; // Update the mainStat
		repaint(); // Request the panel to repaint itself
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // Call the superclass method to handle the default painting logic, including
									// clearing the background.
		Graphics2D g2 = (Graphics2D) g;
		Color c = new Color(44, 49, 91);
		g2.setColor(c);
		if (showText == false) {
			// front page
			Font font = new Font("SansSerif", Font.BOLD, 60);
			g2.setFont(font);
			g2.drawString("Enter NBA Player Here", 180, 190);
			loadImage("nbalogo");
			g2.drawImage(fImage, 340, 400, this);
		}
		// if the user has entered a name, show it
		if (playerName != null) {
			// Text
			Font font = new Font("SansSerif", Font.BOLD, 60);
			g2.setFont(font);
			int textWidth = g2.getFontMetrics().stringWidth(playerName.toUpperCase()); // Calculate the width of the text
			int x = (getWidth() - textWidth) / 2; // Calculate the X coordinate to center the text horizontall
			g2.drawString(playerName.toUpperCase(), x, 100); // Draw the text centered horizontally
//			// Draw rectangles
//			g2.setColor(Color.RED); // Set the color of the rectangles
//			for (String button : buttons.keySet()) {
//				Rectangle bounds = buttons.get(button);
//				g2.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
//			}
			// if that player exists, load their team and stats
			if (checkPlayer(playerName)) {
				String team = getTeam(playerName);
				loadImage(team);
				g2.drawImage(fImage, (getWidth() - fImage.getWidth()) / 2, 200, this);
				loadImage("m1n");
				g2.drawImage(fImage, 60, 305, this);
				loadImage("pts");
				g2.drawImage(fImage, 60, 380, this);
				loadImage("fgm");
				g2.drawImage(fImage, 60, 455, this);
				loadImage("fga");
				g2.drawImage(fImage, 60, 530, this);
				loadImage("fg");
				g2.drawImage(fImage, 60, 605, this);
				loadImage("3pm");
				g2.drawImage(fImage, 60, 680, this);
				loadImage("3pa");
				g2.drawImage(fImage, 60, 755, this);
				loadImage("3p_");
				g2.drawImage(fImage, 240, 680, this);
				loadImage("ftm");
				g2.drawImage(fImage, 240, 755, this);
				loadImage("fta");
				g2.drawImage(fImage, 420, 680, this);
				loadImage("ft_");
				g2.drawImage(fImage, 420, 755, this);
				loadImage("gp");
				g2.drawImage(fImage, 600, 680, this);
				loadImage("reb");
				g2.drawImage(fImage, 600, 755, this);
				loadImage("ast");
				g2.drawImage(fImage, 780, 305, this);
				loadImage("stl");
				g2.drawImage(fImage, 780, 380, this);
				loadImage("blk");
				g2.drawImage(fImage, 780, 455, this);
				loadImage("tov");
				g2.drawImage(fImage, 780, 530, this);
				loadImage("eff");
				g2.drawImage(fImage, 780, 605, this);
				loadImage("oreb");
				g2.drawImage(fImage, 780, 680, this);
				loadImage("dreb");
				g2.drawImage(fImage, 780, 755, this);
				loadImage("return");
				g2.drawImage(fImage, -140, -170, this);
				
				
				textWidth = g2.getFontMetrics().stringWidth(mainStat); // Calculate the width of the text
				x = (getWidth() - textWidth) / 2;
				g2.drawString(mainStat, x, 175);
			}
			// else, the player does not exist
			else {
				String noPlayer = "Player does not exist.";
				textWidth = g2.getFontMetrics().stringWidth(noPlayer); // Calculate the width of the text
				x = (getWidth() - textWidth) / 2;
				g2.drawString(noPlayer, x, 400);
				loadImage("return");
				g2.drawImage(fImage, -140, -170, this);
			}
		}
	}
	private void loadImage(String input) {
		try {
			fImage = ImageIO.read(new File("src/images/" + input + ".png")); // Adjust the path and format as needed
		} catch (IOException e) {
			System.err.println("Image Unavailable");
			fImage = null;
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
					team = row[2]; // get team
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
	public String getStat(String playerName, int num) {
		try {
			Scanner scanner = new Scanner(new File("NBA STAT REAL - Sheet1.csv"));
			String stat = null;
			while (scanner.hasNextLine()) {
				String[] row = scanner.nextLine().split(","); // Split by commas
				// lowercase and trim name
				String player = row[1].trim().toLowerCase();
				if (player.equals(playerName.toLowerCase())) { // find the player
					stat = row[num]; // get stat
					return stat;
				}
			}
			scanner.close(); // Close the scanner if the player is not found
			return stat;
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
