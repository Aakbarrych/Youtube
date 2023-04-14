package com.example.youtube.ui.playlist

import Playlists
import androidx.lifecycle.LiveData
import com.example.youtube.App
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.BaseViewModel

class PlaylistViewModel : BaseViewModel() {

    fun getPlaylists(): LiveData<Resource<Playlists>> {
        return App.repository.getPlaylists()
    }

}