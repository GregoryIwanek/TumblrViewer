package com.grzegorziwanek.tumblrviewer.ui.common.viewholders

import android.view.View
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.model.data.entity.Post
import com.grzegorziwanek.tumblrviewer.ui.common.base.OnGenericClickListener
import com.grzegorziwanek.tumblrviewer.util.ImageLoader
import kotlinx.android.synthetic.main.question_list_item.view.*

/**
 * ViewHolder for Posts of "type" = answer
 */
class PostQuestionVH(itemView: View,
                     private val imageLoader: ImageLoader,
                     private val listener: OnGenericClickListener<Post>) : PostVH(itemView) {

    init {
        itemView.btn_favorites.setOnClickListener { if (this::post.isInitialized) listener.onClick(post) }
    }

    private lateinit var post: Post

    override fun bind(post: Post) {
        this.post = post

        itemView.tv_name.text = post.tumbleLogInner.name
        imageLoader.loadImageFromUrl(post.tumbleLogInner.avatarSmall, R.drawable.bg_grey_yellow, itemView.iv_avatar)
        readFromHtmlInto(post.regularBody, itemView.tv_caption)
        readFromHtmlInto(post.question, itemView.tv_question)
        readFromHtmlInto(post.answer, itemView.tv_answer)
        setupTags(post.tags ?: emptyList(), itemView.tv_tags)
    }
}