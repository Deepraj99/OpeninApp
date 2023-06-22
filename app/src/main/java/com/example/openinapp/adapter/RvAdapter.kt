package com.example.openinapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.openinapp.R
import com.example.openinapp.models.RvModel

class RvAdapter(private val context: Context, private val list: List<RvModel>) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon = view.findViewById<ImageView>(R.id.ivIcon)!!
        val response = view.findViewById<TextView>(R.id.tvResponse)!!
        val heading = view.findViewById<TextView>(R.id.tvHeading)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.icon.setImageResource(list[position].icon)
        holder.response.text = list[position].response
        holder.heading.text = list[position].desc
    }
}