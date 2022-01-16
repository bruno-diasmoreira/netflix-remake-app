package com.example.netflixremake;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflixremake.model.Movie;
import com.example.netflixremake.util.ImageDownloaderTask;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{


    private Context context;
    private List<Movie> movies;


    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {

        Movie item = movies.get(position);
        holder.bind(item);

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
            ImageView imageView = itemView.findViewById(R.id.img_movie);

            String url = "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/ec0a8037523531.5743724ddec89.jpg";

            new ImageDownloaderTask(imageView).execute(url);
        }
    }

}
