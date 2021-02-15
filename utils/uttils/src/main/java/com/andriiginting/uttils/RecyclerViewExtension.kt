package com.andriiginting.uttils

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL

fun RecyclerView.setGridView(numberOfColumns: Int = 2): RecyclerView {
    layoutManager = GridLayoutManager(context, numberOfColumns)
    return this
}

fun RecyclerView.setHorizontal(context: Context): RecyclerView {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    return this
}

fun RecyclerView.setVertical(context: Context): RecyclerView {
    layoutManager = LinearLayoutManager(context, VERTICAL, false)
    return this
}