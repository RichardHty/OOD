package Models;


public class Player {
    private String uName;
    private double asset;
    private int score;
    private double bet;
    private boolean isDealer;

    public Player(String user, int bet){
        this.uName = user;
        this.asset = 500;
        this.score = 0;
        this.bet   = bet;
        this.isDealer = false;
    }

    private boolean broken() {
        return asset <= 0;
    }
    public void resetScoreAndBet() {
        this.score = 0;
        this.bet = 0;
    }
    public String getuName() {
        return uName;
    }

    public double getAsset() {
        return asset;
    }

    public int getScore() {
        return score;
    }

    public double getBet() {
        return bet;
    }

    public boolean isDealer() {
        return isDealer;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public void setDealer() {
        isDealer = true;
    }

    public void updateScore(int score) {
        this.score = score;
    }

    public void updateAsset(int type) {

        if(type == Constants.WIN) {
            asset += bet;
        } else if(type == Constants.LOSE){
            asset -= bet;
            asset = asset<0? 0:asset;
        }
    }

    @Override
    public String toString() {
        return "Hello," + uName  +
                ", you have " + asset +
                " dollars.";
    }
}
