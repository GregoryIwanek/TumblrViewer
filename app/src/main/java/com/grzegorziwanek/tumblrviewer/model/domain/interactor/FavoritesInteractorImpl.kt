package com.grzegorziwanek.tumblrviewer.model.domain.interactor

import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite
import com.grzegorziwanek.tumblrviewer.model.repository.database.DatabaseRepository
import com.grzegorziwanek.tumblrviewer.ui.favorites.FavoritesViewState
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoritesInteractorImpl @Inject constructor(private val database: DatabaseRepository): FavoritesInteractor {

    override fun getObjects(): Observable<FavoritesViewState> =
        database.getObjects()
            .map<FavoritesViewState> { FavoritesViewState.DataState(it) }
            .onErrorReturn { FavoritesViewState.ErrorState(Any()) }

    override fun delete(items: List<Favourite>): Observable<FavoritesViewState> =
        database.delete(items)
            .flatMap { getObjects() }

    override fun clearAll(): Observable<FavoritesViewState> =
        database.clearTable()
            .flatMap { getObjects() }

    override fun getPureFavouriteBlogs(): Observable<List<Favourite>> =
        database.getObjects()
            .onErrorReturn { emptyList() }

    override fun insertFavorite(favourite: Favourite): Observable<Favourite> =
        database.insert(listOf(favourite))
            .subscribeOn(Schedulers.io())
            .map { it.first() }
}