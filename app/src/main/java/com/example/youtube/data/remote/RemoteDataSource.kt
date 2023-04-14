package com.example.youtube.data.remote

import PlaylistItem
import Playlists
import com.example.youtube.BuildConfig
import com.example.youtube.core.network.BaseDataSource
import com.example.youtube.core.network.RetrofitClient
import com.example.youtube.core.network.result.Resource
import com.example.youtube.utils.Const
import com.example.youtube.utils.Const.part

class RemoteDataSource : BaseDataSource(){
    private val apiService : ApiService = RetrofitClient.create()

    suspend fun getPlaylists() : Resource<Playlists> = getResult {
        apiService.getPlaylists(
            BuildConfig.API_KEY,
            part,
            Const.channelId
        )
    }

    suspend fun getPlaylistItems(playlistId: String): Resource<PlaylistItem> {
        return getResult {
            apiService.getPlaylistItems(BuildConfig.API_KEY,
                part,
                playlistId
            )
        }
    }
}