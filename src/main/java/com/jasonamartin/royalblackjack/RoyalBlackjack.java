package com.jasonamartin.royalblackjack;

/*
 * Blackjack 0.2 Jason A. Martin
 *
 * Currently in console version.
 * Last updates:
 * Royal Match added. Pays 2-1 for regular suit match and 15-1 for King/Queen match
 * Fixed issue with hands pushing
 * Added name
 *
 * To Do:
 * Add ability to double down
 * Add ability to split hand
 * Add ability to surrender
 * Clean up code for easier functionality
 * - disable all the println
 * start building interface
 *
 * Defects:
 * 1. user can bet unlimited money for Royal Match
 * 2. If user bets more than they have for hand, it doesn't let them know the bet failed.
 *
 */

import java.util.Scanner;
import java.util.InputMismatchException;

public class RoyalBlackjack {



    static int gameStatus=1;

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        Scanner in = new Scanner(System.in);
        int myWager;
        String myAction;
        GameOperations myGame = new GameOperations();
        CardDeck myCards = new CardDeck();
        //int checkInput = 0;
        int insuranceCheck =0;
        String statusRoyalMatch;
        //create player
        Players thePlayer = new Players();

        //create dealer

        Dealer theDealer = new Dealer();

        //setup starting bankRoll

        thePlayer.setBankRoll(5000, 'a');

        //setup gamestatus = 1 for new game

        thePlayer.setGameStatus(1);

        //get the player's name

        thePlayer.setMyName(myGame.RequestName());
        System.out.println ("Great. I'll call you "+thePlayer.getMyName());

