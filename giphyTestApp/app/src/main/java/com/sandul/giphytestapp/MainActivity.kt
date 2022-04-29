package com.sandul.giphytestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    lateinit var recyclerViewGiphy : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewGiphy = findViewById(R.id.recyclerViewGiphy)

        val listGiphy = mutableListOf<GiphyObject>()
        val adapter = GiphyAdapter(this, listGiphy)
        recyclerViewGiphy.adapter = adapter
        recyclerViewGiphy.setHasFixedSize(true)
        recyclerViewGiphy.layoutManager = LinearLayoutManager(this)

        adapter.setOnItemClickListener(object: GiphyAdapter.OnItemClickListener{
            override fun onItemClick(pos: Int) {
                val intent = Intent(this@MainActivity, FullgiphyActivity::class.java).apply {
                    putExtra("title",listGiphy[pos].title)
                    putExtra("imageUrl",listGiphy[pos].images.imageOriginal.url)
                }
                startActivity(intent)

            }
        })

        val service = RetrofitService().getService()
        service?.getGiphyList()?.enqueue(object: Callback<GiphyList> {
            override fun onFailure(call: Call<GiphyList>, t: Throwable) {
                Log.d(TAG, "An error occured")
            }

            override fun onResponse(call: Call<GiphyList>, response: Response<GiphyList>) {
                val body = response.body()
                if (body == null){
                    Log.d(TAG, "No response")
                }
                listGiphy.addAll(body!!.list)
                adapter.notifyDataSetChanged()
            }
        })
    }
}