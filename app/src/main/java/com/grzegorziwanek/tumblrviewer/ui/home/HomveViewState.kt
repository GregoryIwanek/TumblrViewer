package com.grzegorziwanek.tumblrviewer.ui.home

sealed class HomveViewState {

    data class ProgressState(val any: Any) : HomveViewState()

    data class DataState(val any: Any) : HomveViewState()

    data class ErrorState(val any: Any) : HomveViewState()
}