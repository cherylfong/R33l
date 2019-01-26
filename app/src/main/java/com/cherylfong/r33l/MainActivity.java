package com.cherylfong.r33l;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cherylfong.r33l.Adapters.MovieListAdapter;
import com.cherylfong.r33l.Objects.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String API_KEY = "api_key=3cb75c31ee53a94adb2a217c345c85b6";
    // private static final String API_KEY = "api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"; //test key
    private static final String NOW_PLAYING_REQ = "https://api.themoviedb.org/3/movie/now_playing?";

    private MovieListAdapter mAdapter;

    ArrayList<Movie> moviesNowPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView movieListRV;
        movieListRV = this.findViewById(R.id.playing_now_movies_view);
        // vertical scrolling
        movieListRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        moviesNowPlaying = new ArrayList<>();

        // populate the recyclerview
        mAdapter = new MovieListAdapter(this, moviesNowPlaying);
        movieListRV.setAdapter(mAdapter);

        // to handle JSON objects
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_REQ+API_KEY, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    // results is a an array of JSON Objects
                    JSONArray resultsJArray = response.getJSONArray("results");

                    Log.d(LOG_TAG, "results JSON array: " + resultsJArray.toString());

                    // To maintain reference to movieNowPlaying for mAdapter line 46
                    // moviesNowPlaying = Movie.createMovieList(resultsJArray);
                    // possible to move lines 46 & 47 to bottom of onCreate & remove line 43
                    moviesNowPlaying.addAll(Movie.createMovieList(resultsJArray));
                    mAdapter.notifyDataSetChanged();

                    Log.d(LOG_TAG, "moviesNowPlaying arraylist: " + moviesNowPlaying.toString());


                }catch(Exception e){
                    e.printStackTrace();
                    Log.d(LOG_TAG, "Error: " + e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
