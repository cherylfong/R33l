package com.cherylfong.r33l;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    private static final String API_KEY = "api_key=3cb75c31ee53a94adb2a217c345c85b6";
    private static final String NOW_PLAYING_REQ = "https://api.themoviedb.org/3/movie/now_playing?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
