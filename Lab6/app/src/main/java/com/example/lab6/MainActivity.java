package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManger;
    private MyDBHelper dbHelper;
    private MyRecyclerAdapter adapter;
    private String filter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.mainRecyclerView);
        mLayoutManger = new LinearLayoutManager(this);
        mLayoutManger.scrollToPosition(0);
        mRecyclerView.setLayoutManager(mLayoutManger);
        populaterecyclerView(filter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener((view) -> {goToAddUserActivity();});
    }
    private void populaterecyclerView(String filter){
        dbHelper = new MyDBHelper(this);
        adapter = new MyRecyclerAdapter(dbHelper.contactList(), this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        adapter.updateList(dbHelper.contactList());
        Log.d("TAG", "Resume");
    }

    private void goToAddUserActivity(){
        Intent intent = new Intent(MainActivity.this, AddNewContact.class);
        startActivity(intent);
    }
}