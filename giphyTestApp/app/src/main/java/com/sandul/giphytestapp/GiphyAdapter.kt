package com.sandul.giphytestapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GiphyAdapter (val context: Context, val listGiphies: List<GiphyObject> ) :
    RecyclerView.Adapter<GiphyAdapter.GiphyViewHolder>() {

    lateinit var giphyListener: OnItemClickListener

    class GiphyViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.titleGiphy)
        val image = itemView.findViewById<ImageView>(R.id.imageGiphySmall)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {
        return GiphyViewHolder(LayoutInflater.from(context).inflate(R.layout.giphy_item_layout, parent, false), giphyListener)
    }

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        val item = listGiphies[position]

        holder.title.text = item.title
        Glide.with(context).load(item.images.imageOriginal.url).into(holder.image)
    }

    override fun getItemCount(): Int {
        return listGiphies.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int){}
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        giphyListener = listener
    }
}