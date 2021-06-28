package com.example.lab5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    private List<Map<String, ?>> md;                //List of all movies
    private ListFragment.OnItemSelectedListener onItemSelectedListener = null;   //Call back to the Activity
    public MyRecyclerAdapter(List<Map<String, ?>> list)     //Constructor
    {
        md = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView movie_name;
        public TextView movie_year;
        public ImageView poster_img;
        public ViewHolder(View view) {
            super(view);
            movie_name = (TextView) view.findViewById(R.id.movie_name);
            movie_year = (TextView) view.findViewById(R.id.movie_year);
            poster_img = (ImageView) view.findViewById(R.id.poster_photo);
        }
    }

    public Map getItem(int i){
        return md.get(i);
    }

    public void setOnListItemSelectedListener(ListFragment.OnItemSelectedListener listener){
        onItemSelectedListener= listener;
    }

    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        final ViewHolder view_holder = new ViewHolder(v);
        return view_holder;
    }

    @Override
    public void onBindViewHolder (ViewHolder holder, final int position){
        holder.movie_name.setText(md.get(position).get("name").toString());
        holder.movie_year.setText(md.get(position).get("year").toString());
        holder.poster_img.setImageResource(Integer.parseInt(md.get(position).get("image").toString()));
        ViewCompat.setTransitionName(holder.poster_img, md.get(position).get("name").toString());
        holder.poster_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                if(onItemSelectedListener != null) {
                    onItemSelectedListener.onListItemSelected(view,
                            Integer.parseInt(md.get(position).get("image").toString()),
                            md.get(position).get("name").toString(),
                            md.get(position).get("year").toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() { return md.size();}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
