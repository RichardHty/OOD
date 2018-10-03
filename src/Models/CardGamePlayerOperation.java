package Models;

abstract public class CardGamePlayerOperation {
    protected CardSet cardSet;

    CardGamePlayerOperation(CardSet cardSet){
        this.cardSet = cardSet;
    }
    abstract public void dealCard();
    abstract public void displayCardSetInfo();
}
