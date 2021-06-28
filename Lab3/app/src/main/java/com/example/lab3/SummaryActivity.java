package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        PersonInfo pi = (PersonInfo) getIntent().getParcelableExtra("pi");
        TextView nl=findViewById(R.id.name_lastname);
        TextView cz=findViewById(R.id.city_zip);
        TextView dtl=findViewById(R.id.detailTextview);
        nl.setText(String.format("Name : %s LastName : %s", pi.getName(), pi.getLastname()));
        cz.setText(String.format("City : %s Zip : %s", pi.getCity(), pi.getZip()));
        dtl.setText(String.format("Detail : %s", pi.getDetail()));
    }
}