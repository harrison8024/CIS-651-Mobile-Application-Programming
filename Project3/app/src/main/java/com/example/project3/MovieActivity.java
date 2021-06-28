package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar movieToolBar = (Toolbar) findViewById(R.id.movie_toolbar);
        setSupportActionBar(movieToolBar);
        ImageView imageView = findViewById(R.id.large_image);
        Intent intent = getIntent();
        Picasso.get().load(intent.getStringExtra("Image")).into(imageView);
        TextView titleText = findViewById(R.id.title_text);
        titleText.setText(intent.getStringExtra("Name"));
        TextView yearText = findViewById(R.id.year_text);
        yearText.setText("("+intent.getStringExtra("Year")+")");
        TextView lengthText = findViewById(R.id.length_text);
        lengthText.setText(intent.getStringExtra("Length"));
        TextView directorText = findViewById(R.id.director_text);
        String directorString = "<b>Director: </b>" +intent.getStringExtra("Director");
        directorText.setText(Html.fromHtml(directorString));
        TextView castText = findViewById(R.id.cast_text);
        String castString = "<b>Cast: </b>" +intent.getStringExtra("Stars");
        castText.setText(Html.fromHtml(castString));
        RatingBar ratingBar = findViewById(R.id.movie_rating);
        Log.d("Moive Activity", "rating: "+intent.getFloatExtra("Rating", 6));
        ratingBar.setRating(intent.getFloatExtra("Rating", 0));
        TextView descriptionText = findViewById(R.id.description);
        descriptionText.setText(intent.getStringExtra("Description"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_no_search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}