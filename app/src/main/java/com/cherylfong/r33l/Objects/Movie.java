package com.cherylfong.r33l.Objects;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Movie {

    private double voteAverage;
    private String title;
    private String posterPath;
    private String overview;
    private String releaseDate;
    private String backdropPath;

    private static final String POSTER_PORT = "w92";
    private static final String POSTER_LAND = "w185"; // landscape ... perhaps use "w92" for portrait
    private static final String BCKDROPSIZE = "original";

    // handled in onCreate of MoviesActivity
    public Movie(JSONObject jsonObject) throws JSONException{

        voteAverage = jsonObject.getDouble("vote_average");
        title = jsonObject.getString("title");
        posterPath = jsonObject.getString("poster_path");
        overview = jsonObject.getString("overview");
        releaseDate = jsonObject.getString("release_date");
        backdropPath = jsonObject.getString("backdrop_path");
    }

    // required bt parcel library
    // empty constructor
    public Movie() {}

    // getter methods
    public double getVoteAverage() { return voteAverage; }
    public String getTitle() {
        return title;
    }

    public String getPosterPath(String size) {

        String graphicSize;

        switch(size){
            case "PORT":
                graphicSize = POSTER_PORT;
                break;
            case "LAND":
                graphicSize = POSTER_LAND;
                break;
            default:
                graphicSize = "original";
                break;

        }

        return "https://image.tmdb.org/t/p/"+ graphicSize + posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return "Released: " + releaseDate;
    }

    public String getBackdropPath() {
        return "https://image.tmdb.org/t/p/"+ BCKDROPSIZE +backdropPath;
    }

    // to populate a list of movies
    public static ArrayList<Movie> createMovieList(JSONArray jsonArray) throws JSONException{

        ArrayList<Movie> movieArrayList = new ArrayList<>();

        for(int i=0; i< jsonArray.length(); i++){

            movieArrayList.add(new Movie(jsonArray.getJSONObject(i)));
        }

        return movieArrayList;
    }
}
