package com.grzegorziwanek.tumblrviewer.model.domain.interactor

import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite
import com.grzegorziwanek.tumblrviewer.ui.favorites.FavoritesViewState
import io.reactivex.Observable

interface FavoritesInteractor {

    fun getObjects(): Observable<FavoritesViewState>

    fun getPureFavouriteBlogs(): Observable<List<Favourite>>

    fun delete(items: List<Favourite>): Observable<FavoritesViewState>

    fun clearAll(): Observable<FavoritesViewState>

    fun insertFavorite(favourite: Favourite): Observable<Favourite>
}