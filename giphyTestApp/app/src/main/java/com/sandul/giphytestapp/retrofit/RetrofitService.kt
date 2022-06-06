package com.sandul.giphytestapp.retrofit

import com.sandul.giphytestapp.GiphyList
import com.sandul.giphytestapp.GiphyObject
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val apiKey = "tGy2vIkfL8Qew4NnR6MP53Md3dJgsEoX"
const val BASE_URL = "https://api.giphy.com/v1/"

interface RetrofitService {
    @GET("gifs/trending?api_key=$apiKey")
    fun getGiphyList() : Observable<GiphyList>

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}