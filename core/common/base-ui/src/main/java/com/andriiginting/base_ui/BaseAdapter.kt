package com.andriiginting.base_ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class MuviBaseAdapter<T, VH : RecyclerView.ViewHolder>(
    private val onCreateViewHolder: (parent: ViewGroup, viewType: Int) -> VH,
    private val onBindViewHolder: (viewHolder: VH, position: Int, item: T) -> Unit,
    private val onViewType: ((viewType: Int, item: List<T>) -> Int)? = null,
    private val onDetachedFromWindow: ((VH) -> Unit)? = null
) : RecyclerView.Adapter<VH>(), AdapterObserver<T> {

    var items = mutableListOf<T>()
    private var onGetItemViewType: ((position: Int) -> Int)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        onCreateViewHolder.invoke(parent, viewType)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        onBindViewHolder.invoke(holder, position, item)
    }

    override fun getItemViewType(position: Int): Int {
        return if (onViewType != null) {
            onViewType.invoke(position, items)
        } else {
            val onGetItemViewType = onGetItemViewType
            onGetItemViewType?.invoke(position) ?: super.getItemViewType(position)
        }
    }

    override fun addAll(collection: Collection<T>) {
        items.addAll(collection)
        notifyDataSetChanged()
    }

    override fun safeAddAll(collection: Collection<T>?) {
        collection?.let {
            items.addAll(collection)
            notifyDataSetChanged()
        }
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        onDetachedFromWindow?.invoke(holder)
    }

    override fun safeClearAndAddAll(collection: Collection<T>) {
        collection.let {
            clear()
            addAll(collection)
        }
    }

    override fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }

}

interface AdapterObserver<T> {

    fun addAll(collection: Collection<T>)

    fun safeAddAll(collection: Collection<T>?)

    fun safeClearAndAddAll(collection: Collection<T>)

    fun clear()
}