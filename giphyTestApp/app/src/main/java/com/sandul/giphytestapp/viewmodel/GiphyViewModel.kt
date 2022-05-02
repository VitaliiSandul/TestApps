package com.sandul.giphytestapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sandul.giphytestapp.GiphyObject
import com.sandul.giphytestapp.repository.GiphyRepository
import kotlinx.coroutines.*

class  GiphyViewModel(private val repository: GiphyRepository): ViewModel(){

    val errorMessage = MutableLiveData<String>()
    val giphiesList = MutableLiveData<List<GiphyObject>>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getAllGiphies() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getAllGiphies()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    giphiesList.postValue(response.body()?.list)
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}