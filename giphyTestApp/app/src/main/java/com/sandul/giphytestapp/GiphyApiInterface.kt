package com.sandul.giphytestapp

import retrofit2.http.GET

const val apiKey = "tGy2vIkfL8Qew4NnR6MP53Md3dJgsEoX"

interface GiphyApiInterface {
    @GET("gifs/trending?api_key=$apiKey")
    fun getGiphyList() : retrofit2.Call<GiphyList>
}