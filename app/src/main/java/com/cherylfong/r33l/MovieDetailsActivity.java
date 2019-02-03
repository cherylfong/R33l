package com.cherylfong.r33l;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cherylfong.r33l.Objects.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.LOG_TAG;

public class MovieDetailsActivity extends YouTubeBaseActivity {

    private static final String LOG_TAG = MovieDetailsActivity.class.getSimpleName();

    private final String MOVIEDB_API_KEY = "api_key=3cb75c31ee53a94adb2a217c345c85b6";
    private static final String GOOGLE_API_KEY = "AIzaSyBM53G9kyPrysY-5bL-ZDECi7nJOA9ezKA";
    private static final String MOVIE_VIDEO_KEY_REQ = "https://api.themoviedb.org/3/movie/";
    Movie m;

    TextView titleTV, overviewTV, releaseDateTV;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

//        Using Parcel library instead!
//
//        String title = getIntent().getStringExtra("title");
//        String overview = getIntent().getStringExtra("overview");
//        double voteAvg = getIntent().getDoubleExtra("voteAvg", 1.0);
//        String releaseDate = getIntent().getStringExtra("releaseDate");

        m = Parcels.unwrap( getIntent().getParcelableExtra("movieObject"));

        String title = m.getTitle();
        String overview = m.getOverview();
        double voteAvg = m.getVoteAverage();
        String releaseDate = m.getReleaseDate();

        titleTV = findViewById(R.id.movie_title_detail_textView);
        titleTV.setText(title);

        overviewTV = findViewById(R.id.movie_overview_detail_textView);
        overviewTV.setText(overview);

        ratingBar = findViewById(R.id.movie_rating_detail);
        ratingBar.setRating((float)voteAvg);

        releaseDateTV = findViewById(R.id.movie_releaseDate_detail_textView);
        releaseDateTV.setText(releaseDate);

        youTubePlayerView = findViewById(R.id.movie_youtube_detail_player);

        // to handle JSON objects
        AsyncHttpClient client = new AsyncHttpClient();

        Log.d(LOG_TAG, "ID: "+ m.getId());

        String movieVideoInfoURL = MOVIE_VIDEO_KEY_REQ + m.getId() + "/videos?" + MOVIEDB_API_KEY;

        Log.d(LOG_TAG, "movieVideoInfoURL: "+ movieVideoInfoURL );

        client.get(movieVideoInfoURL, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                // results is a an array of JSON Objects
                try {

                    JSONArray resultsJArray = response.getJSONArray("results");
                    if(resultsJArray.length() == 0){

                        // TODO add placeholder if no trailer found!
                        return; // incase if it is empty
                    }
                    JSONObject movieVideoJSONObject = resultsJArray.getJSONObject(0); // only 1 json object returned
                    String videoKey = movieVideoJSONObject.getString("key");
                    youTubePlayerInitializer(videoKey);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }


        });

    }

    // to initialize youtube player based on movie selected
    private void youTubePlayerInitializer(final String videoKey){

        youTubePlayerView.initialize(GOOGLE_API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        youTubePlayer.cueVideo(videoKey);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });

    }
}
