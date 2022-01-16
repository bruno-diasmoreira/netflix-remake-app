package com.example.netflixremake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflixremake.model.Movie;

import java.util.List;

public class MovieSimilarAdapter extends RecyclerView.Adapter<MovieSimilarAdapter.ViewHolder>{

    private Context context;
    private List<Movie> movies;

    public MovieSimilarAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.move_item_similar,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Movie m = movies.get(position);
        holder.bind(m);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Movie item) {
            ImageView image = itemView.findViewById(R.id.img_movie_similar);
            //image.setImageResource(item.getIdUrl());
        }
    }
}
