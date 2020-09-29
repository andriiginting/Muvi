package com.andriiginting.common_widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.andriiginting.uttils.makeGone
import com.andriiginting.uttils.makeVisible
import kotlinx.android.synthetic.main.layout_error.view.*

class MuviErrorScreen @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.layout_error, this)
    }

    fun showErrorScreen() {
        ivErrorScreen.apply {
            makeVisible()
            setAnimation("alien_no_network.json")
        }
        tvErrorDescription.makeVisible()
    }

    fun hideErrorScreen() {
        ivErrorScreen.apply {
            makeGone()
            cancelAnimation()
        }
        tvErrorDescription.makeGone()
    }
}