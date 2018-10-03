package Models;


import com.sun.org.apache.regexp.internal.RE;

import java.util.*;

import static Models.Constants.WIN;

abstract public class Player {
    protected String uName;
    protected double asset;
    protected List<CardSet> currentCards;

    public Player(String user){
        this.uName = user;
        this.asset = 500;
        this.currentCards = new ArrayList<>();
        this.currentCards.add(new CardSet());

    }

    abstract public boolean broken();
    abstract public void reset();

    public String getuName() {
        return uName;
    }

    public double getAsset() {
        return asset;
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


    @Override
    public String toString() {
        return "Hello, " + uName  +
                ", you have " + asset +
                " dollars.";
    }
}
