package com.andriiginting.muvi.detail.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andriiginting.muvi.detail.BuildConfig
import com.andriiginting.muvi.detail.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_detail_similar_movie_component.view.*

class MuviDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun inflate(parent: ViewGroup): MuviDetailViewHolder {
            return MuviDetailViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_detail_similar_movie_component, parent, false)
            )
        }
    }

    fun bind(url: String) {
        Glide.with(itemView.context)
            .load("${BuildConfig.IMAGE_BASE_URL}$url")
            .fallback(R.drawable.bg_circle_dark_grey)
            .error(R.drawable.bg_circle_dark_grey)
            .dontAnimate()
            .into(itemView.ivPoster)
    }
}