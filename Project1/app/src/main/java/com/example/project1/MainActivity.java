package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goEasy(View view) {
        Intent intent = new Intent(this, Activity_Easy.class);
        startActivity(intent);
    }

    public void goMedium(View view) {
        Intent intent = new Intent(this, Activity_Medium.class);
        startActivity(intent);
    }

    public void goHard(View view) {
        Intent intent = new Intent(this, Activity_Hard.class);
        startActivity(intent);
    }
}