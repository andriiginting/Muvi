package com.andriiginting.uttils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setGridView(numberOfColumns: Int): RecyclerView {
    layoutManager = GridLayoutManager(context, numberOfColumns)
    return this
}