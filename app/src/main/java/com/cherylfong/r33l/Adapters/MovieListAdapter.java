package com.cherylfong.r33l.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cherylfong.r33l.MovieDetailsActivity;
import com.cherylfong.r33l.MoviesActivity;
import com.cherylfong.r33l.Objects.Movie;
import com.cherylfong.r33l.R;

import org.parceler.Parcels;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private ArrayList<Movie> movieArrayList;
    private Context mContext;

    private static final String LOG_TAG = MovieListAdapter.class.getSimpleName();


    private boolean votedHigh = false;
    private final int HIGH_VOTE = 1; //
    private final double VOTE_MIN= 6.5; // the minimum vote_average in order to be considered as a high vote

    private View view;

    /**
     * Constructor using the context and the db cursor
     * @param context the calling context/activity
     * @param movies contains a list of movies
     */
    public MovieListAdapter(Context context, ArrayList<Movie> movies) {
        this.mContext = context;
        this.movieArrayList = movies;
    }

    // inflates movie_list_item
    // strange that this method is not called every time for each position!
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Log.d(LOG_TAG, "onCreateViewHolder called!");

        LayoutInflater inflater = LayoutInflater.from(mContext);

        switch (viewType){

            case HIGH_VOTE:
                Log.d(LOG_TAG, "VOTED HIGH");
                view = inflater.inflate(R.layout.high_vote_movie_list_item, viewGroup, false );
                break;

            default:
                view = inflater.inflate(R.layout.movie_list_item, viewGroup, false );
                break;
        }

        return new MovieViewHolder(view);
    }

    // take data at particular position and bind to view holder
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {

        if(isVotedHigh(position)){
            votedHigh = true;
        }

        Log.d(LOG_TAG, "onBindViewHolder called: position " + position);
        Movie m = movieArrayList.get(position);

        // helper method to populate values into layout components
        movieViewHolder.bindValues(m);

    }

    // the number of movie objects to display
    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    //Returns constant value of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {

        if (movieArrayList.get(position).getVoteAverage() >= VOTE_MIN) {
            return HIGH_VOTE;
        } else {
            return 0;
        }
    }

    // check if a movie object is voted high
    private boolean isVotedHigh( int position ){

        if (movieArrayList.get(position).getVoteAverage() >= VOTE_MIN){
            return true;
        }

        return false;
    }

    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV;
        TextView releaseDateTV;
        TextView overviewTV;
        ImageView graphicIV;
        ProgressBar progressBar;
        RelativeLayout relativeLayout;

        /**
         * Constructor for ViewHolder. Within this constructor, get a reference to
         * TextViews
         *
         * @param itemView The View that was inflated in
         *                 {@link MovieListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public MovieViewHolder(View itemView) {
            super(itemView);

            // within movie_list_item.xml
            titleTV = itemView.findViewById(R.id.tile_textView);
            releaseDateTV = itemView.findViewById(R.id.releaseDate_textView);
            overviewTV = itemView.findViewById(R.id.overview_textView);
            graphicIV = itemView.findViewById(R.id.movieGraphic_imageView);
            progressBar = itemView.findViewById(R.id.progressBar);
            relativeLayout = itemView.findViewById(R.id.movie_list_item_container);
        }

        // binds the values to the corresponding layout components
        public void bindValues(final Movie m) {

            progressBar.setVisibility(View.VISIBLE);
            graphicIV.setVisibility(View.INVISIBLE);

            RequestOptions requestOptions = new RequestOptions();
//            requestOptions = requestOptions.transforms(new RoundedCorners(8));

            titleTV.setText(m.getTitle());
            releaseDateTV.setText(m.getReleaseDate());

            // set overview when not voted high
            if(!votedHigh){

                Log.d(LOG_TAG, "setText overview");
                overviewTV.setText(m.getOverview());
            }

            String graphicURL = m.getPosterPath("PORT");

            if( mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE || votedHigh){
                // set back to false, to determine next movie rating
                votedHigh = false;

                Log.d(LOG_TAG, "value of votedHigh: " + votedHigh);

                graphicURL = m.getBackdropPath();
            }

            Log.d(LOG_TAG, "GraphicURL: " + graphicURL);


            // THIS IS PERHAPS THE MOST IMPORTANT LINE !!!!
            // without fitCenter(), there will be extra space when applying just rounded corners!!!
            //
            requestOptions.fitCenter().transform(new RoundedCorners(30));

            // shows progress bar when movie image is loading
            Glide.with(mContext)
                    .load(graphicURL)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            graphicIV.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .apply(requestOptions)
                    .into(graphicIV);


            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,m.getTitle(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext, MovieDetailsActivity.class);

//                    Using Parcel library
//
//                    intent.putExtra("title", m.getTitle());
//                    intent.putExtra("overview", m.getOverview());
//                    intent.putExtra("voteAvg", m.getVoteAverage());
//                    intent.putExtra("releaseDate", m.getReleaseDate());

                    intent.putExtra("movieObject", Parcels.wrap(m));

                    mContext.startActivity(intent);

                }
            });
        }
    }
}
