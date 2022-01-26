import java.io.FileReader;     // Filereader needed for reading lines from input file
import java.io.BufferedReader; // BufferedReader also needed for reading lines from input file
import java.io.IOException;    // IOException needed for handling file opening errors

import java.util.ArrayList; // Array List needed for mutable phrase array
import java.util.Random;    // Random needed for selecting a random phrase
import java.util.Scanner;   // Scanner needing for receiving user input

public class Board {
    private String phrase;                // the string that stores the phrase itself
    private String solvedPhrase;          // the string that stores user progress on the phrase
    private BufferedReader file;          // the file that will be read from
    boolean isPlaying;                    // a variable that stores whether or not the game is running
    private ArrayList<String> allPhrases; // a list of all phrases
    private Random randomGenerator;       // a Random-number generator for selecting random phrases
    private Scanner sc;                   // a Scanner to handle console input
    /*
     * Construct a new board
     * Preconditions: none
     * Postconditions: all methods require this to be done first for them to work
     */
    public Board() {                                    
      isPlaying = true;                                // Make it so the game is playing by default
      allPhrases = new ArrayList<String>();            // Create a new ArrayList, storing Strings
      randomGenerator = new Random();                  // Create a new random-number generator
      sc = new Scanner(System.in).useDelimiter("\\n"); // Construct the scanner on System.in and make it able to take multi-word input
    }

    /*
     * Stores a random phrase into the variable phrase, using input from a file in the current directory named ListOfPhrases.txt
     * Preconditions: the file ListOfPhrases.txt exists in the current directory
     * Postconditions: the random phrase is stored inside of phrase
     */
    public void generateRandomPhrase() {
        try {                                                                    // Try the following:
            file = new BufferedReader(new FileReader("ListOfPhrases.txt"));      //  - open a file, named ListOfPhrases.txt
            String currentLine = file.readLine();                                //  - set a variable to store the currentLine
            allPhrases.add(currentLine);                                         //  - add the current line to the phrases array list
            while(currentLine != null) {                                         //  - while the end of the file is not reached
              currentLine = file.readLine();                                     //  - read the current line
              allPhrases.add(currentLine);}                                      //  - add it to the list
            phrase = allPhrases.get(randomGenerator.nextInt(allPhrases.size())); //  - get a random value from the array list
        } catch(IOException e) {                                                 // If an IOEXception occurs
            System.out.println("Hey! Chances are, your program is configured " + //  - notify the user that their program is likely 
            "incorrectly. Make sure you have the file \"ListOfPhrases.txt\" " +  //    configured incorrectly and that ListOfPhrases
            "in your directory.");                                               //    needs to be in the current directory.
            while(true) {}                                                       //  - idle until program closed
        }
    }
    /*
     * Display what is known about the phrase to the screen, with unknown characters represented with '_'s
     * Preconditions: the string is initialized, and does not contain illegal characters
     * Postconditions: the string is outputted, with unguessed alphabetic characters replaced with '_'
     */
    public void loadPhrase() {
      solvedPhrase = "";                                        // Initialize a variable that will store the displayed string
      for(int i = 0; i < phrase.length(); i++) {                // For every character in the phrase:
        solvedPhrase += Character.isLetter(phrase.charAt(i)) ?  //  - if it's a letter, add an underscore
                        '_' : phrase.charAt(i);}                //  - otherwise, add the character
      System.out.println(solvedPhrase);                         // Print out the string
    }

    /*
     * Displays the scores to the screen
     * Preconditions: player scores are defined
     * Postconditions: the score is printed
     */
    public void printScore(Player player1, Player player2) {
      System.out.println("---------------------");                  // Print out guardrails to clearly separate the score
      System.out.println("Player 1 score: " + player1.getPoints()); // Print out player 1's score
      System.out.println("Player 2 score: " + player2.getPoints()); // Print out player 2's score
      System.out.println("---------------------");                  // Print out guardrails to clearly separate the score
    }

