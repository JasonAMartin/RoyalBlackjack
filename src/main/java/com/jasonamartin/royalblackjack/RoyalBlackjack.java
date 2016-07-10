package com.jasonamartin.royalblackjack;

/*
 * Blackjack 0.2 Jason A. Martin
 *
 * Features Coming: /docs/Development.md
 * Known Defects: /docs/Defects.md
 *
 */

import java.util.Scanner;
import java.util.InputMismatchException;
import com.jasonamartin.royalblackjack.GameOperations.RoyalMatchStates;
import com.jasonamartin.royalblackjack.Players.MoneyTransactionTypes;
import com.jasonamartin.royalblackjack.Players.GameStatus;

public class RoyalBlackjack {

    static int gameStatus=1;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int myWager;
        String myAction;
        GameOperations rbjGame = new GameOperations();
        CardDeck deckOfCards = new CardDeck();
        int insuranceCheck = 0;
        RoyalMatchStates statusRoyalMatch;
        MoneyTransactionTypes addMoney = MoneyTransactionTypes.ADD;
        MoneyTransactionTypes deductMoney = MoneyTransactionTypes.SUBTRACT;
        // create player
        Players thePlayer = new Players();

        // create dealer

        Dealer theDealer = new Dealer();

        // setup starting bankRoll

        thePlayer.setBankRoll(thePlayer.getStartingCapital(), addMoney);

        // setup gamestatus = .PLAYING for new game

        thePlayer.setGameStatus(GameStatus.PLAYING);

        // get the player's name

        thePlayer.setMyName(rbjGame.RequestName());
        System.out.println ("Great. I'll call you "+thePlayer.getMyName());

