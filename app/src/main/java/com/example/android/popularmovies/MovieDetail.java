package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new MovieDetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MovieDetailFragment extends Fragment {

        TheMovie movie_selected;
        String LOG_TAG= MovieDetailFragment.class.getName();

        public MovieDetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView=inflater.inflate(R.layout.fragment_movie_detail, container, false);

            Intent intent;
            intent = getActivity().getIntent();
            TextView txt=(TextView) rootView.findViewById(R.id.movie_detail);
            txt.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
            String json_movie=intent.getStringExtra(Intent.EXTRA_TEXT);

            try {
                movie_selected = new TheMovie(new JSONObject(json_movie));
            }catch (JSONException e){
                Log.d(LOG_TAG,e.toString());
            }

            ImageView imageView= (ImageView) rootView.findViewById(R.id.movie_poster1);
            TextView textView= (TextView) rootView.findViewById(R.id.movie_detail);
            TextView overviewText= (TextView) rootView.findViewById(R.id.movie_overview);
            TextView ratingText= (TextView) rootView.findViewById(R.id.movie_rating);
            TextView releaseDate= (TextView) rootView.findViewById(R.id.movie_release);

            String[] dateAray=movie_selected.getRleaseDate ().split("-");

            Log.d(LOG_TAG,dateAray[0]);

            Date anotherCurDate = new Date(Integer.parseInt(dateAray[0]), Integer.parseInt(dateAray[1]), Integer.parseInt(dateAray[2]));
            SimpleDateFormat formatter = new SimpleDateFormat("MMM d, y");
            String formattedDateString = formatter.format(anotherCurDate);
            Log.d(LOG_TAG,formattedDateString);

            Log.d(LOG_TAG, "Poster Path is: " + movie_selected.getPosterPath("w342"));
            Picasso.with(getActivity()).load(movie_selected.getPosterPath("w342")).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //imageView.setImageResource(mThumbIdi.get(position).getPosterPath());
            textView.setText(movie_selected.getMovieTitle());
            overviewText.setText(movie_selected.getOverview());
            ratingText.setText("Ratings: "+movie_selected.getRating()+"/10");
            releaseDate.setText("Releasing: "+formattedDateString);
            return rootView;
        }
    }
}
