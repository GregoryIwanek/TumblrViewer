package com.grzegorziwanek.tumblrviewer.ui.favorites

import com.grzegorziwanek.tumblrviewer.model.domain.interactor.FavoritesInteractor
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoritesPresenter @Inject constructor(private val interactor: FavoritesInteractor)
    : MviBasePresenter<FavoritesView, FavoritesViewState>() {

    override fun bindIntents() {

        val initIntent: Observable<FavoritesViewState> =
            intent(FavoritesView::initIntent)
                .subscribeOn(Schedulers.io())
                .flatMap { interactor.getObjects() }

        val deleteIntent: Observable<FavoritesViewState> =
            intent(FavoritesView::deleteIntent)
                .subscribeOn(Schedulers.io())
                .flatMap { interactor.delete(listOf(it)) }

        val scrollIntent: Observable<FavoritesViewState> =
            intent(FavoritesView::scrollIntent)
                .subscribeOn(Schedulers.io())
                .map { FavoritesViewState.ScrollState(it) }

        val intents: Observable<FavoritesViewState> =
            Observable.merge(initIntent, scrollIntent, deleteIntent)
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(intents, FavoritesView::render)
    }
}