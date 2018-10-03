package Models;

import static Models.Constants.CARDS_LIMIT;
import static Models.Constants.CARDS_NUM_LIMIT;
import static Models.Constants.CARDS_SUIT_LIMIT;

public class BlackJackPlayerOperation extends CardGamePlayerOperation{

    private static CardGenerator cardGenerator;
    private BlackJackPlayer player;
    private int cardSetIndex;

    public BlackJackPlayerOperation(BlackJackPlayer player,int cardSetIndex) {
        super(player.getCurrentCardSet(cardSetIndex));
        this.cardSetIndex = cardSetIndex;
        this.player = player;
        defaultCardGenerator();
    }
    public BlackJackPlayerOperation(BlackJackPlayer player,int cardSetIndex, CardGenerator cardGenerator) {
        super(player.getCurrentCardSet(cardSetIndex));
        this.cardSetIndex = cardSetIndex;
        this.cardGenerator = cardGenerator;
        this.player = player;
    }
    public static CardGenerator defaultCardGenerator() {
        if(cardGenerator == null){
            cardGenerator = new CardGenerator(CARDS_LIMIT,CARDS_NUM_LIMIT,CARDS_SUIT_LIMIT);
        }
        return cardGenerator;
    }
    public void setPlayer(BlackJackPlayer player) {
        this.player = player;
    }
    public boolean setCardSetIndex(int index) {
        if(index >= player.getCurrentCardSetNum())
            return false;
        cardSet = player.getCurrentCardSet(index);
        return true;
    }
    public boolean setOperationObj(BlackJackPlayer player, int cardSetIndex) {
        setPlayer(player);
        return setCardSetIndex(cardSetIndex);
    }
    public void dealCard(int index) {
        Card card = cardGenerator.getACard();
        player.getCurrentCardSet(index).addCard(card);
    }
    public void hitOperation() {
        dealCard();
        displayCardSetInfo();
    }
    public void splitOperation() {
        if (splitCurrentCards()){
            dealCard(cardSetIndex);
            dealCard(cardSetIndex+1);
            System.out.println("Split succeeded");
        } else {
            System.out.println("Split failed.");
        }
    }
    public boolean doubleDownOperation() {

        if(!player.setBet(cardSet.getBet(), cardSetIndex)){
            return false;
        }
        cardSet.setStand();
        dealCard();
        displayCardSetInfo();
        return true;
    }
    public void standOperation() {
        displayCardSetInfo();
        cardSet.setStand();
    }

    private boolean splitCurrentCards() {

        if(!cardSet.isCurrentCardsSplittable()){
            return false;
        }
        if(player.getAsset() < player.getBet()){
            return false;
        }

        CardSet list = new CardSet();
        list.addCard(cardSet.removeCard(1));
        cardSet.disableCurrentCardsSplittable();

        player.getCurrentCards().add(cardSetIndex+1,list);
        player.setBet(player.getBet(),cardSetIndex+1);

        return true;
    }

    @Override
    public void dealCard() {
        Card card = cardGenerator.getACard();
        player.getCurrentCardSet(cardSetIndex).addCard(card);
    }

    @Override
    public void displayCardSetInfo() {
        for (Card card : cardSet.getCurrentCards())
        {
            System.out.println(card.toString());
        }
        int cardSetScore = cardSet.calculateScore();
        System.out.println("current score for this hand is: " + cardSetScore);
        if(cardSetScore > 21) System.out.println("Score bust.");
        System.out.println();
    }
}
