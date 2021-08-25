package com.example.android_tutorial_anhnn.ui.addapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.android_tutorial_anhnn.R
import com.example.android_tutorial_anhnn.data.model.AppInfor
import com.example.android_tutorial_anhnn.databinding.DialogAddBinding
import com.example.android_tutorial_anhnn.databinding.DialogNotifyBinding
import com.example.android_tutorial_anhnn.ui.deleteapp.DeleteDialogListener

class AddDialog : DialogFragment() {

    lateinit var iListener: AddDialogListener

    private lateinit var binding: DialogAddBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btYes.setOnClickListener {
            var nameInput = binding.edtName.text
            if (nameInput.isNotEmpty()) {
                iListener.onClickAcceptAdd(AppInfor().apply {
                    name = nameInput.toString()
                    url =
                        "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/Icon%2Fworried.png?alt=media&token=0c9467a3-23b5-430f-962e-65aa9a87bf57"
                })
            }
            this.onDestroyView()
        }
        binding.btNo.setOnClickListener {
            iListener.onClickRejectAdd()
            this.onDestroyView()
        }
    }
}