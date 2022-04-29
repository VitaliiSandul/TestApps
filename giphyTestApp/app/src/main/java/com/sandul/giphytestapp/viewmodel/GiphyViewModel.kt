package com.sandul.giphytestapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sandul.giphytestapp.GiphyList
import com.sandul.giphytestapp.GiphyObject
import com.sandul.giphytestapp.repository.GiphyRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class  GiphyViewModel(private val repository: GiphyRepository): ViewModel(){
    val giphiesList = MutableLiveData<List<GiphyObject>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllGiphies() {
        val response = repository.getAllGiphies()
        response?.enqueue(object : Callback<GiphyList?> {
            override fun onResponse(call: Call<GiphyList?>, response: Response<GiphyList?>) {
                giphiesList.postValue(response.body()?.list)
            }

            override fun onFailure(call: Call<GiphyList?>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}