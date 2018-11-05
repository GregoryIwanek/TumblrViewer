package com.grzegorziwanek.tumblrviewer.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html.ImageGetter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition

/**
 * Class
 */
class HtmlImageReader(internal var mContext: Context, internal val textView: TextView) : ImageGetter {

    private var targets = ArrayList<Target<Bitmap>>()

    override fun getDrawable(url: String): Drawable {
        val urlDrawable = UrlDrawable()
        val load = Glide.with(mContext).asBitmap().load(url)
        val target: Target<Bitmap> = BitmapTarget(urlDrawable)
        targets.add(target)
        load.into(target)
        return urlDrawable
    }

    inner class BitmapTarget(private val urlDrawable: UrlDrawable) : SimpleTarget<Bitmap>() {

        internal lateinit var drawable: Drawable

        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

            drawable = BitmapDrawable(mContext.resources, resource)

            textView.post {
                val w = textView.width
                val hh = drawable.intrinsicHeight
                val ww = drawable.intrinsicWidth
                val newHeight = hh * w / ww
                val rect = Rect(0, 0, w, newHeight)
                drawable.bounds = rect
                urlDrawable.bounds = rect
                urlDrawable.drawable = drawable
                textView.text = textView.text
                textView.invalidate()
            }

        }
    }

    inner class UrlDrawable : BitmapDrawable() {
        var drawable: Drawable? = null
        override fun draw(canvas: Canvas) {
            if (drawable != null)
                drawable!!.draw(canvas)
        }
    }

}