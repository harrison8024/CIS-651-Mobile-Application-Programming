package com.example.lab7;

import android.content.Intent;
import android.system.StructPollfd;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Struct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    SimpleDateFormat localDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private class PostModel{
        public String postKey;
        public String uid;
        public String description;
        public String url;
        public String date;
        public PostModel(String uid, String description, String url, String date, String key){
            this.uid = uid;
            this.description = description;
            this.url = url;
            this.date = date;
            this.postKey = key;
        }
    }
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allPostsRef = database.getReference("Posts");
    ChildEventListener usersRefListener;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private List<PostModel> postsList;

    private RecyclerView r;
    public MyRecyclerAdapter(RecyclerView recyclerView){
        postsList = new ArrayList<>();
        r = recyclerView;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        usersRefListener = allPostsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                PostModel userModel = new PostModel(snapshot.child("uid").getValue().toString(),
                        snapshot.child("description").getValue().toString(),
                        snapshot.child("url").getValue().toString(),
                        localDateFormat.format(new Date(Long.parseLong(snapshot.child("timestamp").getValue().toString()))),
                        snapshot.getKey());
                postsList.add(userModel);
                MyRecyclerAdapter.this.notifyItemInserted(postsList.size()-1);
                r.scrollToPosition(postsList.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
                for(PostModel post:postsList){
                    if(post.postKey.equals(key)){
                        post.description = snapshot.child("description").getValue().toString();
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String key = snapshot.getKey();
                for(PostModel post:postsList){
                    if(post.postKey.equals(key)){
                        postsList.remove(post);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@Nullable ViewHolder holder, int position){
        final PostModel u = postsList.get(position);
        String uid = u.uid;
        if(holder.uref != null && holder.urefListener != null){
            holder.uref.removeEventListener(holder.urefListener);
        }
        if(holder.likesRef != null && holder.likesRefListener != null){
            holder.likesRef.removeEventListener(holder.likesRefListener);
        }
        if(holder.likeCountRef != null && holder.likeCountRefListener != null){
            holder.likeCountRef.removeEventListener(holder.likeCountRefListener);
        }
        Picasso.get().load(u.url).into(holder.imageView);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        // add options menu
        if(currentUser.getUid().equals(uid)){
            holder.optionBtn.setVisibility(View.VISIBLE);
            holder.optionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.post_options, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                         @Override
                         public boolean onMenuItemClick(MenuItem item) {
                             switch (item.getItemId()){
                                 case R.id.edit_post:
                                     Log.d("EDIT_POST", "edit post clicked.");
                                     Intent intent = new Intent(v.getContext(), EditPost.class);
                                     intent.putExtra("key", u.postKey.toString());
                                     r.getContext().startActivity(intent);
                                     return true;
                                 case R.id.delete_post:
                                     Log.d("DELETE POST", "delete post clicked");
                                     DatabaseReference postRef = database.getReference("Posts/"+ u.postKey.toString());
                                     postRef.removeValue();
                                     return true;
                                 default:
                                     return false;
                             }
                         }
                    });
                    popup.show();
                }
            });
        } else {
            holder.optionBtn.setVisibility(View.GONE);
        }


        holder.uref = database.getReference("Users").child(uid);
        holder.uref.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@Nullable DataSnapshot snapshot){
                holder.fname_v.setText("First Name: " + snapshot.child("displayname").getValue().toString());
                holder.email_v.setText("Email:  " + snapshot.child("email").getValue().toString());
                holder.phone_v.setText("Phone Num:  " + snapshot.child("phone").getValue().toString());
                holder.date_v.setText("Date Created: " + u.date);
            }
            @Override
            public void onCancelled(@Nullable DatabaseError databaseError){

            }
        });
        holder.likeCountRef = database.getReference("Posts/" + u.postKey + "/likeCount");
        Log.d("LIKEC", u.postKey);
        holder.likeCountRefListener = holder.likeCountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                    holder.likeCount.setText(snapshot.getValue().toString() + " Likes");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.likesRef = database.getReference("Posts/" + u.postKey + "/likes/" + currentUser.getUid());
        holder.likesRefListener = holder.likesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getValue().toString().equals("true")){
                    holder.likeBtn.setImageDrawable(ContextCompat.getDrawable(r.getContext(), R.drawable.like));
                }
                else{
                    holder.likeBtn.setImageDrawable(ContextCompat.getDrawable(r.getContext(), R.drawable.like_disabled));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.likeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                database.getReference("Posts/" + u.postKey).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        PhotoPreview.Post p = currentData.getValue(PhotoPreview.Post.class);
                        if(p == null){
                            return Transaction.success(currentData);
                        }
                        if(p.likes.containsKey(currentUser.getUid())){
                            p.likeCount = p.likeCount - 1;
                            p.likes.remove(currentUser.getUid());
                        }
                        else{
                            p.likeCount = p.likeCount + 1;
                            p.likes.put(currentUser.getUid(), true);
                        }
                        currentData.setValue(p);
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                    }
                });
            }
        });
        holder.description_v.setText(u.description);
    }
    public void removeListener(){
        if(allPostsRef != null && usersRefListener != null){
            allPostsRef.removeEventListener(usersRefListener);
        }
    }
    @Override
    public int getItemCount(){return postsList.size();}
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageButton optionBtn;
        public TextView fname_v;
        public TextView email_v;
        public TextView phone_v;
        public TextView date_v;
        public TextView description_v;
        public ImageView imageView;
        public ImageView likeBtn;
        public TextView likeCount;
        DatabaseReference uref;
        ValueEventListener urefListener;
        DatabaseReference likeCountRef;
        ValueEventListener likeCountRefListener;
        DatabaseReference likesRef;
        ValueEventListener likesRefListener;
        public ViewHolder(View view){
            super(view);
            optionBtn = (ImageButton)view.findViewById(R.id.option_button);
            fname_v = (TextView)view.findViewById(R.id.fname_view);
            email_v = (TextView)view.findViewById(R.id.email_view);
            phone_v = (TextView)view.findViewById(R.id.phone_view);
            date_v = (TextView)view.findViewById(R.id.date_view);
            description_v = (TextView)view.findViewById(R.id.description_view);
            imageView = (ImageView)view.findViewById(R.id.postImg);
            likeBtn = (ImageView)view.findViewById(R.id.likeBtn);
            likeCount = (TextView) view.findViewById(R.id.likeCount);
        }
    }
}













































