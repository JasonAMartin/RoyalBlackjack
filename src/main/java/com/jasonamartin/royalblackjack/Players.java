package com.jasonamartin.royalblackjack;

class Players {

    private int handValue;
    private GameStatus gameStatus;
    private int bankRoll;
    private int currentWager;
    private int hasAces;
    private int hasBlackjack;
    private int hasInsurance;
    private String myName;
    private int currentRoyalMatchWager;

    enum MoneyTransactionTypes {
        ADD,
        SUBTRACT
    }

    enum GameStatus {
        PLAYING,
        QUIT,
        BLACKJACK
    }

    void setHandValue (String hand) {
        if (hand.equalsIgnoreCase("xx10")){
            handValue -= 10;
        }

        handValue += CardDeck.GetHandValue(hand);

        if (hand.charAt(1) == 'a') { hasAces++; }
    }

    void resetHandValue ()  { handValue = 0; }

    int getHandValue () { return handValue; }

    void setGameStatus (GameStatus status) { gameStatus = status; }

    GameStatus getGameStatus () { return gameStatus; }


    void setBankRoll (float money, MoneyTransactionTypes transaction) {
        switch (transaction) {
            case ADD:
                bankRoll += money;
                break;
            case SUBTRACT:
                bankRoll -= money;
        }
    }

    int getBankRoll () { return bankRoll; }

    void setCurrentWager (int wager) { currentWager = wager; }

    int getCurrentWager (){ return currentWager; }

    int getAces () { return hasAces; }

    void setAces (){ hasAces -= 1; }

    int getBlackjack () { return hasBlackjack; }

    int hasInsurance (){ return hasInsurance; }

    void setBlackjack (int insurance){
        hasInsurance=insurance;
        hasBlackjack = 1;
    }

    void resetBlackjack (){
        hasBlackjack=0;
        hasInsurance=0;
        hasAces=0;
        currentRoyalMatchWager=0;
    }

    void setMyName (String name){ myName = name; }

    String getMyName (){ return myName; }

    void setRoyalMatchWager (int cash){ currentRoyalMatchWager = cash; }

    float getRoyalMatchWager (){ return currentRoyalMatchWager; }

    int getStartingCapital() { return 5000; }
}
