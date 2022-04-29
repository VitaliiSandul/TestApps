package com.sandul.giphytestapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    private val BASE_URL = "https://api.giphy.com/v1/"
    private  val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getService(): GiphyApiInterface? {
        return retrofit.create(GiphyApiInterface::class.java)
    }
}