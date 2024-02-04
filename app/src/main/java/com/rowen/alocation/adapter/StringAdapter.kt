package com.rowen.alocation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rowen.alocation.R
import com.rowen.alocation.adapter.StringAdapter.VH

class StringAdapter(private val data: List<String>?) : RecyclerView.Adapter<VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return VH(inflate)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.item_tv.text = data!![position]
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_tv: TextView

        init {
            item_tv = itemView.findViewById(R.id.item_tv)
        }
    }
}