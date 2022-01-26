public class PhraseSolver {
    private Board myBoard;       // Board to keep track of game state and handles general methods
    private Player player1;      // Player to keep track of the first player
    private Player player2;      // Player to keep track of the second player
    private int turnCounter = 0; // integer to keep track of the turn counter
    
    /*
     * The method that constructs PhraseSolver by constructing all member variables
     * Precoditions: none
     * Postconditions: the game is ready to be played
     */
    public PhraseSolver() {
      myBoard = new Board();           // construct the board
      player1 = new Player("player1"); // construct the first player
      player2 = new Player("player2"); // construct the second player
    }
    
  
    /*
     * The method that plays the entire game to its end
     * Preconditions: PhraseSolver is not null
     * Postconditions: game is complete
     */
    public void play() {
      myBoard.generateRandomPhrase();         // Create a random phrase
      myBoard.loadPhrase();                   // Load the phrase
      while(myBoard.isPlaying) {              // While the game is ongoing:
        if(turnCounter % 2 == 0)              //  - If it's player 1's turn:
          myBoard.getInput(player1, player2); //    - Get input from player 1
        else                                  //  - If it's player 2's turn:
          myBoard.getInput(player2, player1); //    - Get input from player 2
        turnCounter++;                        //  - Increment the turn counter
        myBoard.printScore(player1, player2); //  - print the score
        System.out.println();                 //  - print a new line to separate the next turn from this one
      }
    }
  }
  