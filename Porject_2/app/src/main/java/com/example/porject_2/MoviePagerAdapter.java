package com.example.porject_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;
import java.util.Map;

public class MoviePagerAdapter extends FragmentStatePagerAdapter {
    List<Movie> list_movie;
    public MoviePagerAdapter(FragmentManager fragmentManager, List<Movie> list_m){
        super(fragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.list_movie = list_m;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putString(MovieDetailFragment.ARG_TITLE, list_movie.get(position).getTitle());
        args.putString(MovieDetailFragment.ARG_YEAR, list_movie.get(position).getYear());
        args.putInt(MovieDetailFragment.ARG_ID, list_movie.get(position).getImage_id());
        args.putString(MovieDetailFragment.ARG_STARS, list_movie.get(position).getStars());
        args.putFloat(MovieDetailFragment.ARG_RATING, list_movie.get(position).getRating());
        args.putString(MovieDetailFragment.ARG_DESC, list_movie.get(position).getDescription());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return list_movie.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_movie.get(position).getTitle();
    }
}
