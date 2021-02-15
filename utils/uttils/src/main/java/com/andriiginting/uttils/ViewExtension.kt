package com.andriiginting.uttils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable


fun LottieAnimationView.loadAnimation(assetPath: String) {
    setAnimation(assetPath)
    playAnimation()
}

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(BuildConfig.IMAGE_BASE_URL + url)
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

fun EditText.afterTextChanged(listener: (s: String) -> Any): TextWatcher {
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            listener(s?.toString() ?: "")
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
    this.addTextChangedListener(watcher)

    return watcher
}

fun EditText.addTextWatcher(): Flowable<EditTextFlow> {
    return Flowable.create<EditTextFlow>({ emitter ->
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                emitter.onNext(EditTextFlow(p0.toString(), EditTextFlow.Type.AFTER))
            }
        })
    }, BackpressureStrategy.BUFFER)
}

data class EditTextFlow(
    val query: String,
    val type: Type
) {
    enum class Type { AFTER }
}