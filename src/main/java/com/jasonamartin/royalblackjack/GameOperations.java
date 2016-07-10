package com.jasonamartin.royalblackjack;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameOperations {

    public enum HandActions {
        DoubleDown,
        Hit,
        Stand,
        Surrender
    };

    public enum RoyalMatchStates {
        ROYAL,
        NORMAL,
        NONE
    }

    public enum Payouts {
        NORMAL (1),
        BLACKJACK (1.5f),
        NORMALROYALMATCH (2.5f),
        ROYALMATCH (25),
        CROWN (1000);

        private final float pay;

        Payouts(float pay) {
            this.pay = pay;
        }

        public float pay() { return pay; }

    }

    public enum Confirmations {
        YES,
        NO
    };

    public void DisplayHandOptions (int handValue){

        if (handValue<21){
            System.out.println ("Hit or stand? Type \"hit\", \"stand\", \"h\" or \"s\"");
        }

        if(handValue==21){
            System.out.println ("You have 21! The game is over. You have won!");
            //	myBlackjack.setGameStatus(2);
        }


    }

    public void GameAction (HandActions action){

        switch (action) {
            case Hit:
                System.out.println("hit 2");
                break;
            case Stand:
                System.out.println("stand 2");
                break;
            case DoubleDown:
                System.out.println("dd");
                break;
            case Surrender:
                System.out.println("surrender");
                break;
            default:
                System.out.println("nothing.");
                break;
        }
    }

    public int insuranceOption (){
        int insuranceStatus=0;
        boolean benchmark = true;
        Scanner in = new Scanner(System.in);
        String myAction;



        while (benchmark){
            myAction = in.next();
            try {

                if(myAction.equalsIgnoreCase("no")){
                    // player doesn't want insurance
                    System.out.println ("<--insurance declined-->");
                    insuranceStatus=0;
                    benchmark=false;
                }

                if(myAction.equalsIgnoreCase("yes")){
                    System.out.println ("[--insurance taken--]");
                    insuranceStatus=1;
                    benchmark=false;
                }

            }




            catch (InputMismatchException e){
                System.out.println ("Would you like insurance? Type \"yes\" or \"no\"");
            }
            catch (NumberFormatException e ){
                System.out.println ("Would you like insurance? Type \"yes\" or \"no\"");

            }

        }//end while
        return insuranceStatus;
    }

    public String RequestName (){
        String hasName = "";
        boolean benchmark = true;
        Scanner in = new Scanner(System.in);


        while (benchmark){
            System.out.println ("Hey partner. Please enter a name for yourself.");

            hasName = in.nextLine();

            try {

                if(!hasName.equalsIgnoreCase("")){
                    benchmark=false;
                }
            }
            catch (InputMismatchException e){
                System.out.println ("Please enter your name . . .");
            }
            catch (NumberFormatException e ){
                System.out.println ("Please enter your name . . .");

            }
        }
        return hasName;
    }


    public int RequestRoyalMatch (int bankroll, int currentWager){
        int wantsRoyalMatch=0;
        boolean benchmark = true;
        Scanner in = new Scanner(System.in);
        int userEntry;

        while (benchmark){
            System.out.println (String.format("Would you like to wager on the Royal Match? \nEnter zero (0) if you don't. Otherwise, enter the wager amount without the dollar sign (example: %d).", bankroll - currentWager));
            System.out.println ("A normal Royal Match win current pays 2:1 and royal pays 15:1!");
            try {
                userEntry=Integer.parseInt(in.nextLine());
                if(userEntry==0){
                    //player is passing on RoyalMatch
                    System.out.println ("Ok, maybe you'll try Royal Match next time . . .");
                    benchmark=false;
                }else{
                    //player wants RoyalMatch. If desired bet is under total bankroll by at least $1, place bet.
                    if (userEntry <= bankroll) {
                        benchmark = false;
                        wantsRoyalMatch = userEntry;
                    } else {
                        benchmark = true;
                        System.out.print(String.format("You can't bet more than you have. \nYou have $%d. \n", bankroll - currentWager));
                    }
                }
            }
            catch (InputMismatchException e){
                System.out.println ("Please enter a dollar amount . . .");
            }
            catch (NumberFormatException e ){
                System.out.println ("Please enter a dollar amount . . .");

            }
        }
        return wantsRoyalMatch;
    }

    public RoyalMatchStates RoyalMatchCheck (String card1, String card2){
        RoyalMatchStates matchResult;
        String dealtSet = new StringBuilder().append(card1.charAt(1)).append(card2.charAt(1)).toString();
        String[] royalMatch = { "kq", "qk" };
        // If suits are not the same, it's a loss.
        if (card1.charAt(0) == card2.charAt(0)) {
            // suit match
            matchResult = RoyalMatchStates.NORMAL;
            for (String cardSet : royalMatch) {
                if (cardSet.equals(dealtSet)){
                    matchResult = RoyalMatchStates.ROYAL;
                }
            }
        } else {
            matchResult = RoyalMatchStates.NONE;
        }
        return matchResult;
    }
}
