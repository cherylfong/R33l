package com.cherylfong.r33l.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movie {

    private String title;
    private String posterPath;
    private String overview;
    private String releaseDate;
    private String backdropPath;

    private static final String POSTERSIZE = "w185";
    private static final String BCKDROPSIZE = "w300";

    // handled in onCreate of MainActivity
    public Movie(JSONObject jsonObject) throws JSONException{

        title = jsonObject.getString("title");
        posterPath = jsonObject.getString("poster_path");
        overview = jsonObject.getString("overview");
        releaseDate = jsonObject.getString("release_date");
        backdropPath = jsonObject.getString("backdrop_path");
    }

    // getter methods
    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/"+ POSTERSIZE + posterPath;
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
