package com.irsyadashari.retrofit_movie_catalogue.service


import com.irsyadashari.retrofit_movie_catalogue.model.Movie
import com.irsyadashari.retrofit_movie_catalogue.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("movie/latest")
    fun getMovieLatest(@Query("api_key") apiKey: String): Call<Movie>
    @GET("movie/popular")
    fun getPopularMovie(@Query("api_key") apiKey: String): Call<MovieResponse>
}