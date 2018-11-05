package com.grzegorziwanek.tumblrviewer.util

import android.content.Context
import android.widget.ImageView
import javax.inject.Inject

/**
 * Class responsible for loading images from resources and http with Glide
 */
class ImageLoader @Inject constructor(private val context: Context) {

    fun loadImageFromUrlGlide(url: String?, placeholderId: Int, receiver: ImageView) =
        GlideApp.with(context)
            .load(url)
            .placeholder(placeholderId)
            .into(receiver)
}