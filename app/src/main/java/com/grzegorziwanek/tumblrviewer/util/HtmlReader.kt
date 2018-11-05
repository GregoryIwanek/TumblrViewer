package com.grzegorziwanek.tumblrviewer.util

import android.text.Html
import android.widget.TextView

object HtmlReader {

    fun readHtml(source: String, target: TextView) =
        Html.fromHtml(String.format("<b><p>%s</p></b><br>", source),
            HtmlImageReader(target.context, target), null).trim()
}