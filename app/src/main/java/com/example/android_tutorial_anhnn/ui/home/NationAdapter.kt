package com.example.android_tutorial_anhnn.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_tutorial_anhnn.R
import com.example.android_tutorial_anhnn.data.model.AppInfor
import com.example.android_tutorial_anhnn.data.model.National
import com.example.android_tutorial_anhnn.databinding.ItemNationBinding

class NationAdapter : RecyclerView.Adapter<NationAdapter.IconViewHolder>() {

    private val TAG = "NationAdapter"
    var list: MutableList<National> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var iconListener: IconItemListener? = null
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemNationBinding>(
            layoutInflater,
            R.layout.item_nation,
            parent,
            false
        )
        return IconViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ")
        val item = list[position]

//        holder.binding.tvNameNation.text = item.name
//        holder.binding.ivNation.setImageResource(item.flag)


        holder.bind(item)
    }

    override fun getItemCount() = list.size

    fun removeItem(position: Int, item: AppInfor) {
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
        Log.d(TAG, "addItem: ${item.name} $position $itemCount")

    }

    fun addItem(position: Int, item: National) {
        list.add(item)
        notifyItemInserted(position)
        Log.d(TAG, "addItem: ${item.name} $position $itemCount")
    }

    class IconViewHolder(val binding: ItemNationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: National) {
            binding.tvNameNation.text = item.name
            binding.ivNation.setImageResource(item.flag)
        }
    }

    interface IconItemListener {
        fun onItemClick(position: Int, item: National)
    }

}
