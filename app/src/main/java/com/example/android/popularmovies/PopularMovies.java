package com.example.android.popularmovies;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class PopularMovies extends AppCompatActivity {

    public String LOG_TAG=PopularMovies.class.getSimpleName();
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        //setContentView(R.layout.progress_bar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new PopularMoviesFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_movies, menu);
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


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(LOG_TAG, "onAttachedToWindow invoked");
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.d(LOG_TAG, "onAttachFragment invoked");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(LOG_TAG, "onConfigurationChanged invoked");
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Log.d(LOG_TAG, "onContentChanged invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy invoked");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(LOG_TAG, "onDetachedFromWindow invoked");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(LOG_TAG, "onNewIntent invoked");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause invoked");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.d(LOG_TAG, "onPostCreate invoked");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(LOG_TAG, "onPostResume invoked");
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        boolean result = super.onPrepareOptionsMenu(menu);
        Log.d(LOG_TAG, "onPrepareOptionsMenu invoked");
        return result;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart invoked");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume invoked");
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(LOG_TAG, "onRestoreInstanceState invoked");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(LOG_TAG, "onWindowFocusChanged invoked");
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.d(LOG_TAG, "onUserLeaveHint invoked");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop invoked");
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "onSaveInstanceState invoked");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.d(LOG_TAG, "onUserInteraction invoked");
    }

}