    /*
     * The overarching method for getting player input
     * Asks the user for what kind of input they want, then delegate the task of retrieving that input to other methods
     * Preconditions: the game is running
     * Postconditions: the player is able to enter letter or phrase input, depending on their choice
     */
    public void getInput(Player player1, Player player2) {
      System.out.print("Would you like to guess a letter, or the entire word? ("  // prompts the user for what type of input they want
       + player1.getName() + ") (l/w) ");                                         // accepts a single character, l or w
      char input = sc.next().charAt(0);                                           // trim the string down to the first character
      if(input == 'l') {                                                          // if the player is giving letter input:
        guessLetter(player1);                                                     //  - ask for a letter input
      } else if(input == 'w') {                                                   // if the player is giving phrase input:
        guessWord(player1, player2);                                              //  - ask for phrase input
      } else {                                                                    // if the player entered an invalid character:
        getInput(player1, player2);                                               //  - call the entire method again, asking them again
      }
    }

    /*
     * Asks the user for a letter input and waits until one is given before proceeding
     * Increments the score and updates the phrase if the letter is in the word
     * Preconditions:  The player is asking for a letter, not a word
     * Postconditions: The program is ready to handle the next player's input
     */
    private void guessLetter(Player player) {              
      System.out.print("Enter an alphabetic character, "  //
       + player.getName() + " ");                         // Prompts single character input
      char input = sc.next().toLowerCase().charAt(0);     // Obtains and stores single character input
      String newString = "";                              //
      if(Character.isLetter(input)) {                     // Check if the input is alphabetic
        for(int i = 0; i < phrase.length(); i++) {        // 
          if(phrase.charAt(i) == input) {                 // Check if the input is in the hidden word
            newString += phrase.charAt(i);                //  - If so, update the solved phrase with the found character
            player.incrementPoints(1);                    //  - Increase player points. I didn't really understand why randomness would 
                                                          //    make much sense here, and so I didn't use it
          }                                               //
          else                                            // If the character is not in the word
            newString += solvedPhrase.charAt(i);          //  - Do not modify the solved phrase
        }                                                 //
        solvedPhrase = newString;                         // Update the solved phrase to the temporary string
        System.out.println(newString);                    // Display to the player the updated string
      } else {                                            //
        guessLetter(player);                              // If the input isn't alphabetic, ask for it again
      }
    }

    /*
     * Asks the user for a string input and waits until one is given before proceeding
     * Ends the game if the correct word is guessed
     * NOTE: guessing the word correctly will NOT necessarily win you the game, the winner is whoever has the highest score
     * Preconditions:  The player is asking for the entire phrase, not a letter
     * Postconditions: The game is either over, or it proceeds to the next turn
     */
    private void guessWord(Player player1, Player player2) {
      System.out.print("Enter the word, " + player1.getName() + " "); // Prompt the user for input
      String str = sc.next();                                         // Collect the input
      if(str.equals(phrase)) {                                        // If the player correctly guessed the word:
        endGame(player1, player2);                                    //  - end the game with the inputted players
      }                                                               //
    }

    /*
     * Ends the game, announcing the winner
     * Preconditions: The word has been guessed
     * Postconditions: The program terminates, displaying the winner
     */    
    private void endGame(Player player1, Player player2) {
      if(player1.getPoints() > player2.getPoints()) {                               // if player 1 has more points:
        System.out.println("Player 1 " + player1.getName() + " has won this game"); //  - announce that player 1 has won the game
      } else if(player1.getPoints() < player2.getPoints()) {                        // if player 2 has more points:
        System.out.println("Player 2 " + player2.getName() + " has won this game"); //  - announce that player 2 has won the game
      } else {                                                                      // if the game is a tie:
        System.out.println("This game is a tie.");                                  //  - announce that the game is a tie
      }                                                                             //
      isPlaying = false;                                                            // Update the variable that checks if the game is
                                                                                    // ongoing to false
    }
}