package com.example.android_tutorial_anhnn.ui.home

import android.util.Log
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

class HomeFragment : BaseFragment<FragmentHomeBinding>(), AppInfoAdapter.IconItemListener,
    DeleteDialogListener, AddDialogListener{

    private val TAG = "HomeFragment"

    private var appAdapter = AppInfoAdapter()

    private var homeAdapter = TestAdapter()

    private lateinit var chooseApp: AppInfor
    private var choosePosition = 0


    private var layoutMode = true

    override fun getLayoutId() = R.layout.fragment_home

    override fun initView() {

        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rclvHome.layoutManager = linearLayoutManager

        val list = mutableListOf<Any>()

//        list.addAll(FakeData.getAppInfoList())
//        list.addAll(FakeData.nationals)

        homeAdapter.resetList(FakeData.nationals)
        homeAdapter.testListener = object: TestAdapter.TestListener{
            override fun onClickItem(position: Int, item: Any) {
                Log.d(TAG, "onClickItem() called with: position = $position, item = $item")
            }
        }

        binding.rclvHome.adapter = homeAdapter

    }

    override fun setAction() {
        binding.tvAdd.setOnClickListener {
            val addDialog = AddDialog()
            addDialog.show(requireActivity().supportFragmentManager.beginTransaction(), "add")
            addDialog.iListener = this
        }

        binding.tvChangeLayout.setOnClickListener {
            if (layoutMode) {
                var layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rclvHome.layoutManager = layoutManager

                layoutMode = false
            } else{
                var layoutManager = StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL)
                binding.rclvHome.layoutManager = layoutManager

                layoutMode = true
            }
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