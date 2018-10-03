package Models;

import java.util.Random;

import static Models.Constants.CARDS_LIMIT;
import static Models.Constants.CARDS_NUM_LIMIT;
import static Models.Constants.CARDS_SUIT_LIMIT;

public class CardGenerator {
    // singleton class to make sure only one cardGenerator is in use
    private boolean[] remaining;
    private int count;
    private int cardsLimit;
    private int cardsKind;
    private int suitKind;
    static private CardGenerator cardGenerator;

    private CardGenerator(int cardsNumLimit, int cardsKind, int suitKind){
        this.remaining = new boolean[cardsNumLimit];
        this.count = 0;
        this.cardsLimit = cardsNumLimit;
        this.cardsKind = cardsKind;
        this.suitKind = suitKind;
    }
    static public CardGenerator getInstanceOfCardGenerator() {
        if(cardGenerator == null) {
            cardGenerator = new CardGenerator(CARDS_LIMIT,CARDS_NUM_LIMIT,CARDS_SUIT_LIMIT);
        }
        return cardGenerator;
    }
    static public CardGenerator getInstanceOfCardGenerator(int cardsNumLimit, int cardsKind, int suitKind) {
        if(cardGenerator == null) {
            cardGenerator = new CardGenerator(cardsNumLimit,cardsKind,suitKind);
        }
        return cardGenerator;
    }
    public int getCardsLimit() {
        return cardsLimit;
    }

    public Card getACard() {
        if(count >= cardsLimit){
            System.out.println("Run out of cards. Please reset card generator.");
            return null;
        }
        Random rand = new Random();

        int x = rand.nextInt(cardsKind);
        int y = rand.nextInt(suitKind);
        while(count < cardsLimit && remaining[x * suitKind + y]){
            x = rand.nextInt(cardsKind);
            y = rand.nextInt(suitKind);
        }
        count ++;
        remaining[x * suitKind+ y] = true;

        return new Card(x, y);
    }
    public void reset(){
        remaining = new boolean[cardsLimit];
        count = 0;
    }
}
