package com.example.youtube.data.remote

import PlaylistItem
import Playlists
import com.example.youtube.data.remote.model.Videos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("playlists")
    suspend fun getPlaylists(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults : Int
    ) : Response<Playlists>

    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("key") key : String,
        @Query("part") part : String,
        @Query("playlistId") channelId : String,
        @Query("maxResults") maxResults : Int
    ) : Response<PlaylistItem>

    @GET("videos")
    suspend fun getVideo(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("id") id: String
    ): Response<Videos>
}