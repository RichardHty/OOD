package Models;


import com.sun.org.apache.regexp.internal.RE;

import java.util.*;

import static Models.Constants.WIN;

public class Player {
    private String uName;
    private double asset;
    private double bet;
    private boolean isDealer;
    private List<CardSet> currentCards;

    public Player(){
        this.uName = "Dealer";
        this.asset = 500;
        this.bet   = 0;
        this.isDealer = true;
        this.currentCards = new ArrayList<>();
        this.currentCards.add(new CardSet());
    }
    public Player(String user){
        this.uName = user;
        this.asset = 500;
        this.bet   = 0;
        this.isDealer = false;
        this.currentCards = new ArrayList<>();
        this.currentCards.add(new CardSet());

    }

    public boolean broken() {
        boolean flag =  true;
        for(CardSet cardSet:currentCards){
            if(!cardSet.getStand()){
                flag = false;
            }
        }
        flag &= asset <= 0;
        return flag;
    }

    public boolean splitCurrentCards(int i) {
        CardSet currentCardSetToSplit = currentCards.get(i);
        if(!currentCardSetToSplit.isCurrentCardsSplittable()){
            return false;
        }
        if(asset < bet){
            return false;
        }

        CardSet list = new CardSet();
        list.addCard(currentCardSetToSplit.removeCard(1));
        currentCardSetToSplit.disableCurrentCardsSplittable();

        currentCards.add(i+1,list);
        setBet(bet,i+1);

        return true;
    }
    public boolean addCardToCardSet(Card card, int i) {
        currentCards.get(i).addCard(card);
        return currentCards.get(i).isCurrentCardsSplittable();
    }

    public String getuName() {
        return uName;
    }

    public double getAsset() {
        return asset;
    }


    public double getBet() {
        return bet;
    }

    public boolean isDealer() {
        return isDealer;
    }

    public boolean setBet(double bet, int index) {
        if(bet > asset || bet <= 0) {
            return false;
        }
        this.bet   += bet;
        this.asset -= bet;
        this.getCurrentCardSet(index).setBet(bet);
        return true;
    }

    public void setDealer() {
        isDealer = true;
    }

    public List<CardSet> getCurrentCards() {
        return currentCards;
    }
    public CardSet getCurrentCardSet(int i) {
        return currentCards.get(i);
    }
    public int getCurrentCardSetNum() {
        return currentCards.size();
    }
    public void resetCardsAndBet(){
        this.currentCards = new ArrayList<>();
        this.currentCards.add(new CardSet());
        this.bet = 0;
    }
    public void updateAsset(int index, int type) {
        if(type == WIN)
            this.asset += 2 * currentCards.get(index).getBet();
    }

    @Override
    public String toString() {
        return "Hello," + uName  +
                ", you have " + asset +
                " dollars.";
    }
}
