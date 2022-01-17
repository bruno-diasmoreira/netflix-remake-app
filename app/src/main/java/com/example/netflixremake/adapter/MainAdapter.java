package com.example.netflixremake.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflixremake.R;
import com.example.netflixremake.model.Category;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private final Context context;
    private List<Category> categories;

    TextView categoryTitle;
    RecyclerView rvCategoryMovie;

    public MainAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        categoryTitle.setText(categories.get(position).getName());

        rvCategoryMovie.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        rvCategoryMovie.setAdapter(new MovieAdapter(context,categories.get(position).getMovies()));


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.textView_title);
            rvCategoryMovie = itemView.findViewById(R.id.rv_category_movie);

        }
    }

}




