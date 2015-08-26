package com.example.android.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class PopularMoviesFragment extends Fragment {
    private ImageAdapter mAdapter;
    private JSONArray movieList;
    public TheMovie[] movies;

    public PopularMoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootview=inflater.inflate(R.layout.fragment_movies, container, false);;

        FetchMovies fetchtask = new FetchMovies();
        fetchtask.execute();

        movies=getMovies(movieList);
        mAdapter = new ImageAdapter(getActivity(), Arrays.asList(movies));


        GridView gridview = (GridView) rootview.findViewById(R.id.moviesgrid);
        gridview.setAdapter(mAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return rootview;

    }

    public TheMovie[] getMovies(JSONArray moviesJSON) {
        TheMovie[] list=new TheMovie[20];
        try{
            for( int i=0;i<moviesJSON.length();i++) {
            list[i] = new TheMovie(moviesJSON.getJSONObject(i));
            }
        }catch (JSONException e){
            Log.e("TheMovie","Unable tovreate movie object.");
        }

        return list;
    }

    public class FetchMovies extends AsyncTask<String,Void,JSONArray> {

        private final String LOG_TAG = FetchMovies.class.getSimpleName();

        @Override
        protected JSONArray doInBackground(String... params) {

            // If there's no zip code, there's nothing to look up.  Verify size of params.
            //if (params.length == 0) {
              //  return null;
           // }

            JSONArray json;
            final String BASE_PATH = "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_PARM = "sort_by";
            final String API_KEY = "api_key";

            Uri builtUri = Uri.parse(BASE_PATH)
                    .buildUpon()
                    .appendQueryParameter(SORT_PARM, "popularity.desc")
                    .appendQueryParameter(API_KEY, "206bcb4d43725484275829800db443c9")
                    .build();

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                URL url = new URL(builtUri.toString());
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                try {
                    json = (new JSONObject(buffer.toString())).getJSONArray("results");
                } catch (JSONException e) {
                    Log.e("JSON Exception", e.toString());
                    return null;
                }
                Log.v("Movies JSON", "Movies JSON Array Length : " + json.length());

            } catch (IOException e) {
                Log.e("Movies JSON", "Error", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Movies JSON", "Error closing stream", e);
                    }
                }
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONArray strings) {
            movieList=strings;
        }

   }

}