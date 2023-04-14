package com.example.youtube.ui.playlist

import Item
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.core.network.connection.ConnectionLiveData
import com.example.youtube.core.network.result.Status
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.ActivityPlaylistBinding
import com.example.youtube.ui.detail.DetailActivity
import com.example.youtube.ui.playlist.adapter.PlaylistAdapter

class PlayListActivity : BaseActivity<ActivityPlaylistBinding, PlaylistViewModel>() {

    private lateinit var adapter: PlaylistAdapter

    private lateinit var cld: ConnectionLiveData

    override fun setPlaylist() {
        super.setPlaylist()
        adapter = PlaylistAdapter(this::onItemClick)
    }

    override fun isConnection() {
        super.isConnection()

        val noInternetView = binding.noInternet
        val yesInternetView = binding.playlistLayout
        cld = ConnectionLiveData(application)

        cld.observe(this) { isConnected ->
            if (isConnected) {
                noInternetView.root.visibility = View.GONE
                yesInternetView.visibility = View.VISIBLE
            } else {
                noInternetView.root.visibility = View.VISIBLE
                yesInternetView.visibility = View.GONE
            }
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.loading.observe(this){
            binding.progressBar.isVisible = it
        }

        viewModel.getPlaylists().observe(this) {
            when(it.status) {
                Status.SUCCESS -> {
                    binding.rvPlaylist.layoutManager = LinearLayoutManager(this)
                    binding.rvPlaylist.adapter = adapter
                    it.data?.let { it1 -> adapter.setPlayList(it1.items) }
                    viewModel.loading.postValue(false)
                }
                Status.LOADING -> {
                    viewModel.loading.postValue(true)
                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    viewModel.loading.postValue(false)
                }
            }
        }
    }

    override fun inflateViewBinding(): ActivityPlaylistBinding {
        return ActivityPlaylistBinding.inflate(layoutInflater)
    }

    private fun onItemClick(item: Item){
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(ID, item.id)
        }
        intent.putExtra(ID, item.id)
        intent.putExtra(TITLE, item.snippet.title)
        intent.putExtra(DESC, item.snippet.description)
        intent.putExtra(COUNT,item.contentDetails.itemCount)
        startActivity(intent)
    }

    override val viewModel: PlaylistViewModel by lazy {
        ViewModelProvider(this)[PlaylistViewModel::class.java]
    }

    companion object {
        const val ID = "id"
        const val TITLE = "title"
        const val DESC = "desc"
        const val COUNT = "count"
        const val DURATION = "duration"
    }
}