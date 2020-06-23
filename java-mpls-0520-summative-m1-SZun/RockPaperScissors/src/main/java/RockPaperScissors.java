
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author samg.zun
 */
public class RockPaperScissors {

    public static void main(String[] args) {

        game();
        boolean wantsToPlayAgain = true;
        do {
            String userChoice = ConsoleIO.readString("Would you like to play again? (y/n) ");
            if (userChoice.equals("y")) {
                game();
            } else {
                wantsToPlayAgain = false;
            }
        } while (wantsToPlayAgain);

    }

    public static void game() {
        int wins = 0;
        int losses = 0;
        int ties = 0;

        int rounds = ConsoleIO.readInt("How many rounds would you like to play? (1-10) ", 1, 10);

        for (int i = 0; i < rounds; i++) {
            //get user input
            RPS userInput = RPS.values()[ConsoleIO.readInt("Guess rock (1), paper (2), or scissors (3) ", 1, 3) - 1];

            //calculate computer choice
            Random rng = new Random();
            RPS computerChoice = RPS.values()[rng.nextInt(3)];
            //compare user input and computer choise and update variables
            if (userInput == computerChoice) {
                ties = handleOutcome(ties, "tie");
            } else if (userInput == RPS.ROCK) {
                if (computerChoice == RPS.PAPER) {
                    losses = handleOutcome(losses, "loss");
                } else {
                    wins = handleOutcome(wins, "win");
                }
            } else if (userInput == RPS.PAPER) {
                if (computerChoice == RPS.ROCK) {
                    wins = handleOutcome(wins, "win");
                } else {
                    losses = handleOutcome(losses, "loss");
                }
            } else {
                if (computerChoice == RPS.ROCK) {
                    losses = handleOutcome(losses, "loss");
                } else {
                    wins = handleOutcome(wins, "win");
                }
            }
        }

        System.out.println("Ties: " + ties + " Wins: " + wins + " Losses: " + losses);
        String res = wins == losses ? "You've tied!" : (wins > losses ? "You've won!" : "The computer won");
        System.out.println(res);
    }

    public static int handleOutcome(int num, String result) {
        num++;
        System.out.println(result);
        return num;
    }

}
