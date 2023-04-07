package com.example.youtube.ui

import Playlists
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube.BuildConfig
import com.example.youtube.base.BaseViewModel
import com.example.youtube.remote.ApiService
import com.example.youtube.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaylistViewModel : BaseViewModel() {
    private val  apiService : ApiService = RetrofitClient.create()

    fun playlists(): LiveData<Playlists>{
        return getPlaylists()
    }

     private fun getPlaylists(): LiveData<Playlists> {
        val data = MutableLiveData<Playlists>()
        apiService.getPlaylists(BuildConfig.API_KEY, "contentDetails, snippet", "UCYV8EwdZIIBagEZD11QtPwQ")
            .enqueue(object: Callback<Playlists>{
                override fun onResponse(call: Call<Playlists>, response: Response<Playlists>) {
                    if(response.isSuccessful){
                        data.value = response.body()
                    }
                }

                override fun onFailure(call: Call<Playlists>, t: Throwable) {
                    println(t.stackTrace)
                    // problems: 404 - not found, 401 - invalid token, 403 - available stop
                }

            })
        return data
    }
}