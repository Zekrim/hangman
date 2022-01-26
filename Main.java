/*
 * HANGMAN: 2.5.5
 * By Marshall Hamon
 * 1.25.2022
 * APCSA 1
 * Plays a game of hangman, where the winner is determined by who has guessed the most letters.
 * Relevant preconditions:
 *  - Players have one-word names (underscores, hyphens, etc. ok)
 *  - ListOfPhrases in current directory
 * Postconditions:
 *  - A game of hangman is played and the results are displayed
 */

public class Main {
    public static void main(String[] args) { 
        PhraseSolver mySolver = new PhraseSolver(); // create a new phrase solver
        mySolver.play(); // run the game
    }
}
