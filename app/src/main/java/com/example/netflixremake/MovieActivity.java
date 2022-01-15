package com.example.netflixremake;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflixremake.model.Movie;

import java.util.ArrayList;
import java.util.List;


public class MovieActivity extends AppCompatActivity {

    TextView txtTitle,txtDesc,txtCast;
    RecyclerView rvSimilarMovies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        txtTitle = findViewById(R.id.txt_title);
        txtCast = findViewById(R.id.txt_cast);
        txtDesc = findViewById(R.id.txt_desc);
        rvSimilarMovies = findViewById(R.id.rv_similar_movies);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
            getSupportActionBar().setTitle(null);
        }

        LayerDrawable drawable = (LayerDrawable) ContextCompat.getDrawable(this,R.drawable.shadows);

        if(drawable != null){
            Drawable drawableMovie = ContextCompat.getDrawable(this,R.drawable.captain);
            drawable.setDrawableByLayerId(R.id.shadow_drawable,drawableMovie);
            ((ImageView) findViewById(R.id.image_view)).setImageDrawable(drawable);
        }

        txtTitle.setText("Capitão América: O Primeiro Vingador");

        txtDesc.setText("2ª Guerra Mundial. Steve Rogers (Chris Evans) é um jovem que aceitou ser voluntário em uma " +
                "série de experiências que visam criar o supersoldado americano. Os militares conseguem transformá-lo em uma " +
                "arma humana, mas logo percebem que o supersoldado é valioso demais para pôr em risco na luta contra os nazistas. Desta forma, Rogers é usado como uma celebridade do exército, marcando presença em paradas realizadas pela Europa no intuito de levantar a estima dos combatentes. " +
                "Para tanto passa a usar uma vestimenta com as cores da bandeira dos Estados Unidos, azul, branca e vermelha. Só que um plano nazista faz com que Rogers entre em ação e assuma a alcunha de Capitão América, usando seus dons para combatê-los em plenas trincheiras da guerra.");

        txtCast.setText(getString(R.string.cast,"Chris Evans, Hayley Atwell, Sebastian Stan, Tommy Lee Jones, Hugo Weaving, Dominic Cooper, Stanley Tucci"));


        List<Movie> movies = new ArrayList<>();

        for(int j = 0; j < 30; j++){
            Movie m = new Movie();
            //m.setIdUrl(R.drawable.placeholder_bg);
            movies.add(m);
        }


        rvSimilarMovies.setLayoutManager(new GridLayoutManager(this,3));
        MovieSimilarAdapter adapter = new MovieSimilarAdapter(this,movies);
        rvSimilarMovies.setAdapter(adapter);

    }
}