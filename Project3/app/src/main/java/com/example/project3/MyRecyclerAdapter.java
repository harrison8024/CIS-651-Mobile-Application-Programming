package com.example.project3;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>
        implements Filterable {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference allMovieRef = database.getReference("movies");
    ChildEventListener moviesRefListener;
    private List<Movie> movieList;
    private List<Movie> movieList_filtered;
    private List<String> keyList;
    private List<String> keyList_filtered;

    private OnListItemClickListener onListItemClickListener = null;   //Call back to the Activity
    public MyRecyclerAdapter(RecyclerView recyclerView)     //Constructor
    {
        movieList = new ArrayList<>();
        keyList = new ArrayList<>();
        moviesRefListener = allMovieRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Movie movieItem = new Movie(
                        snapshot.child("name").getValue().toString(),
                        snapshot.child("image").getValue().toString(),
                        snapshot.child("description").getValue().toString(),
                        snapshot.child("length").getValue().toString(),
                        snapshot.child("year").getValue().toString(),
                        Double.parseDouble(snapshot.child("rating").getValue().toString()),
                        snapshot.child("director").getValue().toString(),
                        snapshot.child("stars").getValue().toString(),
                        snapshot.child("url").getValue().toString());

                movieList.add(movieItem);
                keyList.add(snapshot.getKey());

                MyRecyclerAdapter.this.notifyItemInserted(movieList.size()-1);
                recyclerView.scrollToPosition(movieList.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                int index = keyList_filtered.indexOf(snapshot.getKey());
                movieList_filtered.remove(index);
                keyList_filtered.remove(index);
                MyRecyclerAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        movieList_filtered = movieList;
        keyList_filtered = keyList;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()) {
                    movieList_filtered = movieList;
                    keyList_filtered = keyList;
                } else {
                    List<Movie> filteredList = new ArrayList<>();
                    List<String> filteredKeyList = new ArrayList<>();
                    for(int i = 0; i < movieList.size(); i++){
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if(movieList.get(i).getName().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(movieList.get(i));
                            filteredKeyList.add(keyList.get(i));
                        }
                    }
                    movieList_filtered = filteredList;
                    keyList_filtered = filteredKeyList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = movieList_filtered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                movieList_filtered = (ArrayList<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView movie_name;
        public TextView movie_description;
        public ImageView poster_img;
        public ImageButton options_button;
        public ViewHolder(View view) {
            super(view);
            movie_name = (TextView) view.findViewById(R.id.movie_name);
            movie_description = (TextView) view.findViewById(R.id.movie_description);
            poster_img = (ImageView) view.findViewById(R.id.poster_photo);
            options_button = (ImageButton) view.findViewById(R.id.option_button);
        }
    }

    public Movie getItem(int i){
        return movieList_filtered.get(i);
    }

    public void setOnListItemClickListener(OnListItemClickListener listener){
        onListItemClickListener= listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        final ViewHolder view_holder = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(onListItemClickListener!=null){
                    onListItemClickListener.onItemClick(v, view_holder.getAdapterPosition());
                }
            }
        });
        return view_holder;

    }

    @Override
    public void onBindViewHolder (ViewHolder holder, final int position){

        holder.movie_name.setText(movieList_filtered.get(position).getName());
        holder.movie_description.setText(movieList_filtered.get(position).getDescription());
        Picasso.get().load(movieList_filtered.get(position).getImage()).into(holder.poster_img);
        holder.options_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OPTION CLICK", "option menu clicked KEY: " + keyList_filtered.get(position));
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.movie_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.duplicate_movie:
                                AlertDialog.Builder alertbox;
                                alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                                alertbox.setMessage("Are you sure to duplicate this movie?");
                                alertbox.setTitle("Duplicate Movie");
                                alertbox.setCancelable(true);
                                alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(v.getContext(),"Duplicate Movie: Yes", Toast.LENGTH_SHORT).show();
                                        String key = allMovieRef.push().getKey();
                                        key = key.concat("_new");
                                        Movie movie = movieList_filtered.get(position);
                                        Log.d("NEW KEY", "key: " + key);
                                        Map<String, Object> movieValues = movie.toMap();
                                        Map<String, Object> childUpdates = new HashMap<>();
                                        childUpdates.put(key, movie);
                                        allMovieRef.updateChildren(childUpdates);
                                        return;
                                    }

                                });
                                alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(v.getContext(), "Duplicate Movie: No", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                });
                                alertbox.show();
                                return true;
                            case R.id.delete_movie:
                                AlertDialog.Builder alertbox_d;
                                alertbox_d = new AlertDialog.Builder(v.getRootView().getContext());
                                alertbox_d.setMessage("Are you sure to delete this movie?");
                                alertbox_d.setTitle("Delete Movie");
                                alertbox_d.setCancelable(true);
                                alertbox_d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(v.getContext(),"Delete Movie: Yes", Toast.LENGTH_SHORT).show();
                                        allMovieRef.child(keyList_filtered.get(position)).removeValue();
                                        return;
                                    }
                                });
                                alertbox_d.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(v.getContext(),"Delete Movie: No", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                });
                                alertbox_d.show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList_filtered.size();}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
