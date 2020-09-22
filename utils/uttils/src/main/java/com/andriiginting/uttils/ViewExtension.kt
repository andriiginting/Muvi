package com.andriiginting.uttils

import android.view.View
import com.airbnb.lottie.LottieAnimationView


fun LottieAnimationView.loadAnimation(assetPath: String) {
    setAnimation(assetPath)
    playAnimation()
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}