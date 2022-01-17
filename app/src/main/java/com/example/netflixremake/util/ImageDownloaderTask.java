package com.example.netflixremake.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.netflixremake.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewWeakReference;

    boolean shadowEnabled;

    public void setShadowEnabled(boolean shadowEnabled) {
        this.shadowEnabled = shadowEnabled;
    }

    public ImageDownloaderTask(ImageView imageView) {
        this.imageViewWeakReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        String urlImage = params[0];

        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(urlImage);

            urlConnection = (HttpURLConnection) url.openConnection();

            int responseCode = urlConnection.getResponseCode();

            if (responseCode != 200)
                return null;

            InputStream inputStream = urlConnection.getInputStream();

            if (inputStream != null) {
                return BitmapFactory.decodeStream(inputStream);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }


        return null;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if (isCancelled())
            bitmap = null;

        ImageView imageView = imageViewWeakReference.get();

        if (imageView != null && bitmap != null) {

            if (shadowEnabled) {
                LayerDrawable drawable = (LayerDrawable) ContextCompat.getDrawable(imageView.getContext(), R.drawable.shadows);
                if(drawable != null){
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                    drawable.setDrawableByLayerId(R.id.shadow_drawable,bitmapDrawable);
                    imageView.setImageDrawable(drawable);
                }
            }
            else {
                if (bitmap.getWidth() < imageView.getWidth() || bitmap.getHeight() < imageView.getHeight()) {
                    Matrix matrix = new Matrix();
                    matrix.postScale((float) imageView.getWidth() / (float) bitmap.getWidth(),
                            (float) imageView.getHeight() / (float) bitmap.getHeight());
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                }
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
