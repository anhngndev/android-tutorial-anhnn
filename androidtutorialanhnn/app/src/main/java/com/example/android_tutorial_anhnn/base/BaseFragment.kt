package com.example.android_tutorial_anhnn.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

    protected lateinit var binding: DB

    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun setAction()

    open fun initBinding() {}

    open fun isCanBackPress() = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isCanBackPress()) {
            setDispatcherOnBackPress()
        }
        initView()
        setAction()
    }

    private fun setDispatcherOnBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
        }
    }

    open fun onBackPress() {
        requireActivity().onBackPressed()
    }

    open fun onFinish(){
    }
}
