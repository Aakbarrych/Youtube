package com.example.youtube.ui.playlist

import Playlists
import androidx.lifecycle.LiveData
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.BaseViewModel
import com.example.youtube.repository.Repository

class PlaylistViewModel(private val repository: Repository) : BaseViewModel() {

    fun getPlaylists(): LiveData<Resource<Playlists>> {
        return repository.getPlaylists()
    }

}