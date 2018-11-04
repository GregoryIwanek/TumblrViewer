package com.grzegorziwanek.tumblrviewer.util

import android.support.v7.util.DiffUtil

class AdapterDiffUtil<T : Any>(private val oldItems: List<T>,
                               private val newItems: List<T>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition].hashCode() == newItems[newItemPosition].hashCode()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition] == newItems[newItemPosition]

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size
}