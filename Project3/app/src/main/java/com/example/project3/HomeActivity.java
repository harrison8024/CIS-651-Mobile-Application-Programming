package com.example.project3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recycler_view;
    private MyRecyclerAdapter myRecyclerAdapter;
    private List<String> keyList = null;
    private HashMap<String, Movie> key_to_Movie = null;
    private static final int REQUEST_FOR_CAMERA = 0011;
    private static final int OPEN_FILE = 0012;
    private static final String TEMP_IMAGE_NAME = "tempImage";
    private Uri imageUri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);
        //Upload data into firebase realtime database
//        UploadData ud = new UploadData();
        recycler_view = (RecyclerView) findViewById(R.id.mainRecyclerView);
        myRecyclerAdapter = new MyRecyclerAdapter(recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recycler_view.setLayoutManager(layoutManager);
        myRecyclerAdapter.setOnListItemClickListener(new OnListItemClickListener(){
            @Override
            public void onItemClick(View v, int position) {
                Log.d("ON ITEM CLICK", "movie: " + myRecyclerAdapter.getItem(position) + "Name: " + myRecyclerAdapter.getItem(position).getName());
                Movie movieMap = myRecyclerAdapter.getItem(position);
                Intent intent = new Intent(getBaseContext(), MovieActivity.class);
                intent.putExtra("Name", movieMap.name);
                intent.putExtra("Image", movieMap.image);
                intent.putExtra("Year", movieMap.year);
                intent.putExtra("Length", movieMap.length);
                intent.putExtra("Director", movieMap.director);
                intent.putExtra("Stars", movieMap.stars);
                intent.putExtra("Rating", (float) movieMap.rating);
                intent.putExtra("Description", movieMap.description);
                startActivity(intent);
            }
        });
        recycler_view.setAdapter(myRecyclerAdapter);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast toast = Toast.makeText(getApplication(), "Query Text=" + query, Toast.LENGTH_SHORT);
                toast.show();
                myRecyclerAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myRecyclerAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void addNewMovie(View view) {
        Toast.makeText(this, "Add New Movie", Toast.LENGTH_SHORT).show();
        checkPermissions();
    }
    private void takePhoto(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        Intent chooser = Intent.createChooser(intent, "Select a Camera App.");
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(chooser, REQUEST_FOR_CAMERA);
        }
    }

    private void selectPhoto(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera Roll");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        Intent chooser = Intent.createChooser(intent, "Select Photo");
        startActivityForResult(chooser, OPEN_FILE);

    }
    private static File getTempFile(Context context) {
        File imageFile = new File(context.getExternalCacheDir(), TEMP_IMAGE_NAME);
        imageFile.getParentFile().mkdirs();
        return imageFile;
    }
    private void checkPermissions(){
        if(ContextCompat.checkSelfPermission(getBaseContext(),
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this, "We need permission to access your camera and photo.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_FOR_CAMERA);
        }
        else{
            AlertDialog.Builder alertbox;
            alertbox = new AlertDialog.Builder(this);
            alertbox.setMessage("Take a photo or select from camera roll?");
            alertbox.setTitle("Photo Selection");
            alertbox.setCancelable(true);
            alertbox.setPositiveButton("Take Photo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    takePhoto();
                }
            });
            alertbox.setNegativeButton("Select Photo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectPhoto();
                }
            });
            alertbox.show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FOR_CAMERA && resultCode == RESULT_OK) {
            if(imageUri == null){
                Toast.makeText(this, "Error taking photo", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, AddMovieActivity.class);
            Log.d("FROM CAMERA", "uri"+ imageUri.toString());
            intent.putExtra("uri", imageUri.toString());
            startActivity(intent);
            return;
        } else if(requestCode == OPEN_FILE && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, AddMovieActivity.class);
            intent.putExtra("uri", data.getData().toString());
            startActivity(intent);
            return;
        }
    }

//    @Override
//    protected  void onStart() {
//        super.onStart();
//        recycler_view = (RecyclerView) findViewById(R.id.mainRecyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        layoutManager.scrollToPosition(0);
//        recycler_view.setLayoutManager(layoutManager);
//        myRecyclerAdapter.setOnListItemClickListener(new OnListItemClickListener(){
//            @Override
//            public void onItemClick(View v, int position) {
//                Movie movieMap = myRecyclerAdapter.getItem(position);
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(movieMap.getImage(),
//                        movieMap.getName(), movieMap.getYear(),
//                        (float)movieMap.getRating(), movieMap.getDescription());
//
//                fragmentTransaction.replace(R.id.detailFragment, movieDetailFragment);
//                fragmentTransaction.commit();
//            }
//        });
//        recycler_view.setAdapter(myRecyclerAdapter);
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
//    }
}