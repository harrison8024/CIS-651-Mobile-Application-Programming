package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import android.os.Handler;
import android.widget.TextView;

import java.util.logging.LogRecord;

public class CardGVAdapter extends ArrayAdapter<Card> {

    public CardGVAdapter(@NonNull Context context, ArrayList<Card> cardArrayList) {
        super(context, 0, cardArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if(listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }
        Card card = getItem(position);
        card.setArrayId(position);
        ImageButton cardIB = listitemView.findViewById(R.id.imgBtn);
        ViewGroup vg = parent;

        //Set onClick
        cardIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Flip over
                ((ImageButton) v).setImageResource(card.getImgId());

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // If second img is clicked
                        if(GameControl.setImgClicked(card.getImgId(), card.getArrayId())) {
                            // Check if both Cards are not the same
                            if(!GameControl.checkCard()){
                                // Turn second card back to pokeball
                                ((ImageButton) v).setImageResource(R.drawable.pokeball);
                                //Get imageButton and setImage back to pokeball
                                ViewGroup pregrid = (ViewGroup)vg.getParent();
                                ViewGroup grid = (ViewGroup) pregrid.getChildAt(3);
                                ViewGroup cardView = (ViewGroup) grid.getChildAt(GameControl.firstArrayId);
                                ViewGroup layout = (ViewGroup) cardView.getChildAt(0);
                                ImageButton imageButton = (ImageButton) layout.getChildAt(0);
                                imageButton.setImageResource(R.drawable.pokeball);
                            } else {
                                // Cards are not the same, Check if Game ends
                                if(!GameControl.checkGame()){
                                    // If game ends
                                    // Set visibility for End Game
                                    ViewGroup pregrid = (ViewGroup)vg.getParent();
                                    // Hide Grid
                                    GridView grid = (GridView) pregrid.getChildAt(3);
                                    grid.setVisibility(View.INVISIBLE);
                                    // Show Lvl text
                                    TextView tv = (TextView) pregrid.getChildAt(0);
                                    tv.setVisibility(View.VISIBLE);
                                    // Show Complete text
                                    TextView tv1 = (TextView) pregrid.getChildAt(1);
                                    tv1.setVisibility(View.VISIBLE);
                                    // Show Click
                                    TextView tv2 = (TextView) pregrid.getChildAt(2);
                                    tv2.setText("Total Click: " + GameControl.clickCount);
                                    tv2.setVisibility(View.VISIBLE);

                                }
                            }
                        }
                    }
                }, 500);
            }
        });
        return listitemView;
    }
}
