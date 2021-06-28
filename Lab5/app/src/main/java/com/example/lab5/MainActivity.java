package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.next:
                    nextActivity(v);
                    break;
                case R.id.move_rotate:
                    imageView.animate().setDuration(1000).x(500).y(800).rotationYBy(720).scaleX(.4F).scaleY(.4F);
                    break;
                case R.id.move_back:
                    imageView.animate().setDuration(1000).x(imageView.getLeft()).y(imageView.getTop()).rotationYBy(720).scaleX(1F).scaleY(1F);
                    break;
                case R.id.fade_out:
                    imageView.animate().setDuration(1000).alpha(0F);
                    break;
                case R.id.fade_in:
                    imageView.animate().setDuration(1000).alpha(1F);
                    break;
                case R.id.animator:
                    AnimatorSet spinSet = (AnimatorSet) AnimatorInflater.loadAnimator( getApplicationContext(), R.animator.custom_animator);
                    spinSet.setTarget(imageView);
                    spinSet.start();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.img);
        Button nextButton = findViewById(R.id.next);
        nextButton.setOnClickListener(onClickListener);
        Button rotateButton = findViewById(R.id.move_rotate);
        rotateButton.setOnClickListener(onClickListener);
        Button backButton = findViewById(R.id.move_back);
        backButton.setOnClickListener(onClickListener);
        Button outButton = findViewById(R.id.fade_out);
        outButton.setOnClickListener(onClickListener);
        Button inButton = findViewById(R.id.fade_in);
        inButton.setOnClickListener(onClickListener);
        Button animatorButton = findViewById(R.id.animator);
        animatorButton.setOnClickListener(onClickListener);
    }

    public void nextActivity(View view){
        Intent intent = new Intent(this, NextActivity.class);
        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "newActivity");
        startActivity(intent, optionsCompat.toBundle());
    }
}