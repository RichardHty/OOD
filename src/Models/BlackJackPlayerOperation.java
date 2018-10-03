package Models;


public class BlackJackPlayerOperation extends CardGamePlayerOperation{

    private CardGenerator cardGenerator;
    private BlackJackPlayer player;
    private int cardSetIndex;

    public BlackJackPlayerOperation(BlackJackPlayer player,int cardSetIndex) {
        super(player.getCurrentCardSet(cardSetIndex));
        this.cardSetIndex = cardSetIndex;
        this.player = player;
        this.cardGenerator = CardGenerator.getInstanceOfCardGenerator();

    }
    public BlackJackPlayerOperation(BlackJackPlayer player,int cardSetIndex, CardGenerator cardGenerator) {
        super(player.getCurrentCardSet(cardSetIndex));
        this.cardSetIndex = cardSetIndex;
        this.cardGenerator = cardGenerator;
        this.player = player;
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
    public boolean splitOperation() {
        if (splitCurrentCards()){
            dealCard(cardSetIndex);
            dealCard(cardSetIndex+1);
            System.out.println("Split succeeded");
            return true;
        }
        System.out.println("Split failed. Check your options and money :)");
        return false;
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
    }
}