        while (thePlayer.getGameStatus()==1){
            //main loop
            boolean benchmark1 = true;
            boolean benchmark2 = true;
            theDealer.resetBlackjack();
            thePlayer.resetBlackjack();
            //1. how much money does player have money?


            System.out.println ("You currently have $" +thePlayer.getBankRoll()+" in your bankroll");

            //2. ask for wager and verify it's acceptable

            System.out.println ("How much would you like to wager (do not include dollar symbol)? If you'd like to quit, just enter a zero (0).");
            //myWager=in.nextInt();


            //verify integer is given

            //boolean benchmark1 = true;
            while (benchmark1){

                try {
                    myWager=Integer.parseInt(in.nextLine());
                    //check to see if player has the cash
                    if (myWager<=thePlayer.getBankRoll()){
                        benchmark1=false;
                        if(myWager==0){
                            System.out.println ("Thanks for playing! You finished with $"+thePlayer.getBankRoll());
                            System.exit(0);

                        }
                    }else{
                        //player doesn't have the money
                        System.out.println ("You can't wager that much. You only have $"+thePlayer.getBankRoll());
                    }
                    thePlayer.setCurrentWager(myWager);
                }
                catch (InputMismatchException e){
                    //System.out.println ("How much would you like to wager (do not include dollar symbol)?");
                }
                catch (NumberFormatException e ){
                    // System.out.println ("How much would you like to wager (do not include dollar symbol)?");
                }

            } //end try, catch

            //2.5 Check if player wants RoyalMatch

            thePlayer.setRoyalMatchWager(myGame.RequestRoyalMatch());



            //3. call shufflecards to shuffle

            myCards.ShuffleCards(4);

            //3.1 reset player and dealer hand values
            thePlayer.setHandValue("reset");
            theDealer.setHandValue("reset");


            //4. deal cards, update hand values, show dealer's up card.
            thePlayer.setHandValue(myCards.cardShoe.get(0));
            thePlayer.setHandValue(myCards.cardShoe.get(2));


            //4.1 check if cards match for Royal Match

            statusRoyalMatch = myGame.RoyalMatchCheck(myCards.cardShoe.get(0), myCards.cardShoe.get(2));

            //4.11 add or subtract money

            if(statusRoyalMatch.equalsIgnoreCase("lose")) {

                if(thePlayer.getRoyalMatchWager()>0){
                    thePlayer.setBankRoll(thePlayer.getRoyalMatchWager(), 's');
                    System.out.println ("Oh well, easy come, easy go. You lost $" +thePlayer.getRoyalMatchWager()+" playing the Royal Match.");
                }else{
                    //player didn't wager on the Royal Match
                    System.out.println ("Good thing you didn't wager on Royal Match. You would have lost.");
                }

            }

            if(statusRoyalMatch.equalsIgnoreCase("win")) {
                //regular royal match win, pays 2x bet
                thePlayer.setBankRoll(thePlayer.getRoyalMatchWager()*2, 'a');
                System.out.println ("I just added $" + thePlayer.getRoyalMatchWager()*2+" to your bank account!");

            }

            if(statusRoyalMatch.equalsIgnoreCase("royalwin")) {
                //Royal Match special King/Queen, pays 15x bet
                thePlayer.setBankRoll(thePlayer.getRoyalMatchWager()*15, 'a');
                System.out.println ("I just added $" + thePlayer.getRoyalMatchWager()*15+" to your bank account!");

            }


            //4.2 remove cards used

            System.out.println(myCards.cardShoe.get(0));
            System.out.println (myCards.cardShoe.get(2));



            if(thePlayer.getAces()>0){
                System.out.println("You have aces!");
                System.out.println ("Your hand can be: "+thePlayer.getHandValue()+" or "+(thePlayer.getHandValue()-10));
            }else{
                System.out.println ("You were dealt " + thePlayer.getHandValue());

            }

            myCards.cardShoe.remove(0);
            myCards.cardShoe.remove(3);

            //dealer stuff here!!
            theDealer.setHandValue(myCards.cardShoe.get(1));
            theDealer.setHandValue(myCards.cardShoe.get(3));

            System.out.println ("The dealer is showing a " + myCards.cardShoe.get(1));


            //is dealer showing ace? If so, insurance option

            if(myCards.cardShoe.get(1).equalsIgnoreCase("da") || myCards.cardShoe.get(1).equalsIgnoreCase("ha") || myCards.cardShoe.get(1).equalsIgnoreCase("ca") || myCards.cardShoe.get(1).equalsIgnoreCase("ca") ) {
                //ace is showing
                System.out.println ("DEALER ACE!!!****!!!****!!!****!!!***");
                System.out.println ("Would you like insurance? Type \"yes\" or \"no\"");

                //call insurance question method
                insuranceCheck= myGame.insuranceOption();
                //a insuranceStatus=1 is for insurance wanted.
                if(insuranceCheck==1){
                    //player wants insurance. Check it player has cash for it. If so, proceed. If not, give notice.

                    if(thePlayer.getBankRoll()>=thePlayer.getCurrentWager()/2){
                        //player has cash for insurance.

                        //check for 10 and remove money if needed.
                        //if(myCards.cardShoe.get(3).equalsIgnoreCase("sk") || myCards.cardShoe.get(3).equalsIgnoreCase("sq") || myCards.cardShoe.get(3).equalsIgnoreCase("sj") || myCards.cardShoe.get(3).equalsIgnoreCase("hk") || myCards.cardShoe.get(3).equalsIgnoreCase("hq") || myCards.cardShoe.get(3).equalsIgnoreCase("hj") || myCards.cardShoe.get(3).equalsIgnoreCase("ck") || myCards.cardShoe.get(3).equalsIgnoreCase("cq") || myCards.cardShoe.get(3).equalsIgnoreCase("cj") || myCards.cardShoe.get(3).equalsIgnoreCase("dk") || myCards.cardShoe.get(3).equalsIgnoreCase("dq") || myCards.cardShoe.get(3).equalsIgnoreCase("dj") || myCards.cardShoe.get(3).equalsIgnoreCase("d10") || myCards.cardShoe.get(3).equalsIgnoreCase("h10") || myCards.cardShoe.get(3).equalsIgnoreCase("c10") || myCards.cardShoe.get(3).equalsIgnoreCase("c10") ) {
                        if(theDealer.getHandValue()==21){
                            //dealer has BJ. Game over.
                            System.out.println("com.jasonamartin.royalblackjack.Dealer has Blackjack. You lost the hand, but kept your cash.");
                            benchmark2 = false;
                            theDealer.setBlackjack(1);


                        }else{
                            System.out.println ("com.jasonamartin.royalblackjack.Dealer didn't have Blackjack. You lose $"+thePlayer.getCurrentWager()/2);
                            thePlayer.setBankRoll(thePlayer.getCurrentWager()/2, 's');
                        }

                    }else{
                        //player can't do it.
                        System.out.println ("I'm sorry. You don't have the cash for insurance.");
                    }

                }else{
                    //playher declined insurance
                    System.out.println ("WOW! You declined insurance. Sweet.");
                    //if(myCards.cardShoe.get(3).equalsIgnoreCase("sk") || myCards.cardShoe.get(3).equalsIgnoreCase("sq") || myCards.cardShoe.get(3).equalsIgnoreCase("sj") || myCards.cardShoe.get(3).equalsIgnoreCase("hk") || myCards.cardShoe.get(3).equalsIgnoreCase("hq") || myCards.cardShoe.get(3).equalsIgnoreCase("hj") || myCards.cardShoe.get(3).equalsIgnoreCase("ck") || myCards.cardShoe.get(3).equalsIgnoreCase("cq") || myCards.cardShoe.get(3).equalsIgnoreCase("cj") || myCards.cardShoe.get(3).equalsIgnoreCase("dk") || myCards.cardShoe.get(3).equalsIgnoreCase("dq") || myCards.cardShoe.get(3).equalsIgnoreCase("dj") || myCards.cardShoe.get(3).equalsIgnoreCase("d10") || myCards.cardShoe.get(3).equalsIgnoreCase("h10") || myCards.cardShoe.get(3).equalsIgnoreCase("c10") || myCards.cardShoe.get(3).equalsIgnoreCase("c10") ) {
                    if(theDealer.getHandValue()==21){

                        //dealer has BJ. Game over.
                        System.out.println("com.jasonamartin.royalblackjack.Dealer has Blackjack. Should of taken INSURANCE. LOL");
                        benchmark2 = false;
                        theDealer.setBlackjack(0);
                    }


                }
            }

            //remove dealer's cards used
            myCards.cardShoe.remove(1);
            myCards.cardShoe.remove(4);

            //4.5 if dealer has a 10 showing, see if handvalue is 21. If so, hand over. money lost.


            if(myCards.cardShoe.get(1).equalsIgnoreCase("sk") || myCards.cardShoe.get(1).equalsIgnoreCase("sq") || myCards.cardShoe.get(1).equalsIgnoreCase("sj") || myCards.cardShoe.get(1).equalsIgnoreCase("hk") || myCards.cardShoe.get(1).equalsIgnoreCase("hq") || myCards.cardShoe.get(1).equalsIgnoreCase("hj") || myCards.cardShoe.get(1).equalsIgnoreCase("ck") || myCards.cardShoe.get(1).equalsIgnoreCase("cq") || myCards.cardShoe.get(1).equalsIgnoreCase("cj") || myCards.cardShoe.get(1).equalsIgnoreCase("dk") || myCards.cardShoe.get(1).equalsIgnoreCase("dq") || myCards.cardShoe.get(1).equalsIgnoreCase("dj") || myCards.cardShoe.get(1).equalsIgnoreCase("d10") || myCards.cardShoe.get(1).equalsIgnoreCase("h10") || myCards.cardShoe.get(1).equalsIgnoreCase("c10") || myCards.cardShoe.get(1).equalsIgnoreCase("c10") ) {
                //a 10 or face card is showing!
                if(theDealer.getHandValue()==21){
                    //dealer has 21 game over. Take cash!
                    System.out.println("Talk about SUCK. com.jasonamartin.royalblackjack.Dealer had sneaky Blackjack!");
                    benchmark2 = false;
                    theDealer.setBlackjack(0);
                }
            }

            //5. ask player to hit or stand

            //System.out.println ("Would you like to hit or stand? Type \"hit\" or \"stand\"");

            //verify string is given

            // boolean benchmark2 = true;
            while (benchmark2){
                System.out.println ("Would you like to hit or stand? Type \"hit\" or \"stand\"");


                try {
                    myAction = in.next();
                    if (myAction.equalsIgnoreCase("hit")) {
                        //player wants a card

                        thePlayer.setHandValue(myCards.cardShoe.get(0));
                        myCards.cardShoe.remove(0);
                        System.out.println ("Your new hand value is: " + thePlayer.getHandValue());
                        if(thePlayer.getHandValue()>21){

                            //check to see if player has ace. If so, reduce one and hand by 10 and proceed
                            if(thePlayer.getAces()>0){
                                //aces! setAces to reduce count by 1 and deduct 10 from hand value.
                                thePlayer.setAces();
                                //sending "aces" tells program that -10 needs to be given.
                                thePlayer.setHandValue("xx10");

                            }else{
                                // there are no aces. Player busted.

                                System.out.println ("You lost $"+thePlayer.getCurrentWager()+"!");
                                benchmark2=false;
                                //thePlayer.setGameStatus(2);
                            }
                        }

                    }

                    if (myAction.equalsIgnoreCase("stand") || myAction.equalsIgnoreCase("s")) {
                        benchmark2=false;
                    }

                }
                catch (InputMismatchException e){
                    System.out.println ("Would you like to hit or stand? Type \"hit\" or \"stand\"");
                }
                catch (NumberFormatException e ){
                    System.out.println ("Would you like to hit or stand? Type \"hit\" or \"stand\"");
                }

            } //end try, catch

            //6. run playerDecision, which either ends players' turn or gives card

            if(thePlayer.getGameStatus()==1){
                System.out.println ("Game on");
                System.out.println ("The dealer has "+ theDealer.getHandValue());

                //7. dealer action

                while (theDealer.checkAction(theDealer.getHandValue())!=2){
                    System.out.println ("The dealer's action is: " +theDealer.checkAction(theDealer.getHandValue()));
                    //get card
                    theDealer.setHandValue(myCards.cardShoe.get(0));
                    myCards.cardShoe.remove(0);


                }

                System.out.println("The dealer's FINAL total is: "+ theDealer.getHandValue());
            }

            if(thePlayer.getGameStatus()==2){
                System.out.println ("game off");

            }



            //8. Compare hands to see who won and pay up or deduct

            //if game ended early due to dealer BJ
            if (theDealer.getBlackjack()==1){
                //dealer has blackjack. Does player have insurance? If so, do nothing.

                if (theDealer.hasInsurance()==1){
                    //player has insurance
                    System.out.println ("Lucky you took insurance. No harm.");

                }else{
                    //player didn't take insurance
                    System.out.println ("You should have taken insurance. Sorry.");
                    thePlayer.setBankRoll(thePlayer.getCurrentWager(),'s');
                }


            }else{


                if(thePlayer.getHandValue()>21){
                    //player busted out
                    thePlayer.setBankRoll(thePlayer.getCurrentWager(),'s');
                    System.out.println ("You have LOST $"+thePlayer.getCurrentWager());
                }else{
                    //check to see who won

                    if(theDealer.getHandValue()>21){
                        //dealer bust
                        thePlayer.setBankRoll(thePlayer.getCurrentWager(),'a');
                        System.out.println("WINNER! You've won $"+thePlayer.getCurrentWager());
                    }else{
                        //dealer still in

                        if (thePlayer.getHandValue()>theDealer.getHandValue()){
                            //player wins
                            thePlayer.setBankRoll(thePlayer.getCurrentWager(),'a');
                            System.out.println("WINNER! You've beaten the dealer and won $"+thePlayer.getCurrentWager());

                        }else if (thePlayer.getHandValue()<theDealer.getHandValue()){
                            //dealer wins
                            thePlayer.setBankRoll(thePlayer.getCurrentWager(),'s');
                            System.out.println ("com.jasonamartin.royalblackjack.Dealer smoked you. You have LOST $"+thePlayer.getCurrentWager());
                        }else{
                            //push
                            System.out.println ("It's a PUSH! No one wins.");

                        }


                    }

                }

            }//end deal blackjack if else


            //9. if money is gone, setGameStatus(2) otherwise loop to 1

            if(thePlayer.getBankRoll()<1){
                //the player is out of cash. Wrap it up!
                thePlayer.setGameStatus(2);
                System.out.println ("Thanks for playing! You finished with $"+thePlayer.getBankRoll());
            }

            //end main loop

        }


    }//end main


}
