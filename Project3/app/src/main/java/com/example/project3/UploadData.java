package com.example.project3;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadData {
    private DatabaseReference mDatabase;
    private StorageReference storageRef;

    public UploadData(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        MovieData movieData = new MovieData();
//        List<Map<String, ?>> movieList = movieData.getMoviesList();
//        for(int i = 0; i < movieList.size(); i++){
//            Map<String, ?> movieItem = movieList.get(i);
//            writeNewMovie(movieItem.get("name").toString(), movieItem.get("description").toString(),
//                    movieItem.get("length").toString(), movieItem.get("year").toString(),
//                    (double)movieItem.get("rating"), movieItem.get("director").toString(),
//                    movieItem.get("stars").toString(), movieItem.get("url").toString());
//        }
        storageRef = FirebaseStorage.getInstance().getReference();
        getImageUri("alice.jpg");
        getImageUri("avatar.jpg");
        getImageUri("avengers.jpg");
        getImageUri("dark_knight.jpg");
        getImageUri("dark_knight_rises.jpg");
        getImageUri("despicable2.jpg");
        getImageUri("et.jpg");
        getImageUri("forrest_gump.jpg");
        getImageUri("frozen.jpg");
        getImageUri("harry2.jpg");
        getImageUri("hunger_games.jpg");
        getImageUri("hunger_games1.jpg");
        getImageUri("ironman3.jpg");
        getImageUri("jurassicpark.jpg");
        getImageUri("lion.jpg");
        getImageUri("nemo.jpg");
        getImageUri("pirates.jpg");
        getImageUri("rings.jpg");
        getImageUri("rings2.jpg");
        getImageUri("shrek2.jpg");
        getImageUri("spiderman.jpg");
        getImageUri("spiderman2.jpg");
        getImageUri("spiderman3.jpg");
        getImageUri("star_wars1.jpg");
        getImageUri("star_wars3.jpg");
        getImageUri("star_wars4.jpg");
        getImageUri("titanic.jpg");
        getImageUri("toy3.jpg");
        getImageUri("transformers.jpg");
        getImageUri("transformers2.jpg");

    }

//    private void writeNewMovie(String name, String description, String length, String year,
//                               double rating, String director, String stars, String url){
//        String key = mDatabase.child("movie").push().getKey();
//        Movie movie = new Movie(name, description, length, year, rating, director, stars, url);
//        Map<String, Object> movieValues = movie.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/movies/" + key, movieValues);
//        mDatabase.updateChildren(childUpdates);
//    }

    private void getImageUri(String imageName){
        storageRef.child(imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("URL", "name: "+ imageName + "uri: " + uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("URL", "Failed to get URI: " + imageName );
            }
        });
    }
}
