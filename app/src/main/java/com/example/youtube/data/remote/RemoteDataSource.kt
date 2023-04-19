package com.example.youtube.data.remote

import PlaylistItem
import Playlists
import com.example.youtube.BuildConfig
import com.example.youtube.core.network.BaseDataSource
import com.example.youtube.core.network.result.Resource
import com.example.youtube.utils.Const
import com.example.youtube.utils.Const.part
import org.koin.dsl.module

val remoteDataSource = module {
    factory { RemoteDataSource(get()) }
}

class RemoteDataSource(private val apiService: ApiService) : BaseDataSource(){

    suspend fun getPlaylists() : Resource<Playlists> = getResult {
        apiService.getPlaylists(
            BuildConfig.API_KEY,
            part,
            Const.channelId,
            20
        )
    }

    suspend fun getPlaylistItems(playlistId: String): Resource<PlaylistItem> {
        return getResult {
            apiService.getPlaylistItems(BuildConfig.API_KEY,
                part,
                playlistId,
                10
            )
        }
    }

    suspend fun getVideo(id: String?) = getResult {
        apiService.getVideo(
            BuildConfig.API_KEY,
            part,
            id!!
        )
    }
}