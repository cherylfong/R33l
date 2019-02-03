package com.cherylfong.r33l;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.LOG_TAG;

public class MovieDetailsActivity extends YouTubeBaseActivity {

    private static final String LOG_TAG = MovieDetailsActivity.class.getSimpleName();

    private final String MOVIEDB_API_KEY = "api_key=3cb75c31ee53a94adb2a217c345c85b6";
    private static final String GOOGLE_API_KEY = "AIzaSyBM53G9kyPrysY-5bL-ZDECi7nJOA9ezKA";
    private static final String MOVIE_VIDEO_KEY_REQ = "https://api.themoviedb.org/3/movie/";
    Movie m;

//    TextView titleTV, overviewTV, releaseDateTV;
//    RatingBar ratingBar;
//    YouTubePlayerView youTubePlayerView;
//    ImageView noVideoIV;
//    ProgressBar progressBar;

    // Using Butterknife Library:

    @BindView(R.id.movie_title_detail_textView) TextView titleTV;
    @BindView(R.id.movie_overview_detail_textView) TextView overviewTV;
    @BindView(R.id.movie_releaseDate_detail_textView) TextView releaseDateTV;
    @BindView(R.id.movie_rating_detail) RatingBar ratingBar;
    @BindView(R.id.movie_youtube_detail_player) YouTubePlayerView youTubePlayerView;
    @BindView(R.id.no_trailer_detail_imageView) ImageView noVideoIV;
    @BindView(R.id.replace_image_progressbar_detail_imageView) ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // required by lib
        ButterKnife.bind(this);

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

//        titleTV = findViewById(R.id.movie_title_detail_textView);
        titleTV.setText(title);

//        overviewTV = findViewById(R.id.movie_overview_detail_textView);
        overviewTV.setText(overview);

//        ratingBar = findViewById(R.id.movie_rating_detail);
        ratingBar.setRating((float)voteAvg);

//        releaseDateTV = findViewById(R.id.movie_releaseDate_detail_textView);
        releaseDateTV.setText(releaseDate);

//        youTubePlayerView = findViewById(R.id.movie_youtube_detail_player);

//        progressBar = findViewById(R.id.replace_image_progressbar_detail_imageView);

//        noVideoIV = findViewById(R.id.no_trailer_detail_imageView);

        // to handle JSON objects
        AsyncHttpClient client = new AsyncHttpClient();

        Log.d(LOG_TAG, "ID: "+ m.getId());

        String movieVideoInfoURL = MOVIE_VIDEO_KEY_REQ + m.getId() + "/videos?" + MOVIEDB_API_KEY;

        // TODO remove: for demo purpose
        if(m.getId() == 504172 ){
           movieVideoInfoURL = "https://api.themoviedb.org/3/movie/209312/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        }

        Log.d(LOG_TAG, "movieVideoInfoURL: "+ movieVideoInfoURL );

        client.get(movieVideoInfoURL, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                // results is a an array of JSON Objects
                try {

                    JSONArray resultsJArray = response.getJSONArray("results");

                    if(resultsJArray.length() == 0){

                        // To test this if statement
                        // String movieVideoInfoURL = "https://api.themoviedb.org/3/movie/209312/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
                        //
                        Log.d(LOG_TAG, "results zero length");
                        replaceYouTubeVideo();
                        return;
                    }

                    JSONObject movieVideoJSONObject = resultsJArray.getJSONObject(0); // only 1 json object returned

                    // check if key is for YouTube
                    if( !movieVideoJSONObject.getString("site").equals("YouTube") ){

                        Log.d(LOG_TAG, "site != YouTube");
                        replaceYouTubeVideo();
                        return;
                    }

                    String videoKey = movieVideoJSONObject.getString("key");
                    youTubePlayerInitializer(videoKey);
                    youTubePlayerView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                Log.d(LOG_TAG, "Load from URL Failed");
                replaceYouTubeVideo();
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
                        progressBar.setVisibility(View.GONE);
                        if(m.getVoteAverage() >= 6.5){
                            youTubePlayer.loadVideo(videoKey);
                        }else{
                            youTubePlayer.cueVideo(videoKey);
                        }

                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });

    }

    // replaces YouTube video with the backdrop of the movie
    private void replaceYouTubeVideo(){

        progressBar.setVisibility(View.VISIBLE);
        youTubePlayerView.setVisibility(View.GONE);

        Toast.makeText(getApplicationContext(), "No Movie Trailer Found", Toast.LENGTH_LONG).show();

        Glide.with(getApplicationContext())
                .load(m.getBackdropPath())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        noVideoIV.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(noVideoIV);
    }
}
