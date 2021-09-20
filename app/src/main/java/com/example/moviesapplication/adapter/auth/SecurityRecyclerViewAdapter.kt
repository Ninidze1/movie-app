package com.example.moviesapplication.adapter.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.databinding.ButtonLayoutBinding
import com.example.moviesapplication.databinding.NumberItemBinding
import com.example.moviesapplication.entity.auth.ButtonModel

typealias click = (count: Int) -> Unit
class SecurityRecyclerViewAdapter(private var items: MutableList<ButtonModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var itemClick: click

    inner class NumberViewHolder(private var binding: NumberItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val model = items[absoluteAdapterPosition]
            binding.textView.text = model.number.toString()
            binding.root.setOnClickListener {
                itemClick(absoluteAdapterPosition)
            }
        }
    }

    inner class ActionViewHolder(private var binding: ButtonLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val model = items[absoluteAdapterPosition]
            model.src?.let {
                binding.buttonImg.setImageResource(it)
            }
            binding.root.setOnClickListener {
                itemClick(absoluteAdapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1)
            NumberViewHolder(
                NumberItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else
            ActionViewHolder(
                ButtonLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ActionViewHolder -> holder.bind()
            is NumberViewHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        val model = items[position]
        return if (model.src != null) {
            2
        } else {
            1
        }
    }
}