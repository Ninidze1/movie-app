package com.example.moviesapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.databinding.PosterItemBinding
import com.example.moviesapplication.entity.MovieItem
import com.example.moviesapplication.extensions.loadImg
import com.example.moviesapplication.utils.Constants.IMG_DOMAIN

class DashBoardRecyclerAdapter: RecyclerView.Adapter<DashBoardRecyclerAdapter.ItemHolder>() {
    private val items: MutableList<MovieItem> = mutableListOf()

    inner class ItemHolder(private val binding: PosterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: MovieItem
        fun bind() {
            model = items[absoluteAdapterPosition]

            val year = model.firstAirDate?.substring(0,4)
            binding.title.text = model.name
            binding.rating.text = model.voteAverage.toString()
            binding.yearTv.text = year
            binding.languageTv.text = model.origin?.get(0).toString()
            model.backdrop_path?.let { binding.imageView.loadImg(IMG_DOMAIN+model.posterPath) }
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