package com.example.android_tutorial_anhnn.ui.home

import androidx.databinding.ViewDataBinding
import com.example.android_tutorial_anhnn.R
import com.example.android_tutorial_anhnn.base.BaseAdapter
import com.example.android_tutorial_anhnn.data.model.AppInfor
import com.example.android_tutorial_anhnn.data.model.National
import com.example.android_tutorial_anhnn.databinding.ItemAppInforBinding
import com.example.android_tutorial_anhnn.databinding.ItemNationBinding
import com.squareup.picasso.Picasso

class TestAdapter : BaseAdapter() {

    companion object {
        const val VIEW_TYPE_1 = 1
        const val VIEW_TYPE_2 = 2
    }

    var testListener : TestListener? = null

    override fun getItemViewType(position: Int): Int {
//        return if (position % 2 == 0) {
//            VIEW_TYPE_1
//        } else {
//            VIEW_TYPE_2
//        }
        return if (position < 3) {
            VIEW_TYPE_1
        } else {
            VIEW_TYPE_2
        }

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

    inner class AppInfoVH(itemBinding: ViewDataBinding) : BaseVH<AppInfor>(itemBinding) {

        private val binding = itemBinding as ItemAppInforBinding

        init {
            binding.root.setOnClickListener {
                testListener?.onClickItem(adapterPosition, getDataAtPosition(adapterPosition))
            }
        }

        override fun bind(item: AppInfor) {

            super.bind(item)
            binding.tvTitle.text = item.name
            Picasso.with(binding.root.context).load(item.url).into(binding.cvIcon)

        }


    }

    inner class NationalVH(itemBinding: ViewDataBinding) : BaseVH<National>(itemBinding) {

        private val binding = itemBinding as ItemNationBinding

        init {
            binding.root.setOnClickListener {
                testListener?.onClickItem(adapterPosition, getDataAtPosition(adapterPosition))
            }
        }

        override fun bind(item: National) {
            super.bind(item)
            binding.tvNameNation.text = item.name
            binding.ivNation.setImageResource(item.flag)


        }
    }

    interface TestListener {
        fun onClickItem(position: Int, item: Any)
    }
}
