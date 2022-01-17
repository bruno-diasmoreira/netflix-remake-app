package com.example.netflixremake.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflixremake.MovieActivity;
import com.example.netflixremake.R;
import com.example.netflixremake.model.Movie;
import com.example.netflixremake.util.ImageDownloaderTask;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> implements OnItemClickListener {


    private final Context context;
    private List<Movie> movies;


    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);

        return new MovieViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Movie item = movies.get(position);
        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onClick(int position) {
        if (movies.get(position).getId() <= 3) {
            Intent it = new Intent(context, MovieActivity.class);
            it.putExtra("idMovie", movies.get(position).getId());
            context.startActivity(it);
        }
    }


    static class MovieViewHolder extends RecyclerView.ViewHolder {

        public MovieViewHolder(@NonNull View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(view -> {
                onItemClickListener.onClick(getAdapterPosition());
            });

        }

        public void bind(Movie item) {
            ImageView imageView = itemView.findViewById(R.id.img_movie);

            String url = "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/ec0a8037523531.5743724ddec89.jpg";

            new ImageDownloaderTask(imageView).execute(item.getIdUrl());
        }
    }


}

interface OnItemClickListener {
    void onClick(int position);
}
