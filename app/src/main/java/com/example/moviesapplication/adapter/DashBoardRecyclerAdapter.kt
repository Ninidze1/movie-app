package com.example.moviesapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.entity.MovieItem
import com.example.moviesapplication.databinding.PosterItemBinding
import com.example.moviesapplication.extensions.loadImg

class DashBoardRecyclerAdapter: RecyclerView.Adapter<DashBoardRecyclerAdapter.ItemHolder>() {
    private val items: MutableList<MovieItem> = mutableListOf()

    inner class ItemHolder(private val binding: PosterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: MovieItem
        fun bind() {
            model = items[absoluteAdapterPosition]
            binding.title.text = model.name
            binding.rating.text = model.voteAverage.toString()
            val baseUrl = "https://image.tmdb.org/t/p/w500"
            model.backdrop_path?.let { binding.imageView.loadImg(baseUrl+model.posterPath) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(PosterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = items.size

    fun addItems(item: MutableList<MovieItem>) {
        this.items.clear()
        this.items.addAll(item)
        notifyItemRangeChanged(0, item.size)
    }
}