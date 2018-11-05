package com.grzegorziwanek.tumblrviewer.ui.common.viewholders

import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import com.grzegorziwanek.tumblrviewer.model.data.entity.Post
import com.grzegorziwanek.tumblrviewer.util.HtmlReader

abstract class PostVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(post: Post)

    protected fun readFromHtmlInto(source: String?, target: TextView) {
        if (!source.isNullOrBlank()) {
            target.visibility = View.VISIBLE
            target.movementMethod = LinkMovementMethod.getInstance()
            target.text =  HtmlReader.readHtml(source!!.trim(), target)
        } else target.visibility = View.GONE
    }

    protected fun setupTags(tags: List<String>, target: TextView) {
        if (tags.isEmpty()) {
            target.visibility = View.GONE
        } else {
            target.visibility = View.VISIBLE
            target.text = tags.joinToString {"#$it " }
        }
    }
}