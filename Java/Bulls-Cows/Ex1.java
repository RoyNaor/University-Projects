import java.util.Scanner;
import java.util.Arrays;

/**
 * Introduction to Computer Science, Ariel University, Ex1 (manual Example + a Template for your solution)
 * See: https://docs.google.com/document/d/1C1BZmi_Qv6oRrL4T5oN9N2bBMFOHPzSI/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true

 * Ex1 Bulls & Cows - Automatic solution.
 * Author: Roy Naor
 * ID: 323917104
 * ** README **
 * This Java class provides an automated and manual solutions to the game "Bulls and Cows".
 * The game is based on logic where the player needs to guess a secret number based on result returned for each guess.
 * The result is given in terms of "Bulls", representing correct digits in their correct positions,
 * and "Cows", correct digits but in the wrong positions.
 * The goal is to guess the secret number with as few guesses as possible

 * **** General Solution (algorithm) ****
 * The algorithm is based on a boolean array that his length is equal to the maximum possible numbers there are
 * between zero and ten in the power of number of digits of the code (0 < num < 10 ** number of digits)
 * every value at the start is TRUE, The reason is: every number in the array can be the secret code until the
 * algorithm prove it's not the secret code so the value change to FALSE.
 * The algorithm use the properties of Class "BP_Game" to extract the number of digits in the code.
 * After extracting the number of digits the method "autoEx1Game" create the boolean array "possibleNumbers" and Fill it
 * using the "Arrays" Class with TRUE. The main loop the method use is While. Using one of the "BP_Game" methods
 * called "isRunning" the method can run over and over until "isRunning" return FALSE.
 * Within the while loop in "autoEx1Game",the algorithm generates a guess for the secret code.
 * This is by calling the "NextGuess" method,
 * which loop over the possibleNumbers array and selects the next number that is still TRUE.
 * The selected number is then transformed into an array of its digits using the "toArray" method.
 * This digit array is the current guess. The guess is then submitted to the BP_Server game,
 * then returns the count of Bulls and Cows. After receiving the Bulls and Cows count,
 * the algorithm updates the "possibleNumbers" array using the "updateGuess" method.
 * This method loops over each number in the "possibleNumbers" array
 * and determines if it could produce the same Bulls and Cows count as the current guess.
 * If a number would produce a different count, it is marked as FALSE in the "possibleNumbers" array.

 * **** Results ****
 * Make sure to state the average required guesses
 * for 2,3,4,5,6 digit code:
 * Average required guesses 2: 7.24
 * Average required guesses 3: 7.94
 * Average required guesses 4: 8.79
 * Average required guesses 5: 9.02
 * Average required guesses 6: 9.49
 */

public class Ex1 {
    public static final String Title = "Ex1: Bulls & Cows game";
    public static int numOfDigits = 4; // Global variable, number of digits [2,6]
    public static int rounds_counter = 0;


    public static void main(String[] args) {
        BP_Server game = new BP_Server();   // Starting the "game-server"
        long myID = 323917104L;             // Your ID should be written here
        game.startGame(myID, numOfDigits);  // Starting a game
        System.out.println(Title+" with code of "+numOfDigits+" digits");
        //manualEx1Game(game); // the manual way to play
        autoEx1Game(game); // My function - Roy Naor.
    }
    public static void manualEx1Game(BP_Server game) {
        Scanner sc = new Scanner(System.in); //create input scanner
        int ind=1;      // Index of the guess
        int numOfDigits = game.getNumOfDigits();
        double max = Math.pow(10,numOfDigits);
        while(game.isRunning()) {           // While the game is running (the code has not been found yet)
            System.out.print(ind+") enter a guess: ");
            int g = sc.nextInt();
            if(g>=0 && g<max) {
                int[] guess = toArray(g, numOfDigits); // int to digit array
                int[] res = game.play(guess); // Playing a round and getting the B,P
                if (game.isRunning()) {     // While the game is still running
                    System.out.println(ind + ") B: " + res[0] + ",  P: " + res[1]); // Prints the Bulls [0], and the Cows [1]
                    ind += 1;               // Increasing the index of guess
                }
            }
            else {
                System.out.println("ERR: wrong input, try again");
            }
        }
        System.out.println(game.getStatus());
    }

