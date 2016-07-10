package com.jasonamartin.royalblackjack;

public class Dealer extends Players {

    public int checkAction (int currentHandValue){
        // action based on hand value. 1 is hit and 2 is stand
        // Dealer hits on 2 - 16 and stands on all 17+
        System.out.println(String.format("Sending in dealer HV: %d", currentHandValue));
        int actionCode = 0;
        if (currentHandValue >= 2 && currentHandValue <= 16) {
            actionCode = 1;
        }
        if (currentHandValue >= 17) {
            actionCode = 2;
        }
        return actionCode;
    }
}
