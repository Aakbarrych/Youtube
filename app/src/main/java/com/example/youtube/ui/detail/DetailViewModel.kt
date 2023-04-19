package com.example.youtube.ui.detail

import PlaylistItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.BaseViewModel
import com.example.youtube.repository.Repository

class DetailViewModel(private val repository: Repository) : BaseViewModel() {

    private val mutableVideosId: MutableLiveData<List<String>> = MutableLiveData()
    val liveVideosId: LiveData<List<String>> = mutableVideosId

    fun getPlaylistItems(playlistId: String): LiveData<Resource<PlaylistItem>> {
        return repository.getPlaylistItems(playlistId)
    }
}