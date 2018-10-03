package Models;

import java.util.ArrayList;

import static Models.Constants.WIN;

public class BlackJackPlayer extends Player {
    private double bet;
    private boolean isDealer;
    public BlackJackPlayer() {
        super("Dealer");
        this.bet = 0;
        this.isDealer = true;
    }
    public BlackJackPlayer(String user) {
        super(user);
        this.bet = 0;
        this.isDealer = false;
    }
    public double getBet(){
        return bet;
    }
    public boolean isDealer() {
        return isDealer;
    }
    public void setDealer() {
        System.out.println(uName + ", you are the dealer.");
        isDealer = true;
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
    public void updateAsset(int index, int type) {
        if(type == WIN)
            this.asset += 2 * currentCards.get(index).getBet();
    }
    @Override
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
    @Override
    public void reset(){
        this.currentCards = new ArrayList<>();
        this.currentCards.add(new CardSet());
        this.bet = 0;
    }

}
