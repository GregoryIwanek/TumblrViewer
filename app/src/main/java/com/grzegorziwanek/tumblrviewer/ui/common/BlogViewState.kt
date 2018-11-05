package com.grzegorziwanek.tumblrviewer.ui.common

import com.grzegorziwanek.tumblrviewer.model.data.entity.Blog

open class BlogViewState {

    data class ProgressState(val any: Any) : BlogViewState()

    data class DataState(val blog: Blog) : BlogViewState()

    data class EmptyState(val any: Any) : BlogViewState()

    data class ErrorState(val any: Any) : BlogViewState()

    data class ScrollState(val deltaY: Int) : BlogViewState()

    data class MessageState(val resId: Int) : BlogViewState()
}