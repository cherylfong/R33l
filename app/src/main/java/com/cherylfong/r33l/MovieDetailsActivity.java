package com.cherylfong.r33l;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cherylfong.r33l.Objects.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie m;

    String title;
    String overview;
    double voteAvg;
    String releaseDate;

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

        title = m.getTitle();
        overview = m.getOverview();
        voteAvg = m.getVoteAverage();
        releaseDate = m.getReleaseDate();

        TextView titleTV = findViewById(R.id.movie_title_detail_textView);
        titleTV.setText(title);

        TextView overviewTV = findViewById(R.id.movie_overview_detail_textView);
        overviewTV.setText(overview);

        RatingBar ratingBar = findViewById(R.id.movie_rating_detail);
        ratingBar.setRating((float)voteAvg);

        TextView releaseDateTV = findViewById(R.id.movie_releaseDate_detail_textView);
        releaseDateTV.setText(releaseDate);

    }
}
