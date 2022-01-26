import java.util.Scanner; // Used for accessing player name

public class Player {
    private int numPoints;                       // integer that stores the number of points a player has
    private String name;                         // String that stores the current name
    private Scanner sc = new Scanner(System.in); // Scanner that will be used to get player name, using System.in

    /*
     * Constructs a player by asking their name with a given prompt. 
     * NOTE: since I'm using this to make it so you can create a custom prompt for the player, I cannot overload the Player constructor
     * to take in a player's name directly.
     * preconditions: prompt is correctly initialized
     * postconditions: player is ready to be used.
     */
    public Player(String prompt) {
        numPoints = 0;                                             // Set the number of points to equal zero
        System.out.println("What is your name, " + prompt + "? "); // Prompts the player for input
        name = sc.next();                                          // Receives user input. NOTE: does not work if player name has spaces
    }

    /*
     * Returns the number of points
     * Preconditions: Player is constructed
     * Postconditions: number of points is returned
     */
    public int getPoints() {
      return numPoints; // return the number of points
    }

    /*
     * Adds a certain value to the number of points
     * Preconditions: Player is constructed
     * Postconditions: Increase the number of points by the specified value
     */
    public void incrementPoints(int amountToIncrease) {
      numPoints += amountToIncrease; // add the number of points specified to the total
    }
    /*
     * Returns the name
     * Preconditions: Player is constructed, and name is a valid String
     * Postconditions: The name is returned
     */
    public String getName() {
      return name;
    }
}
