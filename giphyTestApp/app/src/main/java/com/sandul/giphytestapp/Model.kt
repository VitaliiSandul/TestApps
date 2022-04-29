package com.sandul.giphytestapp

import com.google.gson.annotations.SerializedName

data class GiphyList(
    @SerializedName("data")
    val list: List<GiphyObject>
)

data class GiphyObject(
    @SerializedName("images")
    val images: GiphyImage,
    val title: String
)

data class GiphyImage(
    @SerializedName("original")
    val imageOriginal: ImageOriginal
)

data class ImageOriginal(
    val url: String
)