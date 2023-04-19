package com.example.youtube.ui.detail.adapter

import Item
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.youtube.databinding.ItemDetailBinding

class DetailAdapter(var onItemClick: (Item) -> Unit) : Adapter<DetailAdapter.DetailViewHolder>() {
    private val data = arrayListOf<Item>()

    @SuppressLint("NotifyDataSetChanged")
    fun addData(newData: List<Item>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(ItemDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class DetailViewHolder(private val binding: ItemDetailBinding) : ViewHolder(binding.root){
        fun bind(item: Item) {
            binding.apply {
                Glide.with(binding.ivImage).load(item.snippet.thumbnails.maxres.url)
                    .into(binding.ivImage)
                tvTitle.text = item.snippet.title
                itemView.setOnClickListener{
                    onItemClick.invoke(item)
                }
            }
        }

    }
}