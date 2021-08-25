package com.example.android_tutorial_anhnn.ui.addapp

import com.example.android_tutorial_anhnn.data.model.AppInfor
import com.example.android_tutorial_anhnn.ui.DialogListener

interface AddDialogListener  {
    fun onClickAcceptAdd(item: AppInfor)
    fun onClickRejectAdd()
}