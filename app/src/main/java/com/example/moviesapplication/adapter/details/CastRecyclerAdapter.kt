package com.example.moviesapplication.adapter.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.databinding.CastItemLayoutBinding
import com.example.moviesapplication.entity.Cast
import com.example.moviesapplication.extensions.loadImg
import com.example.moviesapplication.utils.Constants

class CastRecyclerAdapter: RecyclerView.Adapter<CastRecyclerAdapter.ItemHolder>() {

    private val items: MutableList<Cast> = mutableListOf()

        inner class ItemHolder(private val binding: CastItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: Cast
        fun bind() {
            model = items[absoluteAdapterPosition]
            model.profilePath?.let { binding.castImg.loadImg(Constants.IMG_DOMAIN + model.profilePath) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(CastItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = items.size

    fun addItems(item: MutableList<Cast>) {
        this.items.clear()
        this.items.addAll(item)
        notifyItemRangeChanged(0, item.size)
    }

}