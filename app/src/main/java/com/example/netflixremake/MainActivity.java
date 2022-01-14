package com.example.netflixremake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.netflixremake.model.Category;
import com.example.netflixremake.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvMain;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMain = findViewById(R.id.rv_main);

        rvMain.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        List<Category> categories = new ArrayList<>();

        List<String> array = new ArrayList<>();
        array.add("Aventura");
        array.add("Ação");
        array.add("Drama");
        array.add("Terror");
        array.add("Desenho");

        for(int i = 0; i < array.size(); i++) {

            Category category = new Category();
            category.setName(array.get(i));


            List<Movie> movies = new ArrayList<>();

            for(int j = 0; j < 25; j++){
                Movie m = new Movie();
                m.setIdUrl(R.drawable.placeholder_bg);
                movies.add(m);
            }

            category.setMovies(movies);
            categories.add(category);
        }

        mainAdapter = new MainAdapter(this,categories);
        rvMain.setAdapter(mainAdapter);

    }
}
