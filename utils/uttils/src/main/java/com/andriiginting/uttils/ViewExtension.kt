package com.andriiginting.uttils

import android.view.View
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


fun LottieAnimationView.loadAnimation(assetPath: String) {
    setAnimation(assetPath)
    playAnimation()
}

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(BuildConfig.IMAGE_BASE_URL+url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .fallback(R.drawable.ic_baseline_image_24)
        .error(R.drawable.ic_baseline_broken_image_24)
        .dontAnimate()
        .into(this)
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}