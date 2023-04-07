package com.example.youtube.ui


import Item
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.base.BaseActivity
import com.example.youtube.databinding.ActivityPlaylistBinding
import com.example.youtube.ui.content_playlist.ContentActivity

class PlayListActivity : BaseActivity<ActivityPlaylistBinding, PlaylistViewModel>() {

    private lateinit var adapter: PlaylistAdapter

    override val viewModel: PlaylistViewModel by lazy {
        ViewModelProvider(this)[PlaylistViewModel::class.java]
    }

    override fun setPlaylist() {
        super.setPlaylist()
        adapter = PlaylistAdapter(this::onItemClick)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.playlists().observe(this) {
            binding.rvPlaylist.layoutManager = LinearLayoutManager(this)
            binding.rvPlaylist.adapter = adapter
            adapter.setPlayList(it.items)
        }
    }

    override fun inflateViewBinding(): ActivityPlaylistBinding {
        return ActivityPlaylistBinding.inflate(layoutInflater)
    }

    private fun onItemClick(item: Item){
        val intent = Intent(this, ContentActivity::class.java).apply {
            putExtra(ID, item.snippet.title)
        }
        Toast.makeText(this, item.snippet.title, Toast.LENGTH_SHORT).show()
//        Toast.makeText(this, item.id, Toast.LENGTH_SHORT).show() - этот тост выдает ссылку  плейлиста
//        не понял точно, какой нужно выводить
        startActivity(intent)

    }
    companion object {
        const val ID = "id"
    }
}