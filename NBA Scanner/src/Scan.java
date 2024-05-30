
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Scan {

	public static void main(String[] args) {

		try {
			HashMap m = new HashMap<String, Integer>();

			// User input
			while (true) {
				Scanner scanner = new Scanner(new File("NBA STAT REAL - Sheet1.csv"));
				Scanner userInput = new Scanner(System.in); // Create a Scanner object
				System.out.println("Please enter an NBA Player");
				String player = userInput.nextLine().trim().toLowerCase(); // read user input and turn lowercase

				// turn the state lowercase and compare to each player in the file
				double stat = -1.0;
				while (scanner.hasNextLine()) {
					String[] row = scanner.nextLine().split(","); // Split by commas
					// lowercase and trim name
					String playerName = row[1].trim().toLowerCase();
					if (playerName.equals(player)) { // find the player
						stat = Double.parseDouble(row[5]); // get the points (5 = points)
						break;
					}
				}
				m.put(player, stat); // link stat to the player

				System.out.println(player + "'s stat is " + m.get(player));

			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}