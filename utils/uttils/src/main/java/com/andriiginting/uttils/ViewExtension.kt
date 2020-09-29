package com.andriiginting.uttils

import android.view.View
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide


fun LottieAnimationView.loadAnimation(assetPath: String) {
    setAnimation(assetPath)
    playAnimation()
}

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(BuildConfig.IMAGE_BASE_URL+url)
        .centerCrop()
        .into(this)
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}