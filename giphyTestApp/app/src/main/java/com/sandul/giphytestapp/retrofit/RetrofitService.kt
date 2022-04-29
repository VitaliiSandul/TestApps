package com.sandul.giphytestapp.retrofit

import com.sandul.giphytestapp.GiphyList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val apiKey = "tGy2vIkfL8Qew4NnR6MP53Md3dJgsEoX"
const val BASE_URL = "https://api.giphy.com/v1/"

interface RetrofitService {
    @GET("gifs/trending?api_key=$apiKey")
    fun getGiphyList() : retrofit2.Call<GiphyList>

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}