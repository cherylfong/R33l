# R33l
R33l (as in "Reel") is an android app that allows users to browse movies from the [The Movie Database API](http://docs.themoviedb.apiary.io/#).

## Week 2

### Features 

- [ ] Expose details of movie (ratings using RatingBar, popularity, and synopsis) in a separate activity.
- [ ] Allow video posts to be played in full-screen using the YouTubePlayerView.

#### Bonus Features

- [ ] Trailers for popular movies are played automatically when the movie is selected.
  - [ ] When clicking on a popular movie (i.e. a movie voted for more than 5 stars) the video should be played immediately.
  - [ ] Less popular videos rely on the detailed page should show an image preview that can initiate playing a YouTube video.
- [ ] Add a play icon overlay to popular movies to indicate that the movie can be played.
- [ ] Apply the popular ButterKnife annotation library to reduce view boilerplate.
- [ ] Add a rounded corners for the images using the Glide transformations.

### Walkthough

<img src="YOUR_GIF_URL_HERE" width=250><br>

## Week 1

### Features
- [X] Users can view a list of movies currently playing in theaters from the Movie Database API.

### Bonus Features
- [X] Views are responsive for both landscape/portrait mode.
   - [X] Portrait mode: shows movie title, poster image, release date and overview.
   - [X] Landscape mode: uses backdrop image instead of poster image in addition to the title and movie overview.

- [X] Display a nice default [placeholder graphic](https://guides.codepath.com/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading i.e. progress bar.
- [X] Improved the user interface by experimenting with styling and coloring.
- [X] For popular movies (i.e. a movie with Vote Average more than 6.5), the full backdrop image is displayed. Otherwise, a poster image, the movie title, and overview is listed. Use Heterogenous RecyclerViews and use different ViewHolder layout files for popular movies and less popular ones.

### Walkthough
<br/>
<img src="/img/week1_portait_demo.gif" >
<br/>
<br/>

<img src="/img/week1_landscape_demo.gif">

### Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Androids
