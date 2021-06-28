package com.example.porject_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ViewPagerActivity extends FragmentActivity {
    public class DepthTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;
        public void transformPage(View view, float position){
            int pageWidth = view.getWidth();
            if(position < -1){
                view.setAlpha(0);
            } else if(position <= 0){
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);
            } else if(position <= 1){
                view.setAlpha(1 - position);
                view.setTranslationX(pageWidth * -position);
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else {
                view.setAlpha(0);
            }
        }
    }
    public class ZoomOutPageTransformer implements  ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;
        public void transformPage(View view, float position){
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();
            if(position < -1){
                view.setAlpha(0);
            } else if(position <= 1){
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) /2;
                float horzMargin = pageWidth * (1 - scaleFactor) /2;
                if(position < 0){
                    view.setTranslationX(horzMargin - vertMargin/2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin/2);
                }
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)/(1-MIN_SCALE) * (1-MIN_ALPHA));
            } else {
                view.setAlpha(0);
            }
        }
    }
    MoviePagerAdapter moviePagerAdapter;
    ViewPager viewPager;
    private List<Movie> movieList;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpage);
        movieList = new MovieData().getMoviesList();
        moviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager(), movieList);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(moviePagerAdapter);
        viewPager.setPageTransformer(true, new DepthTransformer());
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem() == 0){
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
        }
    }

}
