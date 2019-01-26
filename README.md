# R33l
R33l i.e. "Reel" is an app that allows users to browse movies from the [The Movie Database API](http://docs.themoviedb.apiary.io/#).

## Week 1

### Features
- [X] Users can view a list of movies currently playing in theaters from the Movie Database API.

### Bonus Features
- [X] Views are responsive for both landscape/portrait mode.
   - [X] Portrait mode: shows movie title, poster image, release date and overview.
   - [X] Landscape mode: uses backdrop image instead of poster image in addition to the title and movie overview.

- [ ] Display a nice default [placeholder graphic](https://guides.codepath.com/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading
- [ ] Improved the user interface by experimenting with styling and coloring.
- [ ] For popular movies (i.e. a movie voted for more than 5 stars), the full backdrop image is displayed. Otherwise, a poster image, the movie title, and overview is listed. Use Heterogenous RecyclerViews and use different ViewHolder layout files for popular movies and less popular ones.

### App Walkthough
<img src="YOUR_GIF_URL_HERE" width=250><br>

### Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Androids
