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
	private JTextField textField;
	private JTextField compareTextField;
	private boolean showText;
	private boolean comparePlayer;
	private boolean comparePage;
	private String playerNameOne;
	private String playerNameTwo;
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
				if (!comparePlayer) {
					playerNameOne = textField.getText().trim();
					removeAll();
					showText = true;
					revalidate();
					repaint();
				} else {
					playerNameOne = textField.getText().trim();
					playerNameTwo = compareTextField.getText().trim();
					removeAll();
					comparePage = true;
					revalidate();
					repaint();
				}
			}
		});
		add(textField);
		// Add mouse listener to handle button clicks
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				for (String button : buttons.keySet()) {
					Rectangle bounds = buttons.get(button);
					if (bounds.contains(e.getPoint())) {
						if (button.equals("compare")) {
							comparePlayer = true;
							compareTextField = new JTextField();
							compareTextField.setBounds(340, 350, 320, 40);
							compareTextField.setHorizontalAlignment(JTextField.CENTER);
							compareTextField.setForeground(textColor);
							compareTextField.setCaretColor(Color.WHITE);
							compareTextField.setBackground(new Color(44, 49, 91));
							add(compareTextField);
							revalidate();
							repaint();
						} else if (button.equals("return")) {
							playerNameOne = null;
							removeAll();
							showText = false;
							add(textField);
							mainStat = "STAT";
							comparePlayer = false;
							revalidate();
							repaint();
						} else {
							String a = getStat(playerNameOne, nums.get(button));
							mainStat = a;
							repaint();
						}
					}
				}
			}
		});
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // Call the superclass method to handle the default painting logic, including
									// clearing the background.
		Graphics2D g2 = (Graphics2D) g;
		Color c = new Color(44, 49, 91);
		g2.setColor(c);
		nums = new HashMap<>();
		buttons = new HashMap<>();
		// FRONT PAGE
		if (!showText) {
			Font font = new Font("SansSerif", Font.BOLD, 60);
			g2.setFont(font);
			g2.drawString("Enter NBA Player Here", 180, 190);
			loadImage("nbalogo");
			g2.drawImage(fImage, 340, 350, this);
			loadImage("compare");
			g2.drawImage(fImage, 355, 650, this);
			buttons.put("compare", new Rectangle(355, 650, 310, 90));
			// DEBUG MODE
			g2.setColor(Color.RED);
			for (String button : buttons.keySet()) {
				Rectangle bounds = buttons.get(button);
				g2.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
			}
			// END
		}
		// PAGE WITH ONE PLAYER
		if (showText) {
			Font font = new Font("SansSerif", Font.BOLD, 60);
			g2.setFont(font);
			int textWidth = g2.getFontMetrics().stringWidth(playerNameOne.toUpperCase());
			int x = (getWidth() - textWidth) / 2;
			g2.drawString(playerNameOne.toUpperCase(), x, 100);
			// if player exists
			if (checkPlayer(playerNameOne) && !comparePlayer) {
				// bounds of each button
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
				// DEBUG MODE: Drawing red rectangles around buttons
				g2.setColor(Color.RED);
				for (String button : buttons.keySet()) {
					Rectangle bounds = buttons.get(button);
					g2.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
				}
				// END DEBUG MODE
				// link the string to the stat
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
				// load the buttons and team
				String team = getTeam(playerNameOne);
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
				// display the stat
				textWidth = g2.getFontMetrics().stringWidth(mainStat);
				x = (getWidth() - textWidth) / 2;
				g2.drawString(mainStat, x, 175);
			} else if (comparePage && checkPlayer(playerNameOne) && checkPlayer(playerNameTwo)) {
				loadImage("return");
				g2.drawImage(fImage, -140, -170, this);
				buttons.put("return", new Rectangle(20, 30, 180, 100));
			}
			// else, the player does not exist
			else {
				String noPlayer = "Player does not exist.";
				textWidth = g2.getFontMetrics().stringWidth(noPlayer);
				x = (getWidth() - textWidth) / 2;
				g2.drawString(noPlayer, x, 400);
				loadImage("return");
				g2.drawImage(fImage, -140, -170, this);
				buttons.put("return", new Rectangle(20, 30, 180, 100));
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
