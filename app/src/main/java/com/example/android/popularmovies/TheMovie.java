package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pankajanand on 26/8/15.
 */
public class TheMovie implements Parcelable {
    private int movieID,voteCount,voteAverage;
    private String movieTitle,moviePlot,posterPath,movieReleaseDate;
    private JSONObject moviesJSON;
    String LOG_TAG= TheMovie.class.getSimpleName();

    public TheMovie(JSONObject movies){
        moviesJSON=movies;
    }

    private TheMovie(Parcel in) {
        try {
            moviesJSON = new JSONObject(in.readString());
        }catch (JSONException e){
            Log.d("Parcelable","Parcelable Constructor");
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(moviesJSON.toString());
    }

    public static final Parcelable.Creator<TheMovie> CREATOR = new Parcelable.Creator<TheMovie>() {
        public TheMovie createFromParcel(Parcel in) {
            return new TheMovie(in);
        }

        public TheMovie[] newArray(int size) {
            return new TheMovie[size];
        }
    };

    //get the movie title
    String getMovieTitle(){
        try {
            movieTitle = moviesJSON.getString("original_title");
        }catch (JSONException e){
            Log.e(LOG_TAG,"Unable to get the title.");
        }

        return movieTitle;
    }

    public String getPosterPath( String size){
        try {
            posterPath = moviesJSON.getString("poster_path");
        }catch (JSONException e){
            Log.e(LOG_TAG,"Unable to get the poster path.");
        }

        return "http://image.tmdb.org/t/p/"+size+"/"+posterPath;
    }

    public String getJSON(){
        return moviesJSON.toString();
    }

    public String getOverview(){

        try {
            moviePlot = moviesJSON.getString("overview");
        }catch (JSONException e){
            Log.e(LOG_TAG,"Unable to get the overview.");
        }
        return moviePlot;
    }
    public String getRating(){

        try {
            moviePlot = moviesJSON.getString("vote_average");
        }catch (JSONException e){
            Log.e(LOG_TAG,"Unable to get the vote_average.");
        }
        return moviePlot;
    }

    public String getRleaseDate(){

        try {
            movieReleaseDate = moviesJSON.getString("release_date");
        }catch (JSONException e){
            Log.e(LOG_TAG,"Unable to get the release_date.");
        }
        return movieReleaseDate;
    }

}
