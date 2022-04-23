package com.example.moviehub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviehub.R
import com.example.moviehub.data.model.Film
import com.example.moviehub.databinding.ItemFilmCardBinding

class MainFragmentListAdapter(
    private val listener: FilmItemListener
) : RecyclerView.Adapter<MainFragmentListAdapter.FilmsViewHolder>() {

    private val items = ArrayList<Film>()

    fun setItems(items: ArrayList<Film>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val binding: ItemFilmCardBinding =
            ItemFilmCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FilmsViewHolder(binding, listener)
    }

    class FilmsViewHolder(
        private val itemBinding: ItemFilmCardBinding,
        private val listener: FilmItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        private lateinit var film: Film

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Film) {
            this.film = item
            Glide.with(itemBinding.root)
                .load(item.posterUrl)
                .placeholder(R.color.shimmer_placeholder)
                .into(itemBinding.cardImage)
            itemBinding.cardRating.text = item.rating.toString()

            if (itemBinding.cardRating.text.contains("%")) {
                itemBinding.cardRating.isVisible = false
            }
        }

        override fun onClick(p0: View?) {
            listener.onClickedFilm(film)
        }
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size
}
