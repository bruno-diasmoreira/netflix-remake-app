package com.example.netflixremake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflixremake.adapter.MovieSimilarAdapter;
import com.example.netflixremake.model.Movie;
import com.example.netflixremake.model.MovieDetail;
import com.example.netflixremake.util.ImageDownloaderTask;
import com.example.netflixremake.util.MovieDetailTask;

import java.util.ArrayList;
import java.util.List;


public class MovieActivity extends AppCompatActivity implements MovieDetailTask.MovieDetailLoader {

    TextView txtTitle, txtDesc, txtCast;
    RecyclerView rvSimilarMovies;
    ImageView imageDetail;

    MovieSimilarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        txtTitle = findViewById(R.id.txt_title);
        txtCast = findViewById(R.id.txt_cast);
        txtDesc = findViewById(R.id.txt_desc);
        rvSimilarMovies = findViewById(R.id.rv_similar_movies);
        imageDetail = findViewById(R.id.image_view);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            getSupportActionBar().setTitle(null);
        }

        LayerDrawable drawable = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.shadows);

       /* if(drawable != null){
            Drawable drawableMovie = ContextCompat.getDrawable(this,R.drawable.captain);
            drawable.setDrawableByLayerId(R.id.shadow_drawable,drawableMovie);
            ((ImageView) findViewById(R.id.image_view)).setImageDrawable(drawable);
        }*/


        List<Movie> movies = new ArrayList<>();


        rvSimilarMovies.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new MovieSimilarAdapter(this, movies);
        rvSimilarMovies.setAdapter(adapter);


        Bundle extras = getIntent().getExtras();
        int idMovie = extras.getInt("idMovie");

        if (extras != null) {
            MovieDetailTask movieDetailTask = new MovieDetailTask(this);
            movieDetailTask.setMovieDetailLoader(this);
            movieDetailTask.execute("https://tiagoaguiar.co/api/netflix/" + idMovie);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResult(List<MovieDetail> movieDetails) {

        adapter.setMovies(movieDetails.get(0).getMoviesSimilar());
        adapter.notifyDataSetChanged();

        //details movie
        Movie movie = movieDetails.get(0).getMovie();
        txtTitle.setText(movie.getTitle());
        txtDesc.setText(movie.getDesc());
        txtCast.setText(getString(R.string.cast, movie.getCast()));

        ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask(imageDetail);
        imageDownloaderTask.setShadowEnabled(true);
        imageDownloaderTask.execute(movie.getIdUrl());
    }
}