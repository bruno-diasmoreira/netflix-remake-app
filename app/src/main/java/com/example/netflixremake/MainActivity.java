package com.example.netflixremake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

import com.example.netflixremake.adapter.MainAdapter;
import com.example.netflixremake.model.Category;
import com.example.netflixremake.util.CategoryTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryTask.CategoryLoader {

    private MainAdapter mainAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //testes
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }




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
