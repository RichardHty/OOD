import Models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BlackJack extends CardGame{

    private int currentPlayerAmount;
    private int mode;
    private int dealerIndex;
    private Scanner scanner ;
    private List<BlackJackPlayer> playerList;
    private BlackJackPlayerOperation op;
    private BlackJackPlayer dealer;

    BlackJack(int numOfPlayers, int mode) {
        this.mode = mode;
        this.currentPlayerAmount = numOfPlayers;
        this.cardGenerator = BlackJackPlayerOperation.defaultCardGenerator();
        this.dealerIndex = 0;
        this.scanner = new Scanner(System.in);
        this.playerList = new ArrayList<>();
    }
    BlackJack(int cardsNumLimit, int cardsKind, int suitKind, int numOfPlayers, int mode) {
        super(cardsNumLimit,cardsKind, suitKind);
        this.currentPlayerAmount = numOfPlayers;
        this.mode = mode;
        this.dealerIndex = 0;
        this.scanner = new Scanner(System.in);
        this.playerList = new ArrayList<>();
    }

    public void start() {
        // set dealer, set user name
        if(mode == Constants.MODE_PLAYER_PLAYER) {
            Random rand = new Random();
            dealerIndex = rand.nextInt(currentPlayerAmount);
        }
        for(int i=0;i<currentPlayerAmount;i++) {
            if(i == dealerIndex && mode == Constants.MODE_PLAYER_COMPUTER){
                playerList.add(new BlackJackPlayer());
                continue;
            }
            System.out.println("What is your name: ");
            playerList.add(new BlackJackPlayer(getInput()));
        }
        playerList.get(dealerIndex).setDealer();
        this.dealer = playerList.get(dealerIndex);

        op = new BlackJackPlayerOperation(playerList.get(dealerIndex),0, cardGenerator);

        displayAllPlayerAssets();
    }
    public boolean play() {

        // deal two cards for each player
        for(int i=0;i<2;i++){
            for(BlackJackPlayer p : playerList) {
                op.setPlayer(p);
                op.dealCard();
            }
        }

        displayDealerCardShowing();
        playerSetBet();
        playerChooseOperation();

        // display result for dealer
        dealerHitUntilFinished();
        System.out.println("For dealer");
        op.setOperationObj(dealer, 0);
        op.displayCardSetInfo();

        //display result for this round
        displayResultThisRound();

        // reset config for current players
        List<BlackJackPlayer> currentPlayers = new ArrayList<>(playerList);
        boolean flag = isAllPlayerQuit(currentPlayers);
        playerList = currentPlayers;
        cardGenerator.reset();
        return flag;
    }

    private String getInput()
    {
        return scanner.nextLine();
    }
    private boolean isOperationInputValid(String passedString) {
        return ("hit".equalsIgnoreCase(passedString)  ||
                "stand".equalsIgnoreCase(passedString) ||
                "split".equalsIgnoreCase(passedString) ||
                "dd".equalsIgnoreCase(passedString) ||
                "double down".equalsIgnoreCase(passedString)) ;
    }

    private void dealerHitUntilFinished() {
        op.setOperationObj(playerList.get(dealerIndex), 0);
        while (playerList.get(dealerIndex).getCurrentCards().get(0).calculateScore() <= 17)
        {
            op.dealCard();
        }
    }
    private List<Boolean> carSetWinnerCalculation(Player p) {
        int dealerScore = playerList.get(dealerIndex).getCurrentCardSet(0).calculateScore();
        List<Boolean> list = new ArrayList<>();
        List<CardSet> cards = p.getCurrentCards();
        for(CardSet cardSet:cards){
            int handScore = cardSet.calculateScore();
            if((handScore == 21 && dealerScore != 21) ||
                    (handScore <  21 && dealerScore < handScore) ||
                    (handScore <  21 && dealerScore > 21)) {
                list.add(true);
            } else {
                list.add(false);
            }
        }
        return list;
    }
    private void playerSetBet() {
        for (BlackJackPlayer p:playerList){
            if(p.isDealer()) {
                continue;
            }
            System.out.print(p.getuName()+", what is your bet? ");
            while(!p.setBet(Double.parseDouble(getInput()),0)){
                System.out.println("Please enter a valid number(you now have "+p.getAsset()+" can be used): ");
            }
        }
    }
    private void playerChooseOperation() {
        int count = playerList.size() - 1;
        boolean[] quit = new boolean[playerList.size()];

        while(count > 0 ){
            for (int i=0;i<playerList.size();i++){
                BlackJackPlayer p = playerList.get(i);
                if (p.isDealer() || quit[i]){
                    continue;
                }

                if(operationForEachCardSet(p)) {
                    quit[i] = true;
                    count --;
                }

            }
        }
    }

    private void displayDealerCardShowing(){
        System.out.println("Dealer is showing:\n"+playerList.get(dealerIndex).getCurrentCardSet(0).getCard(0).toString());
    }
    private boolean isAllPlayerQuit(List<BlackJackPlayer> currentPlayers) {
        boolean flag = true;
        for(BlackJackPlayer p:playerList) {
            p.reset();
            if(p.isDealer() || p.broken() ){
                flag &= true;
                continue;
            }
            System.out.println(p);
            System.out.print("Another round[y/n]? ");
            String input = getInput();
            if("n".equalsIgnoreCase(input) || "no".equalsIgnoreCase(input)) {
                currentPlayers.remove(p);
            } else {
                flag &= false;
            }
        }
        return flag;
    }
    private void displayResultThisRound() {
        for(BlackJackPlayer p:playerList) {
            if(p.isDealer()){
                continue;
            }
            displayWinOrLoss(p);
            displayPlayerAsset(p);
        }
    }
    private void displayPlayerAsset(BlackJackPlayer player){
        System.out.println(player);
    }
    private void displayAllPlayerAssets() {
        System.out.println();
        for(BlackJackPlayer p : playerList) {
            if(p.isDealer()) {
                continue;
            }
            System.out.println(p);
        }
    }

    private boolean operationForEachCardSet(BlackJackPlayer userPlayer){
        String input;
        boolean flag = true;
        int hands = userPlayer.getCurrentCardSetNum();
        op.setOperationObj(userPlayer,0);

        System.out.println(userPlayer.getuName()+", you get");

        for(int i=0;i<hands;i++){
            CardSet cardSet = userPlayer.getCurrentCardSet(i);
            boolean cardSetSplittable = cardSet.isCurrentCardsSplittable();
            op.setCardSetIndex(i);

            if(!cardSet.getStand() && cardSet.calculateScore() >= 21){
                if(cardSet.calculateScore() == 21){
                    flag &= true;
                }
                cardSet.setStand();
                System.out.println("For hand "+(i+1)+",");
                op.displayCardSetInfo();

            }
            if(cardSet.getStand()){
                flag &= true;
                continue;
            }
            System.out.print("For hand "+(i+1)+",");
            op.displayCardSetInfo();

            boolean canBeDouble = userPlayer.getAsset() > cardSet.getBet();
            input = chooseOperation(cardSetSplittable, canBeDouble);

            if ("hit".equalsIgnoreCase(input)) {
                op.hitOperation();
                flag &= false;
            } else if("split".equalsIgnoreCase(input)){
                op.splitOperation();
                flag &= false;
            } else if("dd".equalsIgnoreCase(input) || "double down".equalsIgnoreCase(input)) {
                flag &= op.doubleDownOperation();
            } else if("stand".equalsIgnoreCase(input) || cardSet.getStand()) {
                op.standOperation();
                flag &= true;
            } else {
                System.out.println("Input error");
                flag &= false;
            }

        }
        return flag;

    }
    private String chooseOperation(boolean cardSetSplittable, boolean carSetDouble){
        String input;
        String options = "Hit, stand";
        if (carSetDouble) {
            options += ", double down";
        }
        if (cardSetSplittable) {
            options += ", split";
        }
        options += " ?";
        do {
            System.out.println(options);
            input = getInput();
        } while (!isOperationInputValid(input));
        return input;
    }


    private void displayWinOrLoss(BlackJackPlayer userPlayer){
        List<Boolean> wonCardSetsForThisPlayer = carSetWinnerCalculation(userPlayer);
        int i = 0;
        StringBuilder res = new StringBuilder();
        String head = "Player " + userPlayer.getuName() + ", your card set ";
        res.append(head);
        for(Boolean flag: wonCardSetsForThisPlayer){
            res.setLength(head.length());
            res.append(i+1) ;
            double bet = userPlayer.getCurrentCardSet(i).getBet();
            if (flag) {
                res.append(" wins! You won ");
                userPlayer.updateAsset(i,Constants.WIN);
            } else {
                res.append(" lost. You lost ");
                userPlayer.updateAsset(i,Constants.LOSE);
            }
            res.append(bet);
            System.out.println(res.toString());
            i++;
        }
    }


}
