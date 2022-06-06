package com.sandul.giphytestapp.repository

import com.sandul.giphytestapp.retrofit.RetrofitService

class GiphyRepository constructor(private val retrofitService: RetrofitService) {
    fun getAllGiphies() = retrofitService.getGiphyList()
}