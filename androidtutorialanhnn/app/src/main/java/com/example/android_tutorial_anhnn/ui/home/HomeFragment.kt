package com.example.android_tutorial_anhnn.ui.home

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.android_tutorial_anhnn.FakeData
import com.example.android_tutorial_anhnn.R
import com.example.android_tutorial_anhnn.base.BaseFragment
import com.example.android_tutorial_anhnn.data.model.AppInfor
import com.example.android_tutorial_anhnn.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(), AppInforAdapter.IconItemListener{

    private var iconAdapter= AppInforAdapter()
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

    override fun onItemClick(item: AppInfor) {
        Toast.makeText(requireContext(),"${item.name} ${item.url}",Toast.LENGTH_LONG).show()
    }
}