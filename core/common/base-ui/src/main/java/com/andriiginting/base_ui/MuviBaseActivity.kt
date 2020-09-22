package com.andriiginting.base_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class MuviBaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupView()
    }

    abstract fun getLayoutId(): Int

    abstract fun setupView()
}