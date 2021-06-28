package com.example.porject_2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class MovieDetailFragment extends Fragment {
    public static final String ARG_ID = "id";
    public static final String ARG_TITLE = "title";
    public static final String ARG_YEAR = "year";
    public static final String ARG_STARS = "stars";
    public static final String ARG_RATING = "rating";
    public static final String ARG_DESC = "description";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        Bundle args = getArguments();
        // Inflate the layout for the fragment
        final View v = inflater.inflate(R.layout.fragment_layout, container, false);
        ImageView imageView = v.findViewById(R.id.large_image);
        imageView.setImageResource(args.getInt("id"));
        TextView titleText = v.findViewById(R.id.title_text);
        titleText.setText(args.getString("title"));
        TextView yearText = v.findViewById(R.id.year_text);
        yearText.setText(args.getString("year"));
        TextView starsText = v.findViewById(R.id.stars);
        starsText.setText(args.getString("stars"));
        RatingBar ratingBar = v.findViewById(R.id.movie_rating);
        ratingBar.setRating(args.getFloat("rating"));
        TextView descriptionText = v.findViewById(R.id.description);
        descriptionText.setText(args.getString("description"));
        return v;
    }

}
