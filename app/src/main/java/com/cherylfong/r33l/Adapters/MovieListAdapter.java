package com.cherylfong.r33l.Adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cherylfong.r33l.Objects.Movie;
import com.cherylfong.r33l.R;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private ArrayList<Movie> movieArrayList;
    private Context mContext;

    private static final String LOG_TAG = MovieListAdapter.class.getSimpleName();

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
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Log.d(LOG_TAG, "onCreateViewHolder called!");

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false );
        return new MovieViewHolder(view);
    }

    // take data at particular position and bind to view holder
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {

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


    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV;
        TextView releaseDateTV;
        TextView overviewTV;
        ImageView graphicIV;
        ProgressBar progressBar;

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
        }

        // binds the values to the corresponding layout components
        public void bindValues(Movie m) {


            titleTV.setText(m.getTitle());
            releaseDateTV.setText(m.getReleaseDate());
            overviewTV.setText(m.getOverview());

            String graphicURL = m.getPosterPath();

            if( mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                graphicURL = m.getBackdropPath();
            }

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
                            return false;
                        }
                    })
                    .into(graphicIV);
        }
    }
}
