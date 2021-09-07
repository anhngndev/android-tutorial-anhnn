package com.example.android_tutorial_anhnn.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android_tutorial_anhnn.R
import com.example.android_tutorial_anhnn.common.widget.LeftRightTextView

class AccountActivity : AppCompatActivity() {
    private val TAG = "AccountActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

//        val lrtvPolicy = findViewById<LeftRightTextView>(R.id.lrtvAccountPolicy)
//
//        lrtvPolicy.setIconLeft(getDrawable(R.drawable.ic_policy))
//        lrtvPolicy.setTitle("Điều khoản sử dụng")
//        lrtvPolicy.setIconRightOnClick {
//            Log.d(TAG, "onCreate: ")
//        }

    }
}