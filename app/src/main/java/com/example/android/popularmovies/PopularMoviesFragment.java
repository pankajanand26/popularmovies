package com.example.android.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class PopularMoviesFragment extends Fragment {
    private ImageAdapter mAdapter;
    private JSONArray movieList;
    public TheMovie[] movies=new TheMovie[20];
    public GridView gridview;
    public ArrayList<TheMovie> list1;
    public String sort;
    public ProgressBar pb,pb3;

    String LOG_TAG = PopularMoviesFragment.class.getSimpleName();

    public PopularMoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        pb=(ProgressBar) getActivity().findViewById(R.id.progressBar1);

        if(pb==null) {
            Log.d(LOG_TAG + "PB Initialization", "Progressbar initialisation");
        }

        if (savedInstanceState == null) {
            Log.d(LOG_TAG,"onCreate invoked");
            sort="popularity.desc";
            fetchMovies(sort,pb);

        }
        else{
            list1 =  savedInstanceState.getParcelableArrayList("key");
            sort=savedInstanceState.getString("sort_order");
           // bar = foo.toArray(new CustomObject[foo.size()]);
            movies= list1.toArray(new TheMovie[list1.size()]);
            if(movies[0] == null){
                Log.d(LOG_TAG, "Movie is null.");
                Log.d(LOG_TAG,sort);
                fetchMovies(sort,pb);
            }else{
                Log.d(LOG_TAG, movies[0].getJSON().toString());
            }

            Log.d(LOG_TAG, (String) ("onCreate invoked" + movies.length));

        }

        //Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    void setListAdapter(ImageAdapter m){

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.popularity) {
            sort="popularity.desc";
            fetchMovies(getString(R.string.popularity_based) + ".desc",pb);
            return true;
        }

        if(id==R.id.rating){
            sort="vote_count.desc";
            fetchMovies(getString(R.string.rating_based) + ".desc",pb);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG,"onCreateView invoked");
        final View rootview=inflater.inflate(R.layout.fragment_movies, container, false);
        //fetch popular movies and populate in the grid
        mAdapter = new ImageAdapter(getActivity(), Arrays.asList(movies));
        gridview = (GridView) rootview.findViewById(R.id.moviesgrid);
        //pb=(ProgressBar) rootview.findViewById(R.id.progressBar1);

        //defining the click listener for items in grid.
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                TheMovie move = (TheMovie) mAdapter.getItem(position);
                String movie_selected = move.getJSON();
                Intent intent1 = new Intent(getActivity(), MovieDetail.class).putExtra(Intent.EXTRA_TEXT, movie_selected);
                startActivity(intent1);

            }
        });

       if(savedInstanceState!=null && movies[0]!=null) {
           gridview.setAdapter(mAdapter);
       }


       return rootview;
    }


    //Converting JSON Strings to Movie objects.
    public void getMovies(JSONArray moviesJSON) {
        if (moviesJSON != null) {

            try {
                for (int i = 0;i<moviesJSON.length(); i++) {
                    movies[i] = new TheMovie(moviesJSON.getJSONObject(i));
                }
            } catch (JSONException e) {
                Log.e("TheMovie", "Unable to create movie object."+ e.toString());
            }
        }
    }

    public void fetchMovies(String sort,ProgressBar pb){
        FetchMovies fetchtask = new FetchMovies(pb);
 //       fetchtask.setProgressBar(pb);
        fetchtask.execute(sort);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        ArrayList<TheMovie> list= new ArrayList<TheMovie>(Arrays.asList(movies));
        outState.putParcelableArrayList("key", list);
        outState.putString("sort_order",sort);
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "onSaveInstanceState invoked");
    }

    public class FetchMovies extends AsyncTask<String,Void,JSONArray> {

        private final String LOG_TAG = FetchMovies.class.getSimpleName();
        private ProgressBar pb2;

        public FetchMovies(ProgressBar pb){
            this.pb2=pb;
        }

        public void setProgressBar(ProgressBar pb){
            this.pb2=pb;
        }

        @Override
        protected void onPreExecute() {
            if(pb2!=null) {
                pb2.setVisibility(View.VISIBLE);
            }
            //pb2.setVisibility(View.VISIBLE);
            //pb3=(ProgressBar) getActivity().findViewById(R.id.progressBar);
            //pb3.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONArray doInBackground(String... params) {

            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                return null;
            }

            JSONArray json;
            final String BASE_PATH = "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_PARM = "sort_by";
            final String API_KEY = "api_key";

            Uri builtUri = Uri.parse(BASE_PATH)
                    .buildUpon()
                    .appendQueryParameter(SORT_PARM, params[0])
                    .appendQueryParameter(API_KEY, "XXX")
                    .build();

            Log.v(LOG_TAG, builtUri.toString());

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
                    Log.d("JSON Movies",buffer.toString());
                    JSONObject j=new JSONObject(buffer.toString());
//                    json = (new JSONObject(buffer.toString())).getJSONArray("results");
                    json= j.getJSONArray("results");

                } catch (JSONException e) {
                    Log.e("JSON Exception", e.toString());
                    return null;
                }
                Log.v("Movies JSON", ((String) ("Movies JSON Array Length " + json.length())));

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
            if(pb2!=null) {
                pb2.setVisibility(View.GONE);
            }
           // pb2.setVisibility(View.INVISIBLE);
            //pb3.setVisibility(View.INVISIBLE);

            movieList=strings;
            Log.d("On AsyncTask",((String)("Length of movieList is " + movieList.length())));

            //converting movie objects.
            getMovies(movieList);

            //if there are no movies then write error message.
            if(movies!=null){

                //connecting the image adapter with the grid view.
                gridview.setAdapter(mAdapter);

               }
            else{
                Log.d("TheMovie","AAl izz Not Well.");
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "onActivityResult invoked");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(LOG_TAG, "onConfigurationChanged invoked");
    }

    @Override
    public void onActivityCreated(Bundle instate){
        super.onActivityCreated(instate);
        Log.d(LOG_TAG,"onActivityCreated invoked");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy invoked");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause invoked");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume invoked");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart invoked");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop invoked");
    }

}
