package com.example.moviesapplication.ui.single_movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.extensions.loadImg
import com.example.data.dto.detail.Cast
import com.example.moviesapplication.databinding.CastItemLayoutBinding

class CastRecyclerAdapter: RecyclerView.Adapter<CastRecyclerAdapter.ItemHolder>() {

    private val items: MutableList<Cast> = mutableListOf()

        inner class ItemHolder(private val binding: CastItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: Cast
        fun bind() {
            model = items[absoluteAdapterPosition]
            model.profilePath?.let { binding.castImg.loadImg(com.example.common.utils.Constants.IMG_DOMAIN + model.profilePath) }
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