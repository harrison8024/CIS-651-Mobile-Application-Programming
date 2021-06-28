package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddMovieActivity extends AppCompatActivity {
    private static final int REQUEST_FOR_LOCATION = 123;
    public class NewMovie {
        public String name;
        public String image;
        public String description;
        public String length;
        public String year;
        public String rating;
        public String director;
        public String stars;
        public String url;

        public NewMovie(String name, String image, String description, String length, String year,
                        String rating, String director, String stars, String url) {
            this.name = name;
            this.image = image;
            this.description = description;
            this.length = length;
            this.year = year;
            this.rating = rating;
            this.director = director;
            this.stars = stars;
            this.url = url;
        }

        public NewMovie() {

        }
    }
    public ImageView imageView;
    public EditText nameText;
    public EditText yearText;
    public EditText lengthText;
    public EditText directorText;
    public EditText castText;
    public EditText ratingText;
    public EditText descriptionText;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        Log.d("IMAGE", "Image uri" + getIntent().getStringExtra("uri"));
        uri = Uri.parse(getIntent().getStringExtra("uri"));
        imageView = findViewById(R.id.previewImage);
        imageView.setImageURI(uri);
        nameText = findViewById(R.id.name_add);
        yearText = findViewById(R.id.year_add);
        lengthText = findViewById(R.id.length_add);
        directorText = findViewById(R.id.director_add);
        castText = findViewById(R.id.cast_add);
        ratingText = findViewById(R.id.rating_add);
        descriptionText = findViewById(R.id.description_add);
        Toolbar toolbar = (Toolbar)findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.appbar_fake, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_FOR_LOCATION && ((grantResults.length>0 && grantResults[0] != PackageManager.PERMISSION_GRANTED)
                || (grantResults.length>1 && grantResults[1] != PackageManager.PERMISSION_GRANTED))){
            Toast.makeText(this, "We need to access you location", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final String fileNameInStorage = UUID.randomUUID().toString();
        String path = "/" + fileNameInStorage + ".jpg";
        final StorageReference imageRef = storage.getReference(path);
        imageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference movieRef = database.getReference("movies/");
                        DatabaseReference newMovieRef = movieRef.push();
                        newMovieRef.setValue(new NewMovie(nameText.getText().toString(), uri.toString(),
                                descriptionText.getText().toString(), lengthText.getText().toString(),
                                yearText.getText().toString(), ratingText.getText().toString(),
                                directorText.getText().toString(), castText.getText().toString(), "NO URL"))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddMovieActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddMovieActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddMovieActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AddMovie(View view) {
        uploadImage();
        finish();
    }
}