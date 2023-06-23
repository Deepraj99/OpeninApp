package com.example.openinapp.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openinapp.R
import com.example.openinapp.models.TopLink

class TopLinksAdapter(private val context: Context, private val list: List<TopLink>) : RecyclerView.Adapter<TopLinksAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.imageView)!!
        val tvLinkName = view.findViewById<TextView>(R.id.tvLinkName)!!
        val tvDate = view.findViewById<TextView>(R.id.tvDate)!!
        val tvNoOfClicks = view.findViewById<TextView>(R.id.tvNoOfClicks)!!
        val tvUrl = view.findViewById<TextView>(R.id.tvUrl)!!
        val ivCopy = view.findViewById<ImageView>(R.id.ivCopy)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view_links, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(list[position].original_image).into(holder.image)
        holder.tvLinkName.text = list[position].app
        holder.tvDate.text = list[position].created_at
        holder.tvNoOfClicks.text = list[position].total_clicks.toString()
        holder.tvUrl.text = list[position].web_link
        holder.ivCopy.setOnClickListener { (context).copyToClipboard(list[position].web_link) }
    }
    private fun Context.copyToClipboard(text: CharSequence){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label",text)
        clipboard.setPrimaryClip(clip)
    }
}