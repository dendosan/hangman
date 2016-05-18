package fun.with.games;

import java.io.IOException;
import java.util.Scanner;

public class HangmanApplication {

   public static void main(String[] args) throws IOException {
      Scanner sc = new Scanner(System.in);
      System.out.println("Welcome to Hangman!\n\n"
            + "I will pick a word and you have 6 chances"
            + " to guess the word.");
      System.out.println();
      System.out.println("I have picked my word.  Below is a picture and below that is your"
            + " current guess which starts off as nothing.\n"
            + "Everytime you guess incorrectly,"
            + " I will add a new part to the picture.\n"
            + "When there is a full person, you lose!");
      boolean doYouWantToPlay = true;
      while ( doYouWantToPlay ) {
         System.out.println("Alright! Let's play!");
         Hangman game = new Hangman();
         
         do {
            System.out.println();
            System.out.println(game.drawPicture());
            System.out.println();
            System.out.println(game.getFormalCurrentGuess());
            //System.out.println(game.mysteryWord);
            
            // Get the guess
            System.out.println("Enter a character that you think is in the word.");
            char guess = (sc.next().toLowerCase()).charAt(0);
            System.out.println();
            
            // Check is guessed already?
            while ( game.isGuessedAlready(guess) ) {
               System.out.println("Try again!  You already guessed \'" + guess + "'");
               guess = (sc.next().toLowerCase()).charAt(0);
            }

            if ( game.playGuess(guess) ) {
               System.out.println();
               System.out.println("Great guess! \'" + guess + "'" + " is in the word!");
            } else {
               System.out.println();
               System.out.println("Unfortunately, \'" + guess + "'" + " is not in the word.");
               
               // Print the fatal hanging if there is no stay on the execution!
               if ( game.currentTry >= game.maxTries ) {
                  System.out.println(game.drawPicture());
               }
            }
            
         } while ( ! game.gameOver() );
         
         System.out.println();
         System.out.println();
         System.out.println();
         System.out.println("Do you want to play another game?  Enter Y if you do.");
         char response = (sc.next().toUpperCase()).charAt(0);
         doYouWantToPlay = (response == 'Y');
      }
      
      sc.close();
   }
}