    /**
     * Simple parsing function that gets an int and returns an array of digits
     * @param a - a natural number (as a guess)
     * @param size  - number of digits (to handle the 00 case).
     * @return an array of digits
     */
    private static int[] toArray(int a, int size){
        int[] c = new int[size];
        for(int j=0;j<c.length;j+=1) {
            c[j] = a%10;
            a=a/10;
        }
        return c;
    }

    /** -autoEx1Game-
     * This function solves the Bulls & Cows game automatically.
     * the function generates guesses and uses the result from each guess (Bulls and Cows)
     * to update the array of possible answers. The function continues to make guesses
     * until the correct code  is found (Bulls is equal to the numbers of digits of the code).
     */
    public static void autoEx1Game(BP_Server game) {
        int numOfDigits = game.getNumOfDigits();
        double max = Math.pow(10, numOfDigits); // the number of possible answers

        boolean[] possibleNumbers = new boolean[(int)max]; // array with all the possible numbers
        Arrays.fill(possibleNumbers, true); // all numbers are possible so true until checked out

        while (game.isRunning()) {
            int guess = NextGuess(possibleNumbers); // create the next guess
            rounds_counter += 1;
            System.out.println(guess);
            int[] guessArray = toArray(guess, numOfDigits); // Convert the guess into an array of digits

            int[] result = game.play(guessArray); // Play a round and get B & P from the server
            int bulls = result[0]; // Number of Bulls in the result


            if (bulls == numOfDigits) {
                // found the code
                break;
            }
            // update the possible number array to create new guess
            possibleNumbers = updateGuess(possibleNumbers, guessArray, result);
        }
    }

    /** -NextGuess-
     * Return a possible number to guess in the game, the function loop over the boolean
     * array with all the possible numbers from 0 to (10 ** number) of digits and return a number that
     * still "true" - a possible choice
     */
    public static int NextGuess(boolean[] possibleNumbers) {
        for (int i = 0; i < possibleNumbers.length; i++) {
            if (possibleNumbers[i]) { //check if the index value is true
                return i;
            }
        }
        return -1; // No remaining possible numbers (should not happen)
    }

    /** -updateGuess-
     * This function updates the boolean array of possible numbers based on the latest guess and its result.
     * It loops over all possible numbers in the 'possibleNumbers' array,
     * and checks if each number would give the same Bulls and Cows result as the latest guess.
     * If a number doesn't match the result, it's marked as impossible (set to false).
    *  */
    public static boolean[] updateGuess(boolean[] possibleNumbers, int[] guess, int[] result) {
        // loop over all the remaining possible numbers and check if they have less than the result
        for (int i = 0; i < possibleNumbers.length; i++) {
            if (possibleNumbers[i]) { //checks only the indexes with true value
                int[] guessArray = toArray(i, guess.length); // copy of guess array
                int[] copyResult = new int[]{0, 0}; // compare to the real result

                // calculate Bulls and Cows for this number compared to the guess
                for (int j = 0; j < guess.length; j++) {
                    if (guess[j] == guessArray[j]) {
                        copyResult[0]++; // Bulls
                    } else {
                        // Check for Cows
                        for (int digit : guess) { // for each digit in guess array
                            if (digit == guessArray[j]) {
                                copyResult[1]++; // Cows
                                break; // break as we found a matching cow, no need to check further
                            }
                        }
                    }
                }

                if (!Arrays.equals(copyResult, result)) {
                    possibleNumbers[i] = false; // false if result doesn't match
                }
            }
        }
        return possibleNumbers;
    }
}
