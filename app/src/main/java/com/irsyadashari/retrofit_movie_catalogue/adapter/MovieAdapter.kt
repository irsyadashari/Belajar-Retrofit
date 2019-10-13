package com.irsyadashari.retrofit_movie_catalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irsyadashari.retrofit_movie_catalogue.R
import com.irsyadashari.retrofit_movie_catalogue.model.Movie
import kotlinx.android.synthetic.main.movie_list.view.*

class MovieAdapter(val movies: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list,parent,false)
        return MovieViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies.get(position))
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        private var view : View = itemView
        private var movie: Movie? = null

        override fun onClick(p0: View?) {
            Toast.makeText(view.context, "Item diklik", Toast.LENGTH_SHORT).show()
        }

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie){
            this.movie = movie
            val imageUrl = StringBuilder()

            imageUrl.append(view.context.getString(R.string.base_path_poster)).append(movie.posterPath)
            view.movieTitle.setText(movie.originalTitle)

            Glide.with(view.context).load(imageUrl.toString()).into(view.moviePoster)
        }

    }

}