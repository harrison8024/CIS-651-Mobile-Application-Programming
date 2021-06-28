package com.example.project1;

import java.util.ArrayList;

public class GameControl {
    private static boolean gameEnd;
    public static int clickCount;
    public static int cardCount;
    private static boolean firstImgClicked, secondImgClicked;
    private static int firstImgId, secondImgId;
    public static int firstArrayId, secondArrayId;
    private static int maxCard;
    private static ArrayList<Card> cardArrayList;
    public GameControl(int gameMode){
        // Initializing all variables
        maxCard = gameMode;
        gameEnd = false;
        clickCount = 0;
        cardCount = 0;
        firstImgClicked = false;
        secondImgClicked = false;
        firstImgId = -1;
        secondImgId = -1;
        firstArrayId = -1;
        secondArrayId = -1;
        switch(gameMode) {
            case 0:
                // Easy Mode - 12
                maxCard = 12;
                break;
            case 1:
                // Medium Mode - 20
                maxCard = 20;
                break;
            case 2:
                //Hard Mode - 30
                maxCard = 30;
                break;
        }
    }

    public static void addCardsToGame(ArrayList<Card> arrayList) {
        cardArrayList = arrayList;
    }

    public static boolean setImgClicked(int imgid, int arrayid) {
        boolean checkNeeded = false;
        if(firstImgClicked) {
            secondImgId = imgid;
            secondArrayId = arrayid;
            secondImgClicked = true;
            checkNeeded = true;
        } else {
            firstImgId = imgid;
            firstArrayId = arrayid;
            firstImgClicked = true;
        }
        addClick();
        return checkNeeded;
    }

    public static boolean checkCard() {
        boolean isSame;
        if(firstImgId == secondImgId){
            cardCount +=2;
            isSame = true;
        } else {
            isSame = false;
        }
        //Reset variables
        firstImgClicked = false;
        secondImgClicked = false;
        firstImgId = -1;
        secondImgId = -1;
        return isSame;
    }

    public static boolean checkGame() {
        if(cardCount == maxCard) {
            return false;
        }
        return true;
    }

    public static void addClick() {
        clickCount++;
    }
}
