package com.example.android_tutorial_anhnn.ui.home

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.android_tutorial_anhnn.R
import com.example.android_tutorial_anhnn.base.BaseAdapter
import com.example.android_tutorial_anhnn.data.model.AppInfor
import com.example.android_tutorial_anhnn.data.model.National
import com.example.android_tutorial_anhnn.databinding.ItemAppInforBinding
import com.example.android_tutorial_anhnn.databinding.ItemNationBinding
import com.squareup.picasso.Picasso

class TestAdapter : BaseAdapter() {
    private val TAG = "TestAdapter"

    companion object {
        const val VIEW_TYPE_1 = 1
        const val VIEW_TYPE_2 = 2


        const val PAYLOAD_SELECT = 3
        const val PAYLOAD_UNSELECT = 4
    }

    var testListener: TestListener? = null

    override fun getItemViewType(position: Int): Int {
//        return if (position % 2 == 0) {
//            VIEW_TYPE_1
//        } else {
//            VIEW_TYPE_2
//        }
//        return if (position < 3) {
//            VIEW_TYPE_1
//        } else {
//            VIEW_TYPE_2
//        }

        return VIEW_TYPE_2
    }

    override fun getLayoutId(viewType: Int): Int {
        return when (viewType) {
            VIEW_TYPE_1 -> R.layout.item_app_infor
            VIEW_TYPE_2 -> R.layout.item_nation
            else -> INVALID_LAYOUT
        }
    }

    override fun onCreateVH(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            VIEW_TYPE_1 -> AppInfoVH(binding)
            VIEW_TYPE_2 -> NationalVH(binding)
            else -> null
        }
    }

    override fun getDataAtPosition(position: Int): Any {
        return list[position]
    }

    fun resetList(list: List<National>) {
        val dataList = list.mapIndexed { i, v ->
            NationalVHData(v)
        }
        submitList(dataList.toMutableList())
    }

    private fun getAdapterSelectMode(): SELECT_MODE = SELECT_MODE.MULTIPLE

    inner class AppInfoVH(itemBinding: ViewDataBinding) : BaseVH<AppInfor>(itemBinding) {

        private val binding = itemBinding as ItemAppInforBinding

        init {
            binding.root.setOnClickListener {
                testListener?.onClickItem(adapterPosition, getDataAtPosition(adapterPosition))
                notifyItemChanged(adapterPosition)

            }
        }

        override fun bind(item: AppInfor) {

            super.bind(item)
            binding.tvTitle.text = item.name
            Picasso.with(binding.root.context).load(item.url).into(binding.cvIcon)

        }

    }

    inner class NationalVH(itemBinding: ViewDataBinding) : BaseVH<NationalVHData>(itemBinding) {

        private val binding = itemBinding as ItemNationBinding

        init {
            binding.root.setOnClickListener {

                val item = getDataAtPosition(adapterPosition) as? NationalVHData
                item?.let {
                    select(it)
                }
            }
        }

        override fun bind(item: NationalVHData, payloads: MutableList<Any>) {
            super.bind(item, payloads)
            for (i in payloads) {
                when (i) {
                    PAYLOAD_SELECT -> {
                        binding.tvIsSelected.visibility = View.VISIBLE
                    }
                    PAYLOAD_UNSELECT -> {
                        binding.tvIsSelected.visibility = View.GONE
                    }
                }
            }

        }

        override fun bind(item: NationalVHData) {
            super.bind(item)

            Log.d(TAG, "bind: ")

            binding.tvNameNation.text = item.national.name
            binding.ivNation.setImageResource(item.national.flag)

            if (item.isSelected) {
                binding.tvIsSelected.visibility = View.VISIBLE
            } else {
                binding.tvIsSelected.visibility = View.GONE
            }
        }

        private fun select(item: NationalVHData) {
            val newIndex = adapterPosition

            if (newIndex != -1) {
                if (getAdapterSelectMode() == SELECT_MODE.SINGLE) {
                    if (item.isSelected) {
                        item.isSelected = !item.isSelected
                        notifyItemChanged(newIndex, PAYLOAD_UNSELECT)
                    } else {
                        val oldIndex = getLastItemSelected()
                        if (oldIndex != -1) {
                            (getDataAtPosition(oldIndex) as NationalVHData).isSelected = false
                            notifyItemChanged(oldIndex, PAYLOAD_UNSELECT)
                        }
                        item.isSelected = true
                        notifyItemChanged(newIndex, PAYLOAD_SELECT)
                    }

                } else if (getAdapterSelectMode() == SELECT_MODE.MULTIPLE) {
                    if (item.isSelected) {
                        item.isSelected = !item.isSelected
                        notifyItemChanged(newIndex, PAYLOAD_UNSELECT)
                    } else {
                        item.isSelected = !item.isSelected
                        notifyItemChanged(newIndex, PAYLOAD_SELECT)
                    }

                }
            }

        }

        private fun getLastItemSelected(): Int {
            return list.indexOfFirst {
                (it as NationalVHData).isSelected
            }
        }
    }

    class NationalVHData(val national: National) : ISelectionItem {
        override var isSelected: Boolean = false
    }

    interface TestListener {
        fun onClickItem(position: Int, item: Any)
    }

    interface ISelectionItem {
        var isSelected: Boolean
    }

    enum class SELECT_MODE {
        SINGLE,
        MULTIPLE
    }
}
