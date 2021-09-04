package com.example.android_tutorial_anhnn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareViewModel : ViewModel() {
    var data = MutableLiveData<Int>(0)
}