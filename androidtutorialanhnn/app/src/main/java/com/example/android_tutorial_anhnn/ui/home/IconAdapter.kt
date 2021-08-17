package com.example.android_tutorial_anhnn.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_tutorial_anhnn.R
import com.example.android_tutorial_anhnn.data.model.Icon
import com.example.android_tutorial_anhnn.databinding.ItemIconBinding

class IconAdapter : RecyclerView.Adapter<IconAdapter.IconViewHolder>() {

    var list: MutableList<Icon> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var iconListener: IconItemListener? = null


    interface IconItemListener {
        fun onItemClick(item: Icon)
    }

    class IconViewHolder(val binding: ItemIconBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemIconBinding>(
            layoutInflater,
            R.layout.item_icon,
            parent,
            false
        )
        return IconViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        val item = list[position]
        holder.binding.item = item
        holder.binding.listener = iconListener
    }

    override fun getItemCount() = list.size
}