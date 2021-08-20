package com.example.android_tutorial_anhnn.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_tutorial_anhnn.R
import com.example.android_tutorial_anhnn.data.model.AppInfor
import com.example.android_tutorial_anhnn.databinding.ItemAppInforBinding
import com.squareup.picasso.Picasso

class AppInforAdapter : RecyclerView.Adapter<AppInforAdapter.IconViewHolder>() {

    var list: MutableList<AppInfor> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var iconListener: IconItemListener? = null
    var context: Context?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemAppInforBinding>(
            layoutInflater,
            R.layout.item_app_infor,
            parent,
            false
        )
        return IconViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        val item = list[position]

        holder.binding.tvIcon.text = item.name
        Picasso.with(context).load(item.url).into(holder.binding.cvIcon);

        holder.binding.ctlIcon.setOnClickListener {
            iconListener?.onItemClick(item)
        }
    }

    override fun getItemCount() = list.size

    class IconViewHolder(val binding: ItemAppInforBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    interface IconItemListener {
        fun onItemClick(item: AppInfor)
    }

}