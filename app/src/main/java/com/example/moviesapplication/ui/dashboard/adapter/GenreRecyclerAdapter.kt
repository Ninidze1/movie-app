package com.example.moviesapplication.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.dto.dashboard.Genre
import com.example.moviesapplication.databinding.GenresItemBinding

typealias genreClick = (genreId: Int, genre: String) -> Unit
class GenreRecyclerAdapter: RecyclerView.Adapter<GenreRecyclerAdapter.ItemHolder>() {

    lateinit var genreClick: genreClick
    private val items: MutableList<Genre> = mutableListOf()

    inner class ItemHolder(private val binding: GenresItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: Genre
        fun bind() {
            model = items[absoluteAdapterPosition]
            binding.genre.text = model.name

            binding.root.setOnClickListener {
                model.id?.let { it1 -> genreClick.invoke(it1, model.name.toString()) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(GenresItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = items.size

    fun addItems(item: MutableList<Genre>) {
        this.items.clear()
        this.items.addAll(item)
        notifyItemRangeChanged(0, item.size)
    }
}