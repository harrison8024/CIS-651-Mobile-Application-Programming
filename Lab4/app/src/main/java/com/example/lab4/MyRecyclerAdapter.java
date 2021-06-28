package com.example.lab4;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>
        implements Filterable {
    private List<Map<String, ?>> md;                //List of all movies
    private List<Map<String, ?>> md_filtered;       //List of filtered movies
    private OnListItemClickListener onListItemClickListener = null;   //Call back to the Activity
    public MyRecyclerAdapter(List<Map<String, ?>> list)     //Constructor
    {
        md = md_filtered = list;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()) {
                    md_filtered = md;
                } else {
                    List<Map<String, ?>> filteredList = new ArrayList<>();
                    for(Map movie:md){
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if(movie.get("name").toString().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(movie);
                        }
                    }
                    md_filtered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = md_filtered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                md_filtered = (ArrayList<Map<String, ?>>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
        return md_filtered.get(i);
    }

    public void setOnListItemClickListener(OnListItemClickListener listener){
        onListItemClickListener= listener;
    }

    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
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
        holder.movie_name.setText(md_filtered.get(position).get("name").toString());
        holder.movie_year.setText(md_filtered.get(position).get("year").toString());
        holder.poster_img.setImageResource(Integer.parseInt(md_filtered.get(position).get("image").toString()));
    }

    @Override
    public int getItemCount() { return md_filtered.size();}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
