import Models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BlackJack extends CardGame{

    private int currentPlayerAmount;
    private int mode;
    private int dealerIndex;
    private double pot;
    private Scanner scanner ;


    BlackJack(int cardsNumLimit, int cardsKind, int suitKind, int numOfPlayers, int mode) {
        super(cardsNumLimit,cardsKind, suitKind);
        this.currentPlayerAmount = numOfPlayers;
        this.mode = mode;
        this.dealerIndex = 0;
        this.scanner = new Scanner(System.in);
    }
    public void start() {

        if(mode == Constants.MODE_PLAYER_PLAYER) {
            Random rand = new Random();
            dealerIndex = rand.nextInt(currentPlayerAmount);
        } else if (mode == Constants.MODE_PLAYER_COMPUTER) {

        } else {

        }
        for(int i=0;i<currentPlayerAmount;i++) {
            if(i == dealerIndex && mode == Constants.MODE_PLAYER_COMPUTER){
                players.add(new Player());
                continue;
            }
            System.out.println("What is your name: ");
            players.add(new Player(getInput()));
        }
        players.get(dealerIndex).setDealer();
        System.out.println(players.get(dealerIndex).getuName()+", you are the dealer.");
    }
    private void dealCard(Player playerToReceiveCard, int index){

        Card card = cardGenerator.getACard();
        playerToReceiveCard.addCardToCardSet(card, index);

    }
    private void dealerHitUntilFinished() {
        while (players.get(dealerIndex).getCurrentCards().get(0).calculateScore() <= 17)
        {
            dealCard(players.get(dealerIndex),0);
        }
    }
    private List<Boolean> playerWins(Player p) {
        // if both player and dealer scores are = to 21 dealer wins
        // if both player and dealer score are over 21 then dealer wins
        // if player's score is <= 21 and players score is greater than dealers score then player wins.
        //if dealer's score is > 21 and player's score is <= 21 then players wins.
        int dealerScore = players.get(dealerIndex).getCurrentCardSet(0).calculateScore();
        List<Boolean> list = new ArrayList<>();
        List<CardSet> cards = p.getCurrentCards();
        for(int i=0;i<cards.size();i++){
            int handScore = cards.get(i).calculateScore();
            if((handScore == 21 && dealerScore != 21) ||
                    (handScore <  21 && dealerScore < handScore) ||
                    (handScore <  21 && dealerScore > 21)) {
                //Player wins
                list.add(true);
            } else {
                //Dealer wins
                list.add(false);
            }
        }
        return list;
    }
    private void displayCardSetAndScore(CardSet cardSet){

        for (Card card : cardSet.getCurrentCards())
        {
            System.out.println(card.toString());
        }
        int cardSetScore = cardSet.calculateScore();
        System.out.println("current score for this hand is: " + cardSetScore);
        if(cardSetScore > 21) System.out.println("Score busted.");
        System.out.println();
    }


    private void displayDealerCardShowing(){
        System.out.println("\nDealer is showing:\n"+players.get(dealerIndex).getCurrentCardSet(0).getCard(0).toString());
    }

     public boolean play() {

        for(int i=0;i<2;i++){
            for(Player p : players) {
                dealCard(p, 0);
            }
        }

        displayDealerCardShowing();
        for (Player p:players){
            if(p.isDealer()) {
                continue;
            }
            System.out.print(p.getuName()+", what is your bet? ");
            while(!p.setBet(Double.parseDouble(getInput()),0)){
                System.out.println("Please enter a valid number(you now have "+p.getAsset()+" can be used): ");
            }
        }
        int count = players.size() - 1;
        boolean[] quit = new boolean[players.size()];
//        boolean[] firstTime = new boolean[players.size()];
        while(count > 0 ){
            for (int i=0;i<players.size();i++){
                Player p = players.get(i);
                if (p.isDealer() || quit[i]){
                    continue;
                }
//                if(!firstTime[i]){
//                    System.out.println(p.getuName()+", you get");
//                    for(int j = 0;j<p.getCurrentCardSetNum();j++){
//                        System.out.println("For hand "+(j+1)+",");
//                        displayCardSetAndScore(p.getCurrentCardSet(j));
//                        System.out.print(p.getuName()+", what is your bet? ");
//                        while(!p.setBet(Double.parseDouble(getInput()),0)){
//                            System.out.println("Please enter a valid number(you now have "+p.getAsset()+" can be used): ");
//                        }
//                    }
//                    firstTime[i] = true;
//                }
                if(operationForEachCardSet(p)) {
                    quit[i] = true;
                    count --;
                }

            }
        }

        dealerHitUntilFinished();
        System.out.println("For dealer");
        displayCardSetAndScore(players.get(dealerIndex).getCurrentCardSet(0));
        for(Player p:players) {
            if(p.isDealer()){
                continue;
            }
            determineWinOrLoss(p);
            displayPlayerAsset(p);
        }
        boolean flag = true;
        List<Player> currentPlayers = new ArrayList<>(players);
        for(Player p:players) {
            p.resetCardsAndBet();
            if(p.isDealer() || p.broken() ){
                flag &= true;
                continue;
            }
            System.out.println(p);
            System.out.print("Another round[y/n]? ");
            String input = getInput();
            if("n".equalsIgnoreCase(input) || "no".equalsIgnoreCase(input)) {
                currentPlayers.remove(p);
                count --;
            } else {
                flag &= false;
            }
        }
        players = currentPlayers;
        cardGenerator.reset();
        return flag;
    }
    private void displayPlayerAsset(Player player){
        System.out.println(player);
    }
    private boolean operationForEachCardSet(Player userPlayer){
        String input;
        boolean flag = true;
        int hands = userPlayer.getCurrentCardSetNum();

        System.out.println(userPlayer.getuName()+", you get");
        for(int i=0;i<hands;i++){
            CardSet cardSet = userPlayer.getCurrentCardSet(i);
            boolean cardSetSplittable = cardSet.isCurrentCardsSplittable();

            if(!cardSet.getStand() && cardSet.calculateScore() >= 21){
                if(cardSet.calculateScore() == 21){
                    flag &= true;
                }
                cardSet.setStand();
                System.out.println("For hand "+(i+1)+",");
                displayCardSetAndScore(cardSet);

            }
            if(cardSet.getStand()){
                flag &= true;
                continue;
            }
            System.out.print("For hand "+(i+1)+",");
            displayCardSetAndScore(cardSet);

            input = chooseOperation(cardSetSplittable);

            if ("hit".equalsIgnoreCase(input)) {
                dealCard(userPlayer, i);
                flag &= false;
                displayCardSetAndScore(cardSet);
            } else if("split".equalsIgnoreCase(input)){
                if (userPlayer.splitCurrentCards(i)){
                    dealCard(userPlayer, i);
                    dealCard(userPlayer, i+1);
                    System.out.println("Split succeeded");
                } else {
                    System.out.println("Split failed.");
                }
                flag &= false;
            } else if("dd".equalsIgnoreCase(input)) {
                if(!userPlayer.setBet(cardSet.getBet(), i)){
                    flag &= false;
                    continue;
                }
                dealCard(userPlayer, i);
                cardSet.setStand();
                displayCardSetAndScore(cardSet);
                flag &= true;
            } else if("stand".equalsIgnoreCase(input) || cardSet.getStand()) {
                displayCardSetAndScore(cardSet);
                cardSet.setStand();
                flag &= true;
            } else {
                System.out.println("Input error");
                flag &= false;
            }

        }
        return flag;

    }
    private String chooseOperation(boolean cardSetSplitable){
        String input;
        String options = "\nHit, stand, double down";
        if(cardSetSplitable){
            options += ", split";
        }
        options += " ?";
        do {
            System.out.println(options);
            input = getInput();
        } while (!isInputStayOrHit(input));
        return input;
    }
    private String getInput()
    {
        return scanner.nextLine();
    }
    private boolean isInputStayOrHit(String passedString)
    {
        return ("hit".equalsIgnoreCase(passedString)  ||
                "stand".equalsIgnoreCase(passedString) ||
                "split".equalsIgnoreCase(passedString) ||
                "dd".equalsIgnoreCase(passedString)) ;
    }

    private void determineWinOrLoss(Player userPlayer){
        List<Boolean> wonCardSetsForThisPlayer = playerWins(userPlayer);
        int i = 0;
        for(Boolean flag: wonCardSetsForThisPlayer){
            if (flag) {
                System.out.println("Player "+userPlayer.getuName()+", your card set "+(i+1)+" wins! You won "+userPlayer.getCurrentCardSet(i).getBet());
                userPlayer.updateAsset(i,Constants.WIN);
            } else {
//                System.out.println("Dealer "+players[dealerIndex].getuName()+" wins!");
                System.out.println("Player "+userPlayer.getuName()+", your card set "+(i+1)+" lost. You lost "+userPlayer.getCurrentCardSet(i).getBet());
                userPlayer.updateAsset(i,Constants.LOSE);
            }
            i++;
        }
        int dealerScore = players.get(dealerIndex).getCurrentCardSet(0).calculateScore();
        System.out.println("Dealer has score: " + dealerScore);
    }
    public void displayAllPlayerAssets() {
        System.out.println();
        for(Player p : players) {
            if(p.isDealer()) {
                continue;
            }
            System.out.println(p);
        }
    }
}
