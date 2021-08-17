package com.example.android_tutorial_anhnn.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.android_tutorial_anhnn.R
import com.example.android_tutorial_anhnn.ShareViewModel
import com.example.android_tutorial_anhnn.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private val shareViewModel : ShareViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment())
            .addToBackStack("home")
            .commit()
    }
}