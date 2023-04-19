package com.example.youtube.repository

import PlaylistItem
import Playlists
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.youtube.core.network.result.Resource
import com.example.youtube.data.remote.RemoteDataSource
import com.example.youtube.data.remote.model.Videos
import kotlinx.coroutines.Dispatchers

class Repository(private val dataSource: RemoteDataSource) {

    fun getPlaylists(): LiveData<Resource<Playlists>> = liveData(Dispatchers.IO){
        emit(Resource.loading())
        val response = dataSource.getPlaylists()
        emit(response)
    }

    fun getPlaylistItems(playlistId: String): LiveData<Resource<PlaylistItem>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val response = dataSource.getPlaylistItems(playlistId)
            emit(response)
        }

    fun getVideo(id: String): LiveData<Resource<Videos>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val response = dataSource.getVideo(id)
            emit(response)
        }
    }
}