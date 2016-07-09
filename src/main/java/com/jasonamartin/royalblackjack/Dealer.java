package com.jasonamartin.royalblackjack;

public class Dealer extends Players {


    public int checkAction (int currentHandValue){
        int actionCode=0;

        //action based on hand value. 1 is hit and 2 is stand


        switch (currentHandValue){

            case 2:
                actionCode=1;
                break;
            case 3:
                actionCode=1;
                break;
            case 4:
                actionCode=1;
                break;
            case 5:
                actionCode=1;
                break;
            case 6:
                actionCode=1;
                break;
            case 7:
                actionCode=1;
                break;
            case 8:
                actionCode=1;
                break;
            case 9:
                actionCode=1;
                break;
            case 10:
                actionCode=1;
                break;
            case 11:
                actionCode=1;
                break;
            case 12:
                actionCode=1;
                break;
            case 13:
                actionCode=1;
                break;
            case 14:
                actionCode=1;
                break;
            case 15:
                actionCode=1;
                break;
            case 16:
                actionCode=1;
                break;
            case 17:
                actionCode=2;
                break;
            case 18:
                actionCode=2;
                break;
            case 19:
                actionCode=2;
                break;
            case 20:
                actionCode=2;
                break;
            case 21:
                actionCode=2;
                break;



            default:
                actionCode=2;
                break;

        }





        return actionCode;
    }



}
