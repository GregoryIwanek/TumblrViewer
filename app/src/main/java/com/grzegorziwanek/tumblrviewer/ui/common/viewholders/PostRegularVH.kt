package com.grzegorziwanek.tumblrviewer.ui.common.viewholders

import android.view.View
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.model.data.entity.Post
import com.grzegorziwanek.tumblrviewer.ui.base.OnGenericClickListener
import com.grzegorziwanek.tumblrviewer.util.ImageLoader
import kotlinx.android.synthetic.main.regular_list_item.view.*

class PostRegularVH(itemView: View,
                    private val imageLoader: ImageLoader,
                    private val listener: OnGenericClickListener<Post>) : PostVH(itemView) {

    init {
        itemView.btn_favorites.setOnClickListener { if (this::post.isInitialized) listener.onClick(post) }
    }

    private lateinit var post: Post

    override fun bind(post: Post) {
        this.post = post

        itemView.tv_name.text = post.tumbleLogInner.name
        imageLoader.loadImageFromUrlGlide(post.tumbleLogInner.avatarSmall, R.drawable.bg_gradient_grey_yellow, itemView.iv_avatar)
        readFromHtmlInto(post.regularBody, itemView.tv_caption)
        setupTags(post.tags ?: emptyList(), itemView.tv_tags)
    }
}