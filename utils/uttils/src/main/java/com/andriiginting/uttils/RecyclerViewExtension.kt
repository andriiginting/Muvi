package com.andriiginting.uttils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setGridView(numberOfColumns: Int = 2): RecyclerView {
    layoutManager = GridLayoutManager(context, numberOfColumns)
    return this
}