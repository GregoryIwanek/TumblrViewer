package com.grzegorziwanek.tumblrviewer.ui.home

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.model.data.entity.Post
import com.grzegorziwanek.tumblrviewer.ui.viewholders.PostPhotoVH
import com.grzegorziwanek.tumblrviewer.util.AdapterDiffUtil
import com.grzegorziwanek.tumblrviewer.util.ImageLoader

class HomeAdapter(private val imageLoader: ImageLoader) : RecyclerView.Adapter<PostPhotoVH>() {

    private val items = mutableListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PostPhotoVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false)
        return PostPhotoVH(view, imageLoader)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PostPhotoVH, position: Int) {
        holder.bind(items[position])
    }

    fun setData(items: List<Post>) {
        val diffCallback = AdapterDiffUtil(this.items, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }
}