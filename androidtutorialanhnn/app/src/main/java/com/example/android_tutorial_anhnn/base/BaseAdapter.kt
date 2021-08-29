package com.example.android_tutorial_anhnn.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter : RecyclerView.Adapter<BaseAdapter.BaseVH<Any>>() {


    companion object {
        // trả về khi trường hợp không có layout nào được tìm thấy
        const val INVALID_LAYOUT = -1
    }

    // 1 danh sach có thể nhận vào nhiều kiểu dữ liệu
    var list: MutableList<Any> = mutableListOf()
        private set

    // nhận vào viewtype trả về layout tương ứng
    abstract fun getLayoutId(viewType: Int): Int

    // nhận vào viewtype và binding trả về class con BaseVH
    abstract fun onCreateVH(viewType: Int, binding: ViewDataBinding): BaseVH<*>?

    // nhận vào vị trí ở trong list tra ve doi tuong tuong ung
    abstract fun getDataAtPosition(position: Int): Any


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH<Any> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            getLayoutId(viewType),
            parent,
            false
        )
        return onCreateVH(viewType, binding) as BaseVH<Any>
    }

    override fun onBindViewHolder(holder: BaseVH<Any>, position: Int) {
        val item = getDataAtPosition(holder.adapterPosition)

        holder.bind(item)

    }

    override fun onBindViewHolder(holder: BaseVH<Any>, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)

        val item = getDataAtPosition(holder.adapterPosition)

        holder.bind(item, payloads)
    }

    override fun getItemCount() = list.size

    fun submitList(list: MutableList<Any>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun add(item: Any){
        list.add(item)
        notifyItemInserted(itemCount)
    }

    fun update(item: Any, position:Int){
        list[position] = item
        notifyItemChanged(position)
    }

    fun delete(position: Int){
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    open inner class BaseVH<T>(itemBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        open fun bind(item: T) {

        }

        open fun bind(item: T, payloads: MutableList<Any>) {

        }
    }


}