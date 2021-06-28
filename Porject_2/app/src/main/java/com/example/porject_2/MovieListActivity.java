package com.example.porject_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MovieListActivity extends AppCompatActivity implements MovieListFragment.OnItemSelectedListener {
    private boolean twoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        if(savedInstanceState == null){
            Log.d("OnCreate", "ListView started");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listview_list, new MovieListFragment()).commit();
        }
        twoPane = false;
        if(findViewById(R.id.listview_info) != null){
            Log.d("Found Two Pane", "Two pane is true");
            twoPane = true;
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onListItemSelected(Movie movie) {
        Bundle args = new Bundle();
        args.putString(MovieDetailFragment.ARG_TITLE, movie.getTitle());
        args.putString(MovieDetailFragment.ARG_YEAR, movie.getYear());
        args.putInt(MovieDetailFragment.ARG_ID, movie.getImage_id());
        args.putString(MovieDetailFragment.ARG_STARS, movie.getStars());
        args.putFloat(MovieDetailFragment.ARG_RATING, movie.getRating());
        args.putString(MovieDetailFragment.ARG_DESC, movie.getDescription());
        args.putBoolean("TwoPane", twoPane);
        Fragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        if(twoPane){
            getSupportFragmentManager().beginTransaction().replace(R.id.listview_info, fragment)
                    .addToBackStack(null).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.listview_list, fragment)
                    .addToBackStack(null).commit();
        }
    }
}