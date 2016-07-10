package com.jasonamartin.royalblackjack;

public class Players {

    private int handValue;
    private int gameStatus;
    private int bankRoll;
    private int currentWager;
    private int hasAces;
    private int hasBlackjack;
    private int hasInsurance;
    private String myName;
    private int currentRoyalMatchWager;
    private int startingCapital = 1000;

    public enum MoneyTransactionTypes {
        ADD,
        SUBTRACT
    }

    public void setHandValue (String a) {
        char cardCheck = a.charAt(1);
        //reset value
        if (a.equalsIgnoreCase("reset")){
            handValue=0;
        }
        if (a.equalsIgnoreCase("xx10")){
            System.out.println("ACES XXXXX. Value was: "+handValue);
            handValue=handValue-10;
            System.out.println("Aces deducted. Value is now: "+handValue);
        }

        switch (a.charAt(1)){
            case '1':
                handValue=handValue+10;
                break;
            case 'j':
                handValue=handValue+10;
                break;
            case 'q':
                handValue=handValue+10;
                break;
            case 'k':
                handValue=handValue+10;
                break;
            case 'a':
                handValue=handValue+11;
                hasAces++;
                break;
            case '2':
                handValue=handValue+2;
                break;
            case '3':
                handValue=handValue+3;
                break;
            case '4':
                handValue=handValue+4;
                break;
            case '5':
                handValue=handValue+5;
                break;
            case '6':
                handValue=handValue+6;
                break;
            case '7':
                handValue=handValue+7;
                break;
            case '8':
                handValue=handValue+8;
                break;
            case '9':
                handValue=handValue+9;
                break;
            default:
                break;

        }
    }

    public int getHandValue (){
        return handValue;
    }

    public void setGameStatus (int b) { gameStatus=b; }

    public int getGameStatus (){
        return gameStatus;
    }


    public void setBankRoll (int money, MoneyTransactionTypes transaction) {
        switch (transaction) {
            case ADD:
                bankRoll += money;
                break;
            case SUBTRACT:
                bankRoll -= money;
        }
    }


    public int getBankRoll () {
        return bankRoll;
    }

    public void setCurrentWager (int w){
        currentWager = w;
    }

    public int getCurrentWager (){
        return currentWager;
    }
    public int getAces () {
        return hasAces;
    }

    public void setAces (){
        System.out.println ("You HAD "+ hasAces + " ace(S)");
        hasAces=hasAces-1;
        System.out.println ("You now have: " + hasAces + " aces");
    }
    public int getBlackjack () {
        return hasBlackjack;
    }

    public int hasInsurance (){
        return hasInsurance;
    }
    public void setBlackjack (int i){
        hasInsurance=i;
        hasBlackjack = 1;
    }
    public void resetBlackjack (){
        hasBlackjack=0;
        hasInsurance=0;
        hasAces=0;
        currentRoyalMatchWager=0;

    }

    public void setMyName (String name){
        myName = name;
    }

    public String getMyName (){
        return myName;
    }
    public void setRoyalMatchWager (int cash){
        currentRoyalMatchWager = cash;
    }

    public int getRoyalMatchWager (){
        return currentRoyalMatchWager;
    }

    public int getStartingCapital() { return startingCapital; }
}
