package com.example.youtube.ui.playlist.adapter


import Item
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.youtube.databinding.ItemPlaylistBinding

class PlaylistAdapter(var onItemClick: (Item) -> Unit) : Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    private var playlist = arrayListOf<Item>()

    inner class PlaylistViewHolder(private var binding: ItemPlaylistBinding) : ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(item: Item) {
            binding.apply {
                Glide.with(binding.ivPlaylist).load(item.snippet.thumbnails.maxres.url)
                    .into(binding.ivPlaylist)
                binding.tvDesc.text = item.snippet.title
                binding.tvVideoAmount.text = "${item.contentDetails.itemCount} videos"
                itemView.setOnClickListener {
                    onItemClick.invoke(item)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPlayList(list: List<Item>) {
        playlist.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.onBind(playlist[position])
    }

    override fun getItemCount(): Int = playlist.size

}