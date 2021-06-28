package com.example.lab7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EditPost extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private EditText description;
    private ImageView postImage;
    private DatabaseReference postRef;
    private FirebaseDatabase database;
    String postKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        postKey = getIntent().getStringExtra("key");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        postRef = database.getReference("Posts/" + postKey);
        postImage = findViewById(R.id.edit_image);
        description = findViewById(R.id.edit_description);
        postRef.child("description").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    description.setText(task.getResult().getValue().toString());
                } else {
                    Log.d("POST EDIT", "Data fetch unsuccessful.");
                }
            }
        });
        postRef.child("url").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    Picasso.get().load(task.getResult().getValue().toString()).into(postImage);
                } else {
                    Log.d("POST EDIT", "Image fetch unsuccessful.");
                }
            }
        });

    }
    public void updatePost(View view){
        postRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                PhotoPreview.Post p = currentData.getValue(PhotoPreview.Post.class);
                p.description = description.getText().toString();
                currentData.setValue(p);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                if(error == null){
                    Toast.makeText(EditPost.this, "Update successful.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditPost.this, "Update Failed: " + error.getDetails(), Toast.LENGTH_SHORT).show();
                }
            }
        });
//        postRef.child("description").setValue(description.getText()).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(EditPost.this, "Update Successful.", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(EditPost.this, "Update Failed.", Toast.LENGTH_SHORT).show();
//            }
//        });
        finish();
    }
}