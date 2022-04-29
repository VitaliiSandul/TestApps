package com.sandul.giphytestapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sandul.giphytestapp.adapter.GiphyAdapter
import com.sandul.giphytestapp.databinding.ActivityMainBinding
import com.sandul.giphytestapp.repository.GiphyRepository
import com.sandul.giphytestapp.retrofit.RetrofitService
import com.sandul.giphytestapp.viewmodel.GiphyViewModel
import com.sandul.giphytestapp.viewmodel.GiphyViewModelFactory

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: GiphyViewModel
    private val retrofitService = RetrofitService.getInstance()
    val adapter = GiphyAdapter{
        val intent = Intent(this@MainActivity, FullgiphyActivity::class.java).apply {
                    putExtra("title",it?.title)
                    putExtra("imageUrl",it?.images?.imageOriginal?.url)
                }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, GiphyViewModelFactory(GiphyRepository(retrofitService)))
                                    .get(GiphyViewModel::class.java)

        binding.recyclerViewGiphy.adapter = adapter

        viewModel.giphiesList.observe(this, Observer {
            Log.d(TAG, "movieList: $it")
            adapter.setGiphiesList(it)
        })

        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "errorMessage: $it")
        })

        viewModel.getAllGiphies()
    }
}