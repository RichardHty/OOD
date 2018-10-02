package Models;

import java.util.Random;

public class CardGenerator {

    private boolean[] remaining;
    private int count;
    private int cardsLimit;
    private int cardsKind;
    private int suitKind;

    public CardGenerator(int cardsNumLimit, int cardsKind, int suitKind){
        remaining = new boolean[cardsNumLimit];
        count = 0;
        cardsLimit = cardsNumLimit;
        this.cardsKind = cardsKind;
        this.suitKind = suitKind;
    }
    public Card getACard() {
        Random rand = new Random();
        int x = rand.nextInt(cardsKind);
        int y = rand.nextInt(suitKind);
        while(count < cardsLimit && remaining[x * suitKind + y]){
            x = rand.nextInt(cardsKind);
            y = rand.nextInt(suitKind);
        }
        // todo:if run out of cards
        count ++;
        remaining[x * suitKind+ y] = true;

        return new Card(x, y);
    }
    public void reset(){
        remaining = new boolean[cardsLimit];
        count = 0;
    }
}
