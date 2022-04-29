package com.sandul.giphytestapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sandul.giphytestapp.R

class FullgiphyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullgiphy)

        val title = findViewById<TextView>(R.id.titleGiphyBig)
        val image = findViewById<ImageView>(R.id.imageGiphyBig)

        title.text = intent.getStringExtra("title")
        val imageUrl = intent.getStringExtra("imageUrl")

        Glide.with(this).load(imageUrl).into(image)
    }
}