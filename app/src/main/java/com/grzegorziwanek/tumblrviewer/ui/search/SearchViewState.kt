package com.grzegorziwanek.tumblrviewer.ui.search

import com.grzegorziwanek.tumblrviewer.ui.common.BlogViewState

sealed class SearchViewState : BlogViewState() {

    data class EditState(val clear: Boolean) : BlogViewState()
}