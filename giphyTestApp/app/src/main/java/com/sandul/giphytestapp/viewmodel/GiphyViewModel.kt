package com.sandul.giphytestapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sandul.giphytestapp.GiphyList
import com.sandul.giphytestapp.repository.GiphyRepository
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class  GiphyViewModel(private val repository: GiphyRepository): ViewModel(){

    val giphiesList = MutableLiveData<GiphyList>()

    fun getGiphyListObserver(): MutableLiveData<GiphyList>{
        return giphiesList
    }

    fun makeApiCall(){
        val retroInstance = repository.getAllGiphies()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(getGiphyListObserverRx())
    }

    private fun getGiphyListObserverRx(): Observer<GiphyList>{
        return object : Observer<GiphyList>{
            override fun onSubscribe(d: Disposable) { }

            override fun onNext(t: GiphyList) {
                giphiesList.postValue(t)
            }

            override fun onError(e: Throwable) {
                giphiesList.postValue(null)
            }

            override fun onComplete() { }
        }
    }
}