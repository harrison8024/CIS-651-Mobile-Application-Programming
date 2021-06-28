package com.example.project1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CardData {
    public ArrayList<Card> cardArrayList;
    public ArrayList<Integer> randomCards;
    private Integer[] imgId = {R.drawable.abra, R.drawable.bellsprout, R.drawable.bullbasaur,
        R.drawable.caterpie, R.drawable.charmander, R.drawable.dratini, R.drawable.eevee,
        R.drawable.jigglypuff, R.drawable.mankey, R.drawable.meowth, R.drawable.mew,
        R.drawable.pidgey, R.drawable.pikachu_2, R.drawable.psyduck, R.drawable.rattata,
        R.drawable.snorlax, R.drawable.squirtle, R.drawable.venonat, R.drawable.weedle,
        R.drawable.zubat};
    public int size;
    public CardData(Integer size){
        this.size = size;
        Random random = new Random(System.currentTimeMillis());
        randomCards = new ArrayList<Integer>();
        cardArrayList = new ArrayList<Card>();

        // Get random Cards size/2
        int i = 0;
        while (i < size/2) {
            Integer randNum = random.nextInt(20);
            if (!randomCards.contains(imgId[randNum])) {
                randomCards.add(imgId[randNum]);
                i++;
            }
        }


        // Fill the Card array
        for (i = 0; i < size; i++) {
            Card card = new Card(i, randomCards.get(i/2));
            cardArrayList.add(card);
        }

        Collections.shuffle(cardArrayList);

    }

    public synchronized void randomizeArrayList() {
        // randomize cardArrayList
        Collections.shuffle(cardArrayList);
    }

    public ArrayList<Card> getRandomCardsArray(){
        return cardArrayList;
    }
}
