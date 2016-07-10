package com.jasonamartin.royalblackjack;// import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CardDeck {

    Random generator = new Random();
    ArrayList<String> cardShoe = new ArrayList<String>();



    public void ShuffleCards (int decks) {

        //setup card deck first

        String suitArray[] = { "c","h","s","d"};

        // iterate for decks
        for (int deckCount = 1; deckCount <= decks; deckCount++) {
            for (int s = 0; s < 4; s++) {
                //begin card setup


                for (int i = 1; i < 14; i++) {


                    switch (i) {

                        case 11:
                            cardShoe.add(suitArray[s] + "j");
                            break;
                        case 12:
                            cardShoe.add(suitArray[s] + "q");
                            break;
                        case 13:
                            cardShoe.add(suitArray[s] + "k");
                            break;
                        case 1:
                            cardShoe.add(suitArray[s] + "a");
                            break;
                        default:
                            cardShoe.add(suitArray[s] + i);
                            break;

                    }

                    //end  loop
                }

                //end card setup
            }
        }
        // System.out.println("CARDS::: " + cardShoe.size() + "\n\n" + cardShoe);

        Collections.shuffle(cardShoe);
    }




    public void GetCardSuit (char a){

        switch (a){

            case 'c':
                System.out.println ("Clubs!");
                break;
            case 'd':
                System.out.println ("Diamonds!");
                break;
            case 'h':
                System.out.println ("Hearts!");
                break;
            case 's':
                System.out.println ("Spades!");
                break;

            //end case
        }

        //end GetCardSuit
    }


    public static int GetCardValue (int a){
        int b=0;


        switch (a){
            case 49:
                b=10;
                break;
            case 50:
                b=2;
                break;
            case 51:
                b=3;
                break;
            case 52:
                b=4;
                break;
            case 53:
                b=5;
                break;
            case 54:
                b=6;
                break;
            case 55:
                b=7;
                break;
            case 56:
                b=8;
                break;
            case 57:
                b=9;
                break;
            case 106:
                b=10;
                break;
            case 113:
                b=10;
                break;
            case 107:
                b=10;
                break;
            case 97:
                b=11;
                break;

            //end case
        }




        return b;

        //end GetCard Value
    }

    //==public int getPlayerValue () {
    //---	return playerValue;
    //===}

    public void setPlayerValue () {
        //---playerValue=playerValue+10;
    }

    //end com.jasonamartin.royalblackjack.CardDeck class
}
