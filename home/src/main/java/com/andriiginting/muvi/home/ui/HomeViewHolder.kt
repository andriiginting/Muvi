package com.andriiginting.muvi.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andriiginting.muvi.home.R
import com.andriiginting.uttils.loadImage
import kotlinx.android.synthetic.main.item_home_component.view.*

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
        itemView.ivPoster.loadImage(url)
    }

    fun setPosterAction(action: (position: Int) -> Unit) {
        itemView.setOnClickListener {
            action.invoke(adapterPosition)
        }
    }
}