        while (thePlayer.getGameStatus() == GameStatus.PLAYING){
            // main loop
            boolean wagerDialog = true;
            boolean gameplayDialog = true;
            theDealer.resetBlackjack();
            thePlayer.resetBlackjack();
            //1. how much money does player have money?
            System.out.println (String.format("You currently have $%d in your bankroll", thePlayer.getBankRoll()));

            // TODO: Move wager dialog to GameOperations.
            while (wagerDialog){

                try {
                    System.out.println ("How much would you like to wager (do not include dollar symbol)? If you'd like to quit, just enter a zero (0).");
                    myWager=Integer.parseInt(in.nextLine());
                    //check to see if player has the cash
                    if (myWager<=thePlayer.getBankRoll()){
                        wagerDialog =false;
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
                    // System.out.println ("How much would you like to wager (do not include dollar symbol)?");
                }
                catch (NumberFormatException e ){
                    // System.out.println ("How much would you like to wager (do not include dollar symbol)?");
                }

            } //end try, catch

            //2.5 Check if player wants RoyalMatch

            thePlayer.setRoyalMatchWager(rbjGame.RequestRoyalMatch(thePlayer.getBankRoll(), thePlayer.getCurrentWager()));



            // 3. call shufflecards to shuffle

            deckOfCards.ShuffleCards(1);

            // 3.1 reset player and dealer hand values
            thePlayer.resetHandValue();
            theDealer.resetHandValue();


            // 4. deal cards, update hand values, show dealer's up card.
            thePlayer.setHandValue(deckOfCards.cardShoe.get(0));
            thePlayer.setHandValue(deckOfCards.cardShoe.get(2));


            // 4.1 check if cards match for Royal Match

            statusRoyalMatch = rbjGame.RoyalMatchCheck(deckOfCards.cardShoe.get(0), deckOfCards.cardShoe.get(2));

            // 4.11 add or subtract money
            float payout;
            switch (statusRoyalMatch) {
                case NORMAL:
                    payout = (thePlayer.getRoyalMatchWager() * GameOperations.Payouts.NORMALROYALMATCH.pay());
                    thePlayer.setBankRoll(payout, addMoney);
                    System.out.println (String.format("I just added $%.0f to your bank account!", payout));
                    break;
                case ROYAL:
                    payout = thePlayer.getRoyalMatchWager() * GameOperations.Payouts.ROYALMATCH.pay();
                    thePlayer.setBankRoll(payout, addMoney);
                    System.out.println (String.format("I just added $%.0f to your bank account!", payout));
                    break;
                case NONE:
                    if(thePlayer.getRoyalMatchWager()>0){
                        thePlayer.setBankRoll(thePlayer.getRoyalMatchWager(), deductMoney);
                        System.out.println (String.format("Oh well, easy come, easy go. You lost $%.0f playing the Royal Match.", thePlayer.getRoyalMatchWager()));
                    }else{
                        //player didn't wager on the Royal Match
                        System.out.println ("Good thing you didn't wager on Royal Match. You would have lost.");
                    }
                    break;
                default:
                    System.out.println("error");
                    break;
            }


            // 4.2 remove cards used

            System.out.println(deckOfCards.cardShoe.get(0));
            System.out.println (deckOfCards.cardShoe.get(2));



            if(thePlayer.getAces()>0){
                System.out.println("You have aces!");
                System.out.println ("Your hand can be: "+thePlayer.getHandValue()+" or "+(thePlayer.getHandValue()-10));
            }else{
                System.out.println ("You were dealt " + thePlayer.getHandValue());

            }

            deckOfCards.cardShoe.remove(0);
            deckOfCards.cardShoe.remove(3);

            // dealer stuff here!!
            theDealer.setHandValue(deckOfCards.cardShoe.get(1));
            theDealer.setHandValue(deckOfCards.cardShoe.get(3));

            System.out.println ("The dealer is showing a " + deckOfCards.cardShoe.get(1));


            // is dealer showing ace? If so, insurance option

            if(deckOfCards.cardShoe.get(1).equalsIgnoreCase("da") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("ha") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("ca") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("ca") ) {
                // ace is showing
                System.out.println ("DEALER ACE!!!****!!!****!!!****!!!***");
                System.out.println ("Would you like insurance? Type \"yes\" or \"no\"");

                // call insurance question method
                insuranceCheck= rbjGame.insuranceOption();
                // a insuranceStatus=1 is for insurance wanted.
                if(insuranceCheck==1){
                    // player wants insurance. Check it player has cash for it. If so, proceed. If not, give notice.

                    if(thePlayer.getBankRoll()>=thePlayer.getCurrentWager()/2){
                        // player has cash for insurance.

                        // check for 10 and remove money if needed.
                        // if(myCards.cardShoe.get(3).equalsIgnoreCase("sk") || myCards.cardShoe.get(3).equalsIgnoreCase("sq") || myCards.cardShoe.get(3).equalsIgnoreCase("sj") || myCards.cardShoe.get(3).equalsIgnoreCase("hk") || myCards.cardShoe.get(3).equalsIgnoreCase("hq") || myCards.cardShoe.get(3).equalsIgnoreCase("hj") || myCards.cardShoe.get(3).equalsIgnoreCase("ck") || myCards.cardShoe.get(3).equalsIgnoreCase("cq") || myCards.cardShoe.get(3).equalsIgnoreCase("cj") || myCards.cardShoe.get(3).equalsIgnoreCase("dk") || myCards.cardShoe.get(3).equalsIgnoreCase("dq") || myCards.cardShoe.get(3).equalsIgnoreCase("dj") || myCards.cardShoe.get(3).equalsIgnoreCase("d10") || myCards.cardShoe.get(3).equalsIgnoreCase("h10") || myCards.cardShoe.get(3).equalsIgnoreCase("c10") || myCards.cardShoe.get(3).equalsIgnoreCase("c10") ) {
                        if(theDealer.getHandValue()==21){
                            //dealer has BJ. Game over.
                            System.out.println("Dealer has Blackjack. You lost the hand, but kept your cash.");
                            gameplayDialog = false;
                            theDealer.setBlackjack(1);


                        }else{
                            System.out.println ("Dealer didn't have Blackjack. You lose $"+thePlayer.getCurrentWager()/2);
                            thePlayer.setBankRoll(thePlayer.getCurrentWager()/2, deductMoney);
                        }

                    }else{
                        //player can't do it.
                        System.out.println ("I'm sorry. You don't have the cash for insurance.");
                    }

                }else{
                    // player declined insurance
                    System.out.println ("WOW! You declined insurance. Sweet.");
                    // if(myCards.cardShoe.get(3).equalsIgnoreCase("sk") || myCards.cardShoe.get(3).equalsIgnoreCase("sq") || myCards.cardShoe.get(3).equalsIgnoreCase("sj") || myCards.cardShoe.get(3).equalsIgnoreCase("hk") || myCards.cardShoe.get(3).equalsIgnoreCase("hq") || myCards.cardShoe.get(3).equalsIgnoreCase("hj") || myCards.cardShoe.get(3).equalsIgnoreCase("ck") || myCards.cardShoe.get(3).equalsIgnoreCase("cq") || myCards.cardShoe.get(3).equalsIgnoreCase("cj") || myCards.cardShoe.get(3).equalsIgnoreCase("dk") || myCards.cardShoe.get(3).equalsIgnoreCase("dq") || myCards.cardShoe.get(3).equalsIgnoreCase("dj") || myCards.cardShoe.get(3).equalsIgnoreCase("d10") || myCards.cardShoe.get(3).equalsIgnoreCase("h10") || myCards.cardShoe.get(3).equalsIgnoreCase("c10") || myCards.cardShoe.get(3).equalsIgnoreCase("c10") ) {
                    if(theDealer.getHandValue()==21){

                        //dealer has BJ. Game over.
                        System.out.println("Dealer has Blackjack. Should of taken INSURANCE. LOL");
                        gameplayDialog = false;
                        theDealer.setBlackjack(0);
                    }


                }
            }

            //remove dealer's cards used
            deckOfCards.cardShoe.remove(1);
            deckOfCards.cardShoe.remove(4);

            //4.5 if dealer has a 10 showing, see if handvalue is 21. If so, hand over. money lost.


            if(deckOfCards.cardShoe.get(1).equalsIgnoreCase("sk") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("sq") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("sj") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("hk") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("hq") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("hj") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("ck") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("cq") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("cj") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("dk") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("dq") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("dj") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("d10") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("h10") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("c10") || deckOfCards.cardShoe.get(1).equalsIgnoreCase("c10") ) {
                //a 10 or face card is showing!
                if(theDealer.getHandValue()==21){
                    //dealer has 21 game over. Take cash!
                    System.out.println("Talk about SUCK. Dealer had sneaky Blackjack!");
                    gameplayDialog = false;
                    theDealer.setBlackjack(0);
                }
            }

            // 4.6 Check if player has a dealt Blackjack. If so, set gameplayDialog to false and pay up.
            // This section stops all action.
            // TODO: This needs to be refactored and moved as if the dealer has a blackjack too, the player is still paid 1.5x rather than normal.

            System.out.println (String.format("The player had %d", thePlayer.getHandValue()));
            if (thePlayer.getHandValue() == 21) {
                thePlayer.setGameStatus(GameStatus.BLACKJACK);
                System.out.println(String.format("BEFORE: %d", thePlayer.getBankRoll()));
                payout = (thePlayer.getCurrentWager() * GameOperations.Payouts.BLACKJACK.pay()) + thePlayer.getCurrentWager();
                thePlayer.setBankRoll(payout, addMoney);
                System.out.println(String.format("YOU GOT A BLACKJACK!!!!!!!\n\n\n\n\n\n\n\n\nThe wager: %d and the pay: %f and multiplier: %f\n\n\n\n\n\n", thePlayer.getCurrentWager(), payout, GameOperations.Payouts.BLACKJACK.pay()));
                System.out.println(String.format("AFTER: %d", thePlayer.getBankRoll()));
            }

            //5. ask player to hit or stand
            while (gameplayDialog && thePlayer.getGameStatus() != GameStatus.BLACKJACK){
                System.out.println ("Would you like to hit or stand? Type \"hit\" or \"stand\"");


                try {
                    myAction = in.next();
                    if (myAction.equalsIgnoreCase("hit")) {
                        //player wants a card

                        thePlayer.setHandValue(deckOfCards.cardShoe.get(0));
                        deckOfCards.cardShoe.remove(0);
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
                                gameplayDialog=false;
                                //thePlayer.setGameStatus(2);
                            }
                        }

                    }

                    if (myAction.equalsIgnoreCase("stand") || myAction.equalsIgnoreCase("s")) {
                        gameplayDialog=false;
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

            if(thePlayer.getGameStatus() == GameStatus.PLAYING){
                System.out.println ("Game on");
                System.out.println ("The dealer has "+ theDealer.getHandValue());

                //7. dealer action

                while (theDealer.checkAction(theDealer.getHandValue())!=2){
                    System.out.println ("The dealer's action is: " +theDealer.checkAction(theDealer.getHandValue()));
                    //get card
                    theDealer.setHandValue(deckOfCards.cardShoe.get(0));
                    deckOfCards.cardShoe.remove(0);


                }

                System.out.println("The dealer's FINAL total is: "+ theDealer.getHandValue());
            }

            if(thePlayer.getGameStatus() == GameStatus.QUIT){
                System.out.println ("game off");

            }


            //8. Compare hands to see who won and pay up or deduct

            //if game ended early due to dealer BJ
            if (theDealer.getBlackjack() == 1){
                //dealer has blackjack. Does player have insurance? If so, do nothing.

                if (theDealer.hasInsurance() == 1){
                    //player has insurance
                    System.out.println ("Lucky you took insurance. No harm.");

                }else{
                    //player didn't take insurance
                    System.out.println ("You should have taken insurance. Sorry.");
                    thePlayer.setBankRoll(thePlayer.getCurrentWager(),deductMoney);
                }


            }else{


                if(thePlayer.getHandValue()>21){
                    //player busted out
                    thePlayer.setBankRoll(thePlayer.getCurrentWager(), deductMoney);
                    System.out.println ("You have LOST $" + thePlayer.getCurrentWager());
                }else{
                    //check to see who won

                    if(theDealer.getHandValue()>21){
                        //dealer bust
                        thePlayer.setBankRoll(thePlayer.getCurrentWager(), addMoney);
                        System.out.println("WINNER! You've won $"+thePlayer.getCurrentWager());
                    }else{
                        //dealer still in

                        if (thePlayer.getHandValue()>theDealer.getHandValue()){
                            //player wins
                            thePlayer.setBankRoll(thePlayer.getCurrentWager(), addMoney);
                            System.out.println("WINNER! You've beaten the dealer and won $" + thePlayer.getCurrentWager());

                        }else if (thePlayer.getHandValue()<theDealer.getHandValue()){
                            //dealer wins
                            thePlayer.setBankRoll(thePlayer.getCurrentWager(),deductMoney);
                            System.out.println ("Dealer smoked you. You have LOST $" + thePlayer.getCurrentWager());
                        }else{
                            //push
                            System.out.println (String.format("It's a PUSH! No one wins. Dealer had: %d", theDealer.getHandValue()));

                        }


                    }

                }

            } // end deal blackjack if else


            // 9. if money is gone, setGameStatus = QUIT otherwise play again.

            if(thePlayer.getBankRoll()<1){
                //the player is out of cash. Wrap it up!
                thePlayer.setGameStatus(GameStatus.QUIT);
                System.out.println (String.format("Thanks for playing! You finished with $%d", thePlayer.getBankRoll()));
            } else {
                thePlayer.setGameStatus(GameStatus.PLAYING);
            }

            //end main loop

        }


    }//end main


}
