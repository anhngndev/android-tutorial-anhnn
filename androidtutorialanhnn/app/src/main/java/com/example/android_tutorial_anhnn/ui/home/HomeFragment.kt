package com.example.android_tutorial_anhnn.ui.home

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.android_tutorial_anhnn.FakeData
import com.example.android_tutorial_anhnn.R
import com.example.android_tutorial_anhnn.base.BaseFragment
import com.example.android_tutorial_anhnn.data.model.AppInfor
import com.example.android_tutorial_anhnn.databinding.FragmentHomeBinding
import com.example.android_tutorial_anhnn.ui.addapp.AddDialog
import com.example.android_tutorial_anhnn.ui.addapp.AddDialogListener
import com.example.android_tutorial_anhnn.ui.deleteapp.DeleteDialogListener
import com.example.android_tutorial_anhnn.ui.deleteapp.DeleteDialog

class HomeFragment : BaseFragment<FragmentHomeBinding>(), AppInforAdapter.IconItemListener
    , DeleteDialogListener
    ,AddDialogListener{

    private var iconAdapter= AppInforAdapter()
    private lateinit var chooseApp : AppInfor
    private lateinit var currentList: MutableList<AppInfor>

    override fun getLayoutId() = R.layout.fragment_home

    override fun initView() {

        currentList = FakeData.getIcons()
        var staggeredGridLayoutManager = StaggeredGridLayoutManager(4,LinearLayoutManager.VERTICAL )
        binding.rclvHome.layoutManager = staggeredGridLayoutManager
        iconAdapter.list = currentList
        iconAdapter.iconListener = this
        iconAdapter.context = requireContext()

        binding.rclvHome.adapter = iconAdapter

    }

    override fun setAction() {
        binding.tvAdd.setOnClickListener {
            val addDialog= AddDialog()
            addDialog.show(requireActivity().supportFragmentManager.beginTransaction(), "add")
            addDialog.iListener = this
        }
    }

    override fun onItemClick(item: AppInfor) {

        chooseApp = item

//      can use bundle
        val deleteDialog= DeleteDialog()
        deleteDialog.show(requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_enter_left_to_right
            ,R.anim.slide_exit_right_to_left
            ,R.anim.slide_pop_enter_right_to_left
            ,R.anim.slide_pop_exit_left_to_right), "delete")
        deleteDialog.iListener = this
    }

    override fun onClickAccept() {
        currentList.remove(chooseApp)
        iconAdapter.list = currentList
    }

    override fun onClickReject() {

    }

    override fun onClickAcceptAdd(item: AppInfor) {
        currentList.add(item)
        iconAdapter.list = currentList
    }

    override fun onClickRejectAdd() {

    }
}