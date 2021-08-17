package com.example.android_tutorial_anhnn

import android.app.Application
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer



fun <T> Fragment.observer(liveData: LiveData<T>, onChange: (T?) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(onChange))
}

@BindingAdapter("anhnn:visible")
fun TextView.SetVisible(x : Int){
    if (x > 0){
        this.visibility = View.VISIBLE
    } else{
        this.visibility = View.GONE
    }
}