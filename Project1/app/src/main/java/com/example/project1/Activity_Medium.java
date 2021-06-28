package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class Activity_Medium extends AppCompatActivity {
    GridView cardsGV;
    boolean firstImgClicked, secondImgClicked;
    int firstImgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium);

        //Create Image button
        cardsGV = findViewById(R.id.grid_view_medium);
        CardData cardData = new CardData(20);
        // Get cardArrayList from cardData
        List<Card> cardArrayList = cardData.getRandomCardsArray();
        CardGVAdapter adapter = new CardGVAdapter(this, (ArrayList<Card>) cardArrayList);
        cardsGV.setAdapter(adapter);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameControl game = new GameControl(1);
            }
        }, 250);
    }
}
