package com.andriiginting.muvi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.andriiginting.home.ui.HomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            navigateToHome()
        }, 4000)
    }

    private fun navigateToHome() {
        Intent(this, HomeActivity::class.java)
            .also(::startActivity)
        finish()
    }
}