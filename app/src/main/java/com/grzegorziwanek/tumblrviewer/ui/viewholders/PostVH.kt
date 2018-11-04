package com.grzegorziwanek.tumblrviewer.ui.viewholders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class PostVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun getContext(): Context = itemView.context
}