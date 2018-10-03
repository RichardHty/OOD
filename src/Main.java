
import java.util.Scanner;

import static Models.Constants.CARDS_LIMIT;
import static Models.Constants.CARDS_NUM_LIMIT;
import static Models.Constants.CARDS_SUIT_LIMIT;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int numOfPlayers = -1, mode = -1;

        while(numOfPlayers <= 0 || (mode != 0 && mode != 1)){
            if(numOfPlayers != -1 && mode != -1) System.out.println("Input error");
            if(numOfPlayers <= 1) {
                System.out.println("Enter a valid number of players(including dealer): ");
                numOfPlayers = Integer.parseInt(input.nextLine());

            } else {
                System.out.println("Enter mode, 0 for player vs player, 1 for computer vs player:");
                mode = Integer.parseInt(input.nextLine());
            }
        }

        BlackJack blackJack = new BlackJack(numOfPlayers,mode);
        blackJack.start();
        while (!blackJack.play());

        System.out.println("\nThanks for playing! Goodbye!");
    }
    private static boolean playerStaysForAnotherRound() {
        System.out.println("Another round?");
        return true;
    }
}
