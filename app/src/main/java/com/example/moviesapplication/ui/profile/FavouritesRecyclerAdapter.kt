package com.example.moviesapplication.ui.profile


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.extensions.loadImg
import com.example.common.extensions.titleAdjustForDash
import com.example.data.dto.person.FavMovie
import com.example.moviesapplication.databinding.PosterItemBinding

typealias onFavouriteClick = (id: Int) -> Unit
class FavouritesRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var onFavouriteClick: onFavouriteClick
    private val items = mutableListOf<FavMovie>()

    inner class ViewHolder(private val binding: PosterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: FavMovie
        fun bind() {
            model = items[absoluteAdapterPosition]

            binding.title.titleAdjustForDash()
            val year = model.year?.substring(0, 4)
            binding.title.text = model.title
            binding.rating.text = model.vote.toString()
            binding.yearTv.text = year.toString()
            if (model.lang?.isNotEmpty() == true)
                binding.languageTv.text = model.lang.toString()
            model.poster?.let { binding.imageView.loadImg(com.example.common.utils.Constants.IMG_DOMAIN + model.poster) }

            binding.root.setOnClickListener {
                model.movieId?.let { it1 -> onFavouriteClick.invoke(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            PosterItemBinding.inflate(
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

    fun addData(items: MutableList<FavMovie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeRemoved(0, items.size)
        notifyItemRangeChanged(0, items.size)
    }

}