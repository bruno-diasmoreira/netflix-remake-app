package com.example.netflixremake.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.netflixremake.model.Movie;
import com.example.netflixremake.model.MovieDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MovieDetailTask extends AsyncTask<String, Void, List<MovieDetail>> {


    private final WeakReference<Context> contextWeakReference;

    private ProgressDialog dialog;

    private MovieDetailLoader movieDetailLoader;

    public void setMovieDetailLoader(MovieDetailLoader movieDetailLoader) {
        this.movieDetailLoader = movieDetailLoader;
    }

    public MovieDetailTask(Context context) {
        this.contextWeakReference = new WeakReference<>(context);
    }


    @Override
    protected List<MovieDetail> doInBackground(String... params) {

        String url = params[0];

        try {
            URL requestUrl = new URL(url);


            HttpsURLConnection urlConnection = (HttpsURLConnection) requestUrl.openConnection();

            urlConnection.setReadTimeout(2000);
            urlConnection.setConnectTimeout(2000);

            int responseCode = urlConnection.getResponseCode();

            if (responseCode > 400) {
                throw new IOException("Erro ao se comunicar com o servidor");
            }


            InputStream inputStream = urlConnection.getInputStream();

            BufferedInputStream in = new BufferedInputStream(inputStream);

            String jsonAsString = toString(in);

            List<MovieDetail> movieDetails = getMovieDetails(new JSONObject(jsonAsString));

            in.close();

            return movieDetails;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Context context = this.contextWeakReference.get();
        if (context != null)
            dialog = ProgressDialog.show(context, "Carregando", "", true);
    }

    @Override
    protected void onPostExecute(List<MovieDetail> movieDetails) {
        super.onPostExecute(movieDetails);
        dialog.dismiss();
        if (movieDetailLoader != null) {
            movieDetailLoader.onResult(movieDetails);
        }
    }

    private String toString(InputStream is) throws IOException {

        byte[] bytes = new byte[1024];

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int lidos;

        while ((lidos = is.read(bytes)) > 0) {
            baos.write(bytes, 0, lidos);
        }

        return new String(baos.toByteArray());
    }


    private List<MovieDetail> getMovieDetails(JSONObject jsonObject) throws JSONException {

        List<MovieDetail> movieDetailList = new ArrayList<>();

        int id = jsonObject.getInt("id");
        String title = jsonObject.getString("title");
        String desc = jsonObject.getString("desc");
        String cast = jsonObject.getString("cast");
        String urlImage = jsonObject.getString("cover_url");

        Movie m = new Movie();
        m.setId(id);
        m.setTitle(title);
        m.setDesc(desc);
        m.setCast(cast);
        m.setIdUrl(urlImage);


        List<Movie> moviesSimilar = new ArrayList<>();

        JSONArray similarArray = jsonObject.getJSONArray("movie");

        for (int i = 0; i < similarArray.length(); i++) {

            JSONObject similarObj = similarArray.getJSONObject(i);

            int idSimilar = similarObj.getInt("id");
            String coverUrl = similarObj.getString("cover_url");

            Movie movie = new Movie();
            movie.setId(idSimilar);
            movie.setIdUrl(coverUrl);


            moviesSimilar.add(movie);
        }


        MovieDetail movieDetail = new MovieDetail(m, moviesSimilar);

        movieDetailList.add(movieDetail);


        return movieDetailList;
    }


    public interface MovieDetailLoader {
        void onResult(List<MovieDetail> movieDetails);
    }


}
