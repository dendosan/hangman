package fun.with.games;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Hangman {

   String mysteryWord;
   StringBuilder currentGuess;
   ArrayList<Character> previousGuesses = new ArrayList<Character>();
   
   int maxTries = 6;
   int currentTry = 0;
   
   ArrayList<String> dictionary = new ArrayList<String>();
   private static FileReader fr;
   private static BufferedReader bfr;
   
   public Hangman() throws IOException {
      initializeStreams();
      mysteryWord = pickWord();
      currentGuess = initializeCurrentGuess();
   }

   public void initializeStreams() throws IOException {
       try {
          File inFile = new File("dictionary.txt");
          fr = new FileReader(inFile);
          bfr = new BufferedReader(fr);
          String currentLine = null;
          while ( (currentLine = bfr.readLine()) != null ) {
             dictionary.add(currentLine);
          }
       } catch(IOException e) {
          System.out.println("Could not init streams");
       }
   }
   
   public String pickWord() {
      Random rand = new Random();
      int wordIndex = Math.abs(rand.nextInt()) % dictionary.size();
      return dictionary.get(wordIndex);
   }
   
   // ex "_ _ A _ _ _ _ _ _ _"
   
   public StringBuilder initializeCurrentGuess() {
      StringBuilder current = new StringBuilder();
      for ( int i = 0; i < mysteryWord.length() * 2; i++ ) {
         if ( i % 2 == 0 ) {
            current.append("_");
         } else {
            current.append(" ");
         }
      }
      return current;
   }
   
   public boolean gameOver() {
      boolean isOver = false;
      if ( didWeWin() ) {
         System.out.println("You won!");
         isOver = true;
      } else if (didWeLose()) {
         System.out.println("Sorry, you lost!  You spent your 6 guesses.");
         System.out.println("The word was " + mysteryWord + ".");
         isOver = true;
      }
      return isOver;
   }
   
   private boolean didWeLose() {
      return currentTry >= maxTries;
   }

   private boolean didWeWin() {
      String guess = getCondensedCurrentGuess();
      return guess.equals(mysteryWord);
   }

   private String getCondensedCurrentGuess() {
      String guess = currentGuess.toString();
      return guess.replace(" ",  "");
   }

   // _ _ A B _ _ _ _ _
   public String getFormalCurrentGuess() {
      return "Current Guess: " + currentGuess.toString();
   }
   //
   //
   //    " - - - - -  \n" +
   //    "|        |  \n" +
   //    "|        O  \n" +
   //    "|      / | \\ \n" +
   //    "|        |   \n" +
   //    "|       / \\ \n" +
   //    "|            \n" +
   //    "|\n" +
   //    "|\n";
   //
   public String drawPicture() {
      
      switch ( currentTry ) {
      case 0: return noPersonDraw();
      case 1: return addHeadDraw();
      case 2: return addBodyDraw();
      case 3: return addOneArmDraw();
      case 4: return addSecondArmDraw();
      case 5: return addOneLegDraw();
      default: return fullPersonDraw();
      }
      
   }

   private String fullPersonDraw() {
      return
            " - - - - - \n" +
            "|        | \n" +
            "|        O \n" +
            "|      / | \\ \n" +
            "|        |   \n" +
            "|       / \\ \n" +
            "|            \n" +
            "|\n" +
            "|\n";
   }

   private String addOneLegDraw() {
      return
            " - - - - - \n" +
            "|        | \n" +
            "|        O \n" +
            "|      / | \\ \n" +
            "|        | \n" +
            "|       /  \n" +
            "|          \n" +
            "|\n" +
            "|\n";
   }

   private String addSecondArmDraw() {
      return
            " - - - - - \n" +
            "|        | \n" +
            "|        O \n" +
            "|      / | \\ \n" +
            "|        | \n" +
            "|          \n" +
            "|          \n" +
            "|\n" +
            "|\n";
   }

   private String addOneArmDraw() {
      return
            " - - - - - \n" +
            "|        | \n" +
            "|        O \n" +
            "|      / | \n" +
            "|        | \n" +
            "|          \n" +
            "|          \n" +
            "|\n" +
            "|\n";
   }

   private String addBodyDraw() {
      return
            " - - - - - \n" +
            "|        | \n" +
            "|        O \n" +
            "|        | \n" +
            "|        | \n" +
            "|          \n" +
            "|          \n" +
            "|\n" +
            "|\n";
   }

   private String addHeadDraw() {
      return
            " - - - - - \n" +
            "|        | \n" +
            "|        O \n" +
            "|          \n" +
            "|          \n" +
            "|          \n" +
            "|          \n" +
            "|\n" +
            "|\n";
   }

   private String noPersonDraw() {
      return
            " - - - - - \n" +
            "|        | \n" +
            "|          \n" +
            "|          \n" +
            "|          \n" +
            "|          \n" +
            "|          \n" +
            "|\n" +
            "|\n";      
   }

   public boolean isGuessedAlready(char guess) {
      return previousGuesses.contains(guess);
   }

   public boolean playGuess(char guess) {
      boolean isGoodGuess = false;

      for ( int i = 0; i < mysteryWord.length(); i++ ) {
         if ( mysteryWord.charAt(i) == guess ) {
            currentGuess.setCharAt(i * 2, guess);
            isGoodGuess = true;
         }
      }
      
      if ( ! isGoodGuess ) {
         currentTry++;
      }

      previousGuesses.add(guess);
      
      return isGoodGuess;
   }
   
}
