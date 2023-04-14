package com.example.youtube.ui.detail

import PlaylistItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube.App
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.BaseViewModel

class DetailViewModel : BaseViewModel() {

    private val mutableVideosId: MutableLiveData<List<String>> = MutableLiveData()
    val liveVideosId: LiveData<List<String>> = mutableVideosId

    fun getPlaylistItems(playlistId: String): LiveData<Resource<PlaylistItem>> {
        return App.repository.getPlaylistItems(playlistId)
    }
}