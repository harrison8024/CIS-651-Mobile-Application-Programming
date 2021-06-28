package com.example.porject_2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MovieListFragment extends Fragment{
    public interface OnItemSelectedListener {
        public void onListItemSelected(Movie movie);
    }

    List<Movie> movieList;

    OnItemSelectedListener onItemSelectedListener = dummycallbacks;

    public MovieListFragment() {
        // Required empty public constructor
    }


    public static MovieListFragment newInstance(String param1, String param2) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieList = new MovieData().getMoviesList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        MovieListAdapter movieListAdapter = new MovieListAdapter(getActivity(), movieList);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(movieListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedListener.onListItemSelected((Movie) parent.getItemAtPosition(position));
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onItemSelectedListener = (OnItemSelectedListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement OnItemClickedListener");
        }
    }

    private static OnItemSelectedListener dummycallbacks = new OnItemSelectedListener() {
        @Override
        public void onListItemSelected(Movie movie){

        }
    };
}