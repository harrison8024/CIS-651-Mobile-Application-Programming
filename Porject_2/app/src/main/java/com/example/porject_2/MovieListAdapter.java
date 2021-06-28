package com.example.porject_2;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MovieListAdapter extends ArrayAdapter<Movie> {

    private final Activity context;
    private List<Movie> movieList;
    public MovieListAdapter(Activity context, List<Movie> movieList) {
        super(context, R.layout.listview_item, movieList);
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View linear_layout = inflater.inflate(R.layout.listview_item, null, true);
        ImageView imageView = (ImageView) linear_layout.findViewById(R.id.list_img);
        TextView textView = (TextView) linear_layout.findViewById(R.id.list_title);
        textView.setText(movieList.get(position).getTitle());
        imageView.setImageResource(movieList.get(position).getImage_id());
        return  linear_layout;
    }

}