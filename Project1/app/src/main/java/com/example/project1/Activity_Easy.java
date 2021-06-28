package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class Activity_Easy extends AppCompatActivity {
    GridView cardsGV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        //Create Image button
        cardsGV = findViewById(R.id.grid_view_easy);
        CardData cardData = new CardData(12);
        // Get cardArrayList from cardData
        ArrayList<Card> cardArrayList = cardData.getRandomCardsArray();
        CardGVAdapter adapter = new CardGVAdapter(Activity_Easy.this, cardArrayList);
        cardsGV.setAdapter(adapter);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameControl game = new GameControl(0);
            }
        }, 250);
    }
}