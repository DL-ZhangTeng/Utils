package com.zhangteng.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhangteng.utils.StateViewHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StateViewHelper().showProgressDialog(this)
    }
}