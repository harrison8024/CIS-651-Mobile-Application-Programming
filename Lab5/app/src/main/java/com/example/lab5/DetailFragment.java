package com.example.lab5;

import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.img_poster);
        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView year = (TextView) rootView.findViewById(R.id.year);
        Bundle args = getArguments();
        imageView.setImageResource(args.getInt("img_id"));
        title.setText(args.getString("mtitle"));
        ViewCompat.setTransitionName(imageView, args.getString("mtitle"));
        year.setText(args.getString("myear"));
        return rootView;
    }
}