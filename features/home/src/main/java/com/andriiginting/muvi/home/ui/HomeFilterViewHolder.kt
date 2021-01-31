package com.andriiginting.muvi.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.andriiginting.muvi.home.R
import kotlinx.android.synthetic.main.item_home_filter_component.view.*

class HomeFilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun inflate(parent: ViewGroup): HomeFilterViewHolder {
            return HomeFilterViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_filter_component, parent, false)
            )
        }
    }

    fun bind(data: HomeFilterData) {
        if (data.isSelected) {
            itemView.tvFilter.apply {
                isSelected = true
                text = data.filter
                setTextColor(
                    getColor(itemView.context, android.R.color.white)
                )
            }
        } else {
            itemView.tvFilter.apply {
                isSelected = false
                text = data.filter
                setTextColor(
                    getColor(itemView.context, android.R.color.black)
                )
            }
        }
    }

    fun onFilterListener(action: (position: Int) -> Unit) {
        itemView.tvFilter.setOnClickListener {
            action(adapterPosition)
        }
    }
}

data class HomeFilterData(
    val filter: String,
    var isSelected: Boolean = false
)