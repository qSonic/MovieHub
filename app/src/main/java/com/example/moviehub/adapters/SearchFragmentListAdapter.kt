package com.example.moviehub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviehub.R
import com.example.moviehub.data.model.Film
import com.example.moviehub.databinding.ItemSearchCardBinding
import com.example.moviehub.util.Constants.MAX_DESCRIPTION_LENGTH

class SearchFragmentListAdapter(
    private val listener: FilmItemListener,
) : RecyclerView.Adapter<SearchFragmentListAdapter.SearchViewHolder>() {

    private val items = ArrayList<Film>()

    fun setItems(items: List<Film>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding: ItemSearchCardBinding =
            ItemSearchCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchViewHolder(binding, listener)
    }

    class SearchViewHolder(
        private val itemBinding: ItemSearchCardBinding,
        private val listener: FilmItemListener
    ) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        private lateinit var film: Film

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Film, holder: SearchViewHolder) {
            this.film = item
            Glide.with(itemBinding.root)
                .load(item.posterUrlPreview)
                .into(itemBinding.searchImage)
            itemBinding.filmTitle.text = item.nameRu

            itemBinding.filmTitleEn.text =
                holder.itemView.context.getString(R.string.format_name_with_year, item.nameEn, item.year)

            if (item.description !== null) {
                if (item.description.length > MAX_DESCRIPTION_LENGTH) itemBinding.filmDescription.text = item.slogan
                else itemBinding.filmDescription.text = item.description
            }
        }

        override fun onClick(p0: View?) {
            listener.onClickedFilm(film)
        }
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) = holder.bind(items[position], holder)

    override fun getItemCount() = items.size

    fun deleteFilm(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }
    fun getItem(position: Int): Film {
        return items[position]
    }
}
