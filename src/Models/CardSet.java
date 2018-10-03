package Models;

import java.util.*;


public class CardSet {
    private List<Card> currentCards;
    private boolean currentCardsSplitable;
    private int score;
    private Map<String,Integer> cards;
    private double bet;
    private boolean stand;
    CardSet(){
        currentCards = new ArrayList<>();
        currentCardsSplitable = false;
        score = 0;
        cards = new HashMap<>();
        stand = false;
        bet = 0;
    }

    public int calculateScore() {
        boolean isAceInHand = false;
        int sum = 0;
        for(Card card:currentCards) {
            sum += card.getScore();
            if(card.isAce()){
                isAceInHand = true;
            }
        }
        if(isAceInHand && sum <= 11) {
            sum += 10;
        }
        return sum;
    }
    void addCard(Card card) {
        currentCards.add(card);
        String k = String.valueOf(card.getScore());
        if(cards.containsKey(k)){
            if(currentCards.size() == 2)
                currentCardsSplitable = true;
            cards.put(k,cards.get(k)+1);
        } else {
            cards.put(k,1);
        }
    }
    public Card removeCard(int index) {
        Card card = currentCards.get(index);
        currentCards.remove(index);
        String k = String.valueOf(card.getScore());
        int res = cards.get(k) - 1;
        if(res < 1){
            cards.remove(k);
        } else {
            cards.put(k,res);
        }
        return card;
    }
    public void setStand() {
        stand = true;
    }
    public boolean getStand() {
        return stand;
    }


    public List<Card> getCurrentCards() {
        return currentCards;
    }
    public Card getCard(int i) {
        return currentCards.get(i);
    }

    public boolean isCurrentCardsSplittable() {
        return currentCardsSplitable;
    }
    public void disableCurrentCardsSplittable() {
        currentCardsSplitable = false;
    }

    public void setBet(double bet) {
        this.bet += bet;
    }
    public double getBet() {
        return this.bet;
    }
}
