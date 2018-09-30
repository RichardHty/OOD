import Models.Constants;
import Models.Dealer;
import Models.Player;

import java.util.Random;

public class BlackJack extends CardGame{

    private int currentPlayerAmount;
    private int mode;
    private int dealerIndex;

    BlackJack(int cardsNumLimit, int cardsKind, int suitKind, int numOfPlayers, int mode) {
        super(cardsNumLimit,cardsKind, suitKind, numOfPlayers);
        currentPlayerAmount = numOfPlayers;
        this.mode = mode;
        dealerIndex = 0;
    }
    private void start() {

        if(mode == Constants.MODE_PLAYER_PLAYER) {
            Random rand = new Random();
            dealerIndex = rand.nextInt(currentPlayerAmount);
        } else if (mode == Constants.MODE_PLAYER_COMPUTER) {

        } else {

        }

        players[dealerIndex].setDealer();
    }
    public void play() {
        start();
        for(Player p : players) {
            if(p.isDealer()){

            }
        }
    }


}
