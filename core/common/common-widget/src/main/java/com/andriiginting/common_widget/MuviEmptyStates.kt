package com.andriiginting.common_widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.andriiginting.uttils.makeGone
import com.andriiginting.uttils.makeVisible
import kotlinx.android.synthetic.main.layout_error.view.*

class MuviEmptyStates @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.layout_error, this)
    }

    fun showEmptyScreen() {
        ivErrorScreen.apply {
            makeVisible()
            setAnimation("empty_states.json")
            playAnimation()
        }
        tvErrorDescription.apply {
            text = context.resources.getString(R.string.empty_description)
            makeVisible()
        }
    }

    fun hideEmptyScreen() {
        ivErrorScreen.apply {
            makeGone()
            cancelAnimation()
        }
        tvErrorDescription.makeGone()
    }
}