package com.example.youtube.data.remote

import PlaylistItem
import Playlists
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("playlists")
    suspend fun getPlaylists(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("channelId") channelId: String
    ) : Response<Playlists>

    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("key") key : String,
        @Query("part") part : String,
        @Query("playlistId") channelId : String
//        @Query("maxResults") maxResults : Int
    ) : Response<PlaylistItem>
}