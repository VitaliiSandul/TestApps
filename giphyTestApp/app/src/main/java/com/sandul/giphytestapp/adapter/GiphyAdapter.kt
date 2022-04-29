package com.sandul.giphytestapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sandul.giphytestapp.GiphyObject
import com.sandul.giphytestapp.databinding.GiphyItemLayoutBinding

class GiphyAdapter(private val onSelect: (GiphyObject?) -> Unit) : RecyclerView.Adapter<GiphyAdapter.GiphyViewHolder>() {

    var giphies = mutableListOf<GiphyObject>()

    fun setGiphiesList(giphies: List<GiphyObject>) {
        this.giphies = giphies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = GiphyItemLayoutBinding.inflate(inflater, parent, false)
        return GiphyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        val item = giphies[position]
        holder.binding.titleGiphy.text = item.title
        Glide.with(holder.itemView.context).load(item.images.imageOriginal.url).into(holder.binding.imageGiphySmall)
        holder.bind(item, onSelect)
    }

    override fun getItemCount(): Int {
        return giphies.size
    }

    class GiphyViewHolder(val binding: GiphyItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GiphyObject?, onSelect: (GiphyObject?) -> Unit) {
            binding.root.setOnClickListener {
                onSelect(item)
            }
        }
    }
}