package com.example.youtube.repository

import Playlists
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube.BuildConfig
import com.example.youtube.core.network.ApiService
import com.example.youtube.core.network.result.Resource
import com.example.youtube.data.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    private val  apiService : ApiService = RetrofitClient.create()

    fun getPlaylists(): LiveData<Resource<Playlists>> {
        val data = MutableLiveData<Resource<Playlists>>()
        data.value = Resource.loading()

        apiService.getPlaylists(BuildConfig.API_KEY, "contentDetails, snippet", "UCYV8EwdZIIBagEZD11QtPwQ")
            .enqueue(object: Callback<Playlists> {
                override fun onResponse(call: Call<Playlists>, response: Response<Playlists>) {
                    if(response.isSuccessful){
                        data.value = Resource.success(response.body())
                    }
                }

                override fun onFailure(call: Call<Playlists>, t: Throwable) {
                    data.value = t.message?.let { Resource.error(it, null, 400) }
                    // problems: 404 - not found, 401 - invalid token, 403 - available stop
                }

            })
        return data
    }
}