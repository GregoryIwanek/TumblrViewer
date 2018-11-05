package com.grzegorziwanek.tumblrviewer.ui.favorites

import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite

sealed class FavoritesViewState {

    data class ProgressState(val any: Any): FavoritesViewState()

    data class DataState(val items: List<Favourite>): FavoritesViewState()

    data class ErrorState(val any: Any): FavoritesViewState()

    data class ScrollState(val deltaY: Int) : FavoritesViewState()
}