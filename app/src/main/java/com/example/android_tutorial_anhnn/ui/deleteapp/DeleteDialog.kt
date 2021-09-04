package com.example.android_tutorial_anhnn.ui.deleteapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.android_tutorial_anhnn.R
import com.example.android_tutorial_anhnn.databinding.DialogNotifyBinding

class DeleteDialog : DialogFragment() {
    lateinit var iListener : DeleteDialogListener
    private lateinit var binding: DialogNotifyBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_notify,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btYes.setOnClickListener {
            iListener.onClickAccept()
            this.onDestroyView()
        }
        binding.btNo.setOnClickListener {
            iListener.onClickReject()
            this.onDestroyView()
        }
    }
}