# R33l
R33l (as in "Reel") is an android app that allows users to browse movies from the [The Movie Database API](http://docs.themoviedb.apiary.io/#).

## Week 2

### Features 

- [X] Expose details of a movie (e.g. ratings using RatingBar, overview and release date) in a separate activity.
- [X] Allow videos to be played in full-screen using the YouTubePlayerView.

#### Bonus Features

- [X] Trailers for popular movies are [played automatically when the movie is selected](https://github.com/cherylfong/R33l/blob/2296d80eb3adff1bb9dc83e04400d1292796788c/app/src/main/java/com/cherylfong/r33l/MovieDetailsActivity.java#L179).
  - [X] When clicking on a popular movie (i.e. a movie voted for more than 6.5 stars) the video plays immediately.
  - [X] Less popular videos don't play automatically on the detailed page.
- [X] [Overlayed play icon](https://github.com/cherylfong/R33l/blob/2296d80eb3adff1bb9dc83e04400d1292796788c/app/src/main/res/layout/high_vote_movie_list_item.xml#L24) on popular movies to indicate that the movie can be played.
- [X] [ButterKnife annotation library](https://github.com/cherylfong/R33l/blob/2296d80eb3adff1bb9dc83e04400d1292796788c/app/build.gradle#L36) to reduce view boilerplate.
- [X] [Rounded corners](https://github.com/cherylfong/R33l/blob/2296d80eb3adff1bb9dc83e04400d1292796788c/app/src/main/java/com/cherylfong/r33l/Adapters/MovieListAdapter.java#L213) for the images using the Glide transformations.

### Walkthough

<br/>
<img src="/img/week2_portait_demo.gif" >
<br/>
<br/>

<img src="/img/week2_landscape_demo.gif">

## Week 1

### Features
- [X] Users can view a list of movies currently playing in theaters from the Movie Database API.

### Bonus Features
- [X] Views are responsive for both landscape/portrait mode.
   - [X] [Portrait mode](https://github.com/cherylfong/R33l/tree/master/app/src/main/res/layout): shows movie title, poster image, release date and overview.
   - [X] [Landscape mode](https://github.com/cherylfong/R33l/tree/master/app/src/main/res/layout-land): uses backdrop image instead of poster image in addition to the title and movie overview.

- [X] Display a nice default [placeholder graphic](https://guides.codepath.com/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading i.e. [progress bar](https://github.com/cherylfong/R33l/blob/2296d80eb3adff1bb9dc83e04400d1292796788c/app/src/main/java/com/cherylfong/r33l/Adapters/MovieListAdapter.java#L226).
- [X] Improved the user interface by experimenting with styling and coloring.
- [X] For popular movies (i.e. a movie with Vote Average more than 6.5), the full [backdrop image is displayed](https://github.com/cherylfong/R33l/blob/2296d80eb3adff1bb9dc83e04400d1292796788c/app/src/main/java/com/cherylfong/r33l/Adapters/MovieListAdapter.java#L198). Otherwise, a poster image, the movie title, and overview is listed.
  - Heterogenous RecyclerViews and different ViewHolder layout files differentiate popular and less popular movies.

### Walkthough
<br/>
<img src="/img/week1_portait_demo.gif" >
<br/>
<br/>

<img src="/img/week1_landscape_demo.gif">

### Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Androids
