package com.grzegorziwanek.tumblrviewer.ui.viewholders

import android.view.View
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.model.data.entity.Post
import com.grzegorziwanek.tumblrviewer.util.ImageLoader
import kotlinx.android.synthetic.main.post_list_item.view.*

class PostPhotoVH(itemView: View,
                  private val imageLoader: ImageLoader) : PostVH(itemView) {

    private lateinit var post: Post

    fun bind(post: Post) {
        this.post = post
        itemView.tv_name.text = post.tumbleLogInner.name
        imageLoader.loadImageFromUrlGlide(post.tumbleLogInner.avatarSmall, R.drawable.abc_ab_share_pack_mtrl_alpha, itemView.iv_avatar)
        imageLoader.loadImageFromUrlGlide(post.photo_medium, R.drawable.abc_ab_share_pack_mtrl_alpha, itemView.iv_post_image)

        val tags = post.tags ?: emptyList()
        if (tags.isEmpty()) itemView.tv_tags.visibility = View.GONE
        else itemView.tv_tags.text = tags.joinToString {"#$it " }
    }
}