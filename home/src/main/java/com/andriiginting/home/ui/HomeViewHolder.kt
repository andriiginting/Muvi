package com.andriiginting.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andriiginting.home.BuildConfig
import com.andriiginting.home.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_home_component.view.ivPoster

class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun inflate(parent: ViewGroup): HomeViewHolder {
            return HomeViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_component, parent, false)
            )
        }
    }


    fun bind(url: String) {
        Glide.with(itemView.context)
            .load("${BuildConfig.IMAGE_BASE_URL}$url")
            .into(itemView.ivPoster)
    }

    fun setPosterAction(action: (position: Int) -> Unit) {
        itemView.ivPoster.setOnClickListener {
            action.invoke(adapterPosition)
        }
    }
}