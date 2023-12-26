package Trolls;

import java.util.Scanner;

/**
 * Class GameTroll.
 * Course code tailored by the CSC207 instructional
 * team at UTM, with special thanks to:
 *
 * @author anshag01
 * @author mustafassami
 * @author guninkakr03
 *  */
public class GameTroll implements Troll {


    /**
     * Print GameTroll instructions for the user
     */
    public void giveInstructions()
    {
        System.out.println("Welcome to the Game Troll's lair!");
        System.out.println("You must guess the correct number between 1 and 10 to pass.");
    }

    /**
     * Play the GameTroll game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {
        Scanner scanner = new Scanner(System.in);
        int correctNumber = 7;

        System.out.println("Enter your guess:");
        int userGuess = scanner.nextInt();

        if (userGuess == correctNumber) {
            System.out.println("Congratulations! You may pass.");
            return true;
        } else {
            System.out.println("Wrong guess! You shall not pass!");
            return false;
        }
    }


    /**
     * Main method, use for debugging
     *
     * @param args: Input arguments
     */
    public static void main(String [] args) throws InterruptedException {
        GameTroll s = new GameTroll();
        boolean a = s.playGame();
    }
}
