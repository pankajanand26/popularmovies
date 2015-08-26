package com.example.android.popularmovies;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pankajanand on 26/8/15.
 */
public class TheMovie {
    private int movieID,voteCount,voteAverage;
    private String movieTitle,moviePlot,posterPath;
    private JSONObject moviesJSON;

    public TheMovie(JSONObject movies){
        moviesJSON=movies;
    }

    //get the movie title
    String getMovieTitle(){
        try {
            movieTitle = moviesJSON.getString("original_title");
        }catch (JSONException e){
            Log.e("TheMovie Title","Unable to get the title.");
        }

        return movieTitle;
    }

    String getPosterPath(){
        try {
            posterPath = moviesJSON.getString("poster_path");
        }catch (JSONException e){
            Log.e("TheMovie Title","Unable to get the poster path.");
        }

        return "http://image.tmdb.org/t/p/"+"w92/"+posterPath;
    }
}
