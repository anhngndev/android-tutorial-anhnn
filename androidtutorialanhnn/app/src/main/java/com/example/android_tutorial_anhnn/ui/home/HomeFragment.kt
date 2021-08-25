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

class HomeFragment : BaseFragment<FragmentHomeBinding>(), AppInforAdapter.IconItemListener,
    DeleteDialogListener, AddDialogListener {

    private var appAdapter = AppInforAdapter()
    private lateinit var chooseApp: AppInfor
    private var choosePosition = 0
    private var layoutMode = true
    private lateinit var currentList: MutableList<AppInfor>

    override fun getLayoutId() = R.layout.fragment_home

    override fun initView() {

        currentList = FakeData.getIcons()
        var staggeredGridLayoutManager = StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL)
        binding.rclvHome.layoutManager = staggeredGridLayoutManager
        appAdapter.list = currentList
        appAdapter.iconListener = this
        appAdapter.context = requireContext()

        binding.rclvHome.adapter = appAdapter

    }

    override fun setAction() {
        binding.tvAdd.setOnClickListener {
            val addDialog = AddDialog()
            addDialog.show(requireActivity().supportFragmentManager.beginTransaction(), "add")
            addDialog.iListener = this
        }

        binding.tvChangeLayout.setOnClickListener {
            var layoutManager = StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL)
            if (layoutMode) {
                layoutManager =
                    StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                layoutMode = false
            } else{
                layoutMode = true
            }
            binding.rclvHome.layoutManager = layoutManager
        }
    }

    override fun onItemClick(position: Int, item: AppInfor) {
        chooseApp = item
        choosePosition = position

        val deleteDialog = DeleteDialog()
        deleteDialog.show(
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_enter_left_to_right,
                    R.anim.slide_exit_right_to_left,
                    R.anim.slide_pop_enter_right_to_left,
                    R.anim.slide_pop_exit_left_to_right
                ), "delete"
        )
        deleteDialog.iListener = this
    }

    override fun onClickAccept() {
        appAdapter.removeItem(choosePosition, chooseApp)
    }

    override fun onClickReject() {

    }

    override fun onClickAcceptAdd(item: AppInfor) {
        appAdapter.addItem(appAdapter.itemCount, item)
    }

    override fun onClickRejectAdd() {

    }
}