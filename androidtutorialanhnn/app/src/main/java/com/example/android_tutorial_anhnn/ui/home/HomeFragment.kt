package com.example.android_tutorial_anhnn.ui.home

import android.graphics.drawable.GradientDrawable
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.android_tutorial_anhnn.FakeData
import com.example.android_tutorial_anhnn.R
import com.example.android_tutorial_anhnn.base.BaseFragment
import com.example.android_tutorial_anhnn.data.model.Icon
import com.example.android_tutorial_anhnn.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(), IconAdapter.IconItemListener{

    private var iconAdapter= IconAdapter()
    override fun getLayoutId() = R.layout.fragment_home

    override fun initView() {

        var staggeredGridLayoutManager = StaggeredGridLayoutManager(4,LinearLayoutManager.VERTICAL )
        binding.rclvHome.layoutManager = staggeredGridLayoutManager
        iconAdapter.list = FakeData.getIcons()
        iconAdapter.iconListener = this
        iconAdapter.context = requireContext()

        binding.rclvHome.adapter = iconAdapter

    }

    override fun setAction() {
    }

    override fun onItemClick(item: Icon) {
        Toast.makeText(requireContext(),"${item.name} ${item.src}",Toast.LENGTH_LONG).show()
    }
}