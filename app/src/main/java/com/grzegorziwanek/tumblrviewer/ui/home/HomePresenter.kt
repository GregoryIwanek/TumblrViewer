package com.grzegorziwanek.tumblrviewer.ui.home

import com.grzegorziwanek.tumblrviewer.MockRepository
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.model.domain.interactor.BlogInteractorImpl
import com.grzegorziwanek.tumblrviewer.model.domain.interactor.FavoritesInteractor
import com.grzegorziwanek.tumblrviewer.ui.common.BlogViewState
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class HomePresenter @Inject constructor(private val interactor: BlogInteractorImpl,
                                        private val favoritesInteractor: FavoritesInteractor)
    : MviBasePresenter<HomeView, BlogViewState>() {

    override fun bindIntents() {

        val initIntent: Observable<BlogViewState> =
            intent(HomeView::initIntent)
                .subscribeOn(Schedulers.io())
                .flatMap { favoritesInteractor.getPureFavouriteBlogs() }
                .map { if (it.isEmpty()) MockRepository.mockFavouriteBlogName()
                else it.shuffled(Random(System.currentTimeMillis())).first().name }
                .flatMap { interactor.getBlogByName(it) }

        val favoriteIntent: Observable<BlogViewState> =
            intent(HomeView::favoriteIntent)
                .subscribeOn(Schedulers.io())
                .flatMap { favoritesInteractor.insertFavorite(it) }
                .map<BlogViewState> { BlogViewState.MessageState( R.string.added_to_favorites) }

        val scrollIntent: Observable<BlogViewState> =
            intent(HomeView::scrollIntent)
                .subscribeOn(Schedulers.io())
                .map { BlogViewState.ScrollState(it) }

        val intents: Observable<BlogViewState> =
            Observable.merge(initIntent, scrollIntent, favoriteIntent)
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(intents, HomeView::render)
    }
}