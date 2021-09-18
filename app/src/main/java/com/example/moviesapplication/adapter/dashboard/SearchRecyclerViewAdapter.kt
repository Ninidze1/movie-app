package com.example.moviesapplication.adapter.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.databinding.DropDownItemBinding
import com.example.moviesapplication.entity.Search

typealias onModelClick = (id: Int) -> Unit
class SearchRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var onResultClick: onModelClick
    private val items = mutableListOf<Search>()

    inner class ViewHolder(private val binding: DropDownItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: Search
        fun bind() {
            model = items[absoluteAdapterPosition]
            binding.textView.text = model.title.toString()
            binding.root.setOnClickListener {
                model.id?.let { id -> onResultClick.invoke(id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            DropDownItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int = items.size

    fun addData(items: MutableList<Search>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeRemoved(0, items.size);
        notifyItemRangeChanged(0, items.size)
    }

}