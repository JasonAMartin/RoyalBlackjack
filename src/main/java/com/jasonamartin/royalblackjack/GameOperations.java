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
        String hasName="";
        boolean benchmark = true;
        Scanner in = new Scanner(System.in);


        while (benchmark){
            System.out.println ("Hey partner. Please enter a name for yourself.");

            hasName = in.next();

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


    public int RequestRoyalMatch (){
        int wantsRoyalMatch=0;
        boolean benchmark = true;
        Scanner in = new Scanner(System.in);
        int userEntry;

        while (benchmark){
            System.out.println ("Would you like to wager on the Royal Match? Enter zero (0) if you don't. Otherwise, enter the wager amount without the dollar sign.");
            System.out.println ("A normal Royal Match win current pays 2:1 and royal pays 15:1!");



            try {
                userEntry=Integer.parseInt(in.nextLine());
                if(userEntry==0){
                    //player is passing on RoyalMatch
                    System.out.println ("Ok, maybe you'll try Royal Match next time . . .");
                    benchmark=false;
                }else{
                    //player wants RoyalMatch, so return dollar amount.
                    benchmark=false;
                    wantsRoyalMatch=userEntry;
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

    public String RoyalMatchCheck (String card1, String card2){
        String matchResult="";
        char suit1;
        char suit2;
        char cardType1;
        char cardType2;
        int royalMatch=0;

        suit1 = card1.charAt(0);
        suit2 = card2.charAt(0);
        cardType1 = card1.charAt(1);
        cardType2 = card2.charAt(1);

        //check for king/queen matches=2 means
        if(cardType1=='q' && cardType2=='k') royalMatch=1;
        if(cardType1=='k' && cardType2=='q') royalMatch=1;

        if(suit1==suit2){
            //is this a ROYAL match (king/queen) or regular match?

            if(royalMatch==1){
                //it's a Royal!
                System.out.println("ROYAL MATCH WIN WIN WIN WIN!!!!");
                matchResult = "royalwin";
            }else{
                //nope. Just regular royal match win
                System.out.println("You won your royal match wager (regular match)");
                matchResult = "win";
            }



        }else {
            System.out.println ("You lost the Royal Match --- hahaha");
            matchResult= "lose";
        }


        return matchResult;
    }

}
