package com.example.netflixremake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.netflixremake.model.Category;
import com.example.netflixremake.model.Movie;
import com.example.netflixremake.util.CategoryTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryTask.CategoryLoader {

    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvMain = findViewById(R.id.rv_main);

        rvMain.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        List<Category> categories = new ArrayList<>();

        mainAdapter = new MainAdapter(this,categories);
        rvMain.setAdapter(mainAdapter);


       CategoryTask categoryTask =  new CategoryTask(this);
       categoryTask.setCategoryLoader(this);
       categoryTask.execute("https://tiagoaguiar.co/api/netflix/home");

    }

    @Override
    public void onResult(List<Category> categories) {
        mainAdapter.setCategories(categories);
        mainAdapter.notifyDataSetChanged();
    }
}
