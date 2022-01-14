package com.example.netflixremake.model;

import java.util.List;

public class Category {

    private String name;
    private List<Movie> movies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMovie(Movie movie){
        this.movies.add(movie);
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies(){
        return movies;
    }
}
