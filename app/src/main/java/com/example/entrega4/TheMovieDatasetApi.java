package com.example.entrega4;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDatasetApi {
    @GET("/3/movie/{category}")
    Call<MovieResults> listOfMovies(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
    @GET("/3/trending/{media_type}/{time_window}")
    Call<TrendingResults> listOfTrending(
            @Path("media_type") String media_type,
            @Path("time_window") String time_window,
            @Query("api_key") String apikey,
            @Query("page") int page
    );

    @GET("/3/tv/{category}")
    Call<SeriesResults> listOfSeries(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
    @GET("/3/tv/{tv_id}")
    Call<SeriesDetailsResults> listOfSerieDetals(
            @Path("tv_id") Integer tvid,
            @Query("api_key") String apikey
    );


    @GET("/3/movie/{movie_id}")
    Call<MovieDetailsResults> listOfMovieDetails(
            @Path("movie_id") Integer movie_id,
            @Query("api_key") String apikey
    );
    @GET("/3/genre/movie/list")
    Call<GenreResults> listOfGenres(
        @Query("api_key") String apiKey
    );
    @GET("/3/genre/tv/list")
    Call<GenreTvResults> listOfGenresTv(
            @Query("api_key") String apiKey
    );


    @GET("/3/movie/{movie_id}/credits")
    Call<CreditResults> listOfCredit(
            @Path("movie_id") Integer movieId,
            @Query("api_key") String apikey
    );
    @GET("/3/tv/{tv_id}/credits")
    Call<CreditResultTv> listOfCreditTv(
            @Path("tv_id") Integer tvId,
            @Query("api_key") String apikey
    );
    @GET("/3/movie/upcoming")
    Call<UpComingsDetails> listOfUpcomings(
            @Query("api_key") String apikey,
            @Query("page") int page


    );
    @GET("/3/movie/{movie_id}/similar")
    Call<RecomedadosResults> listOfRecomendados(
            @Path("movie_id") Integer movieId,
            @Query("api_key") String apikey
    );
}
