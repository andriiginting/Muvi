package com.andriiginting.search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andriiginting.core_network.MovieItem
import com.andriiginting.search.R
import com.andriiginting.uttils.loadImage
import kotlinx.android.synthetic.main.muvi_item_search_component.view.*

class MuviSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun inflate(parent: ViewGroup): MuviSearchViewHolder {
            return MuviSearchViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.muvi_item_search_component, parent, false)
            )
        }
    }

    fun bind(dataModel: MovieItem) {
        with(itemView) {
            ivSearchBanner.loadImage(dataModel.backdropPath.orEmpty())
            ivSearchPoster.loadImage(dataModel.posterPath.orEmpty())
            tvSearchMovieTitle.text = dataModel.title
            tvSearchMovieDescription.text = dataModel.overview
        }
    }

    fun setCardActionListener(action: () -> Unit) {
        itemView.cardContainer.setOnClickListener {
            action.invoke()
        }
    }
}