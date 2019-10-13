package com.irsyadashari.retrofit_movie_catalogue.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.irsyadashari.retrofit_movie_catalogue.R
import com.irsyadashari.retrofit_movie_catalogue.adapter.MovieAdapter
import com.irsyadashari.retrofit_movie_catalogue.model.Movie
import com.irsyadashari.retrofit_movie_catalogue.model.MovieResponse
import com.irsyadashari.retrofit_movie_catalogue.service.APIInterface
import com.irsyadashari.retrofit_movie_catalogue.service.ApiClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import java.nio.channels.spi.AbstractSelectionKey

class MainActivity : AppCompatActivity() {

    private val TAG: String? = MainActivity::class.java.canonicalName
    private lateinit var movies: ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rvMovies.layoutManager = GridLayoutManager(applicationContext, 2)

        val apiKey = getString(R.string.api_key)
        val apiInterface: APIInterface = ApiClient.getClient().create(APIInterface::class.java)
        getLatestMovie(apiInterface, apiKey)
        getPopularMovies(apiInterface, apiKey)

        collapseImage.setOnClickListener {
            Toast.makeText(applicationContext, "Poster Gede", Toast.LENGTH_SHORT).show()
        }
    }

    fun getPopularMovies(apiInterface: APIInterface, apiKey: String) {
        val call: Call<MovieResponse> = apiInterface.getPopularMovie(apiKey)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("TAG", "Movie Size ${movies.size}")
            }

            override fun onResponse(call: Call<MovieResponse>?, response: Response<MovieResponse>?) {
                movies = response!!.body()!!.results
                Log.d("$TAG", "Movie Size ${movies.size}")
                rvMovies.adapter = MovieAdapter(movies)
            }

        })
    }

    fun getLatestMovie(apiInterface: APIInterface, apiKey: String) : Movie?{
        var movie : Movie? = null
        var call : Call<Movie> = apiInterface.getMovieLatest(apiKey)
        call.enqueue(object : Callback<Movie>{
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d("$TAG","GAGAL FETCH DATA POPULER")
            }

            override fun onResponse(call: Call<Movie>?, response: Response<Movie>?) {
            if(response != null){
                var originalTitle : String? = response.body()?.originalTitle
                var posterPath : String? = response.body()?.posterPath

                collapseToolbar.title = originalTitle
                if(posterPath == null){
                    collapseImage.setImageResource(R.drawable.ic_broken_image)
                }else{
                    val imageUrl = StringBuilder()

                    imageUrl.append(getString(R.string.base_path_poster)).append(posterPath)

                    Glide.with(applicationContext).load(imageUrl.toString()).into(collapseImage)
                }
            }
            }
        })
        return movie
    }
}
