package com.example.starwars.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwars.R
import com.example.starwars.models.Film
import kotlinx.android.synthetic.main.film_list_item.view.*

class FilmsAdapter(val clickListener : (Film) -> Unit) : RecyclerView.Adapter<FilmsAdapter.FilmViewHolder>() {

    var films : List<Film> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.film_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.setViewData(films.get(position), clickListener)
    }

    fun setFilmsList(filmsList : List<Film>) {
        films = filmsList
    }

    class FilmViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setViewData(film : Film, clickListener: (Film) -> Unit) {
            view.item_title.text = film.title
            view.item_director.text = film.director
            view.list_parent.setOnClickListener { clickListener(film) }
        }
    }
}