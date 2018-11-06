package com.grzegorziwanek.tumblrviewer.ui.search

import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.model.domain.interactor.BlogInteractor
import com.grzegorziwanek.tumblrviewer.model.domain.interactor.FavoritesInteractor
import com.grzegorziwanek.tumblrviewer.model.domain.interactor.SearchInteractor
import com.grzegorziwanek.tumblrviewer.ui.common.BlogViewState
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchPresenter @Inject constructor(private val blogInteractor: BlogInteractor,
                                          private val searchInteractor: SearchInteractor,
                                          private val favoritesInteractor: FavoritesInteractor)
    : MviBasePresenter<SearchView, BlogViewState>() {

    override fun bindIntents() {

        val initIntent: Observable<BlogViewState> =
            intent(SearchView::initIntent)
                .subscribeOn(Schedulers.io())
                .flatMap { blogInteractor.getBlogLastSearch() }

        val clearIntent: Observable<BlogViewState> =
            intent(SearchView::clearSearchIntent)
                .subscribeOn(Schedulers.io())
                .map { SearchViewState.EditState(true) }

        val favoriteIntent: Observable<BlogViewState> =
            intent(SearchView::addFavoriteIntent)
                .subscribeOn(Schedulers.io())
                .flatMap { favoritesInteractor.insertFavorite(it) }
                .map<BlogViewState> { BlogViewState.MessageState( R.string.added_to_favorites) }

        val searchIntent: Observable<BlogViewState> =
            intent(SearchView::searchIntent)
                .subscribeOn(Schedulers.io())
                .flatMap { blogInteractor.getBlogByName(it) }
                .doOnNext { if (it is BlogViewState.DataState)
                    searchInteractor.storeLastSearch(it.blog.tumblelog.name) }

        val scrollIntent: Observable<BlogViewState> =
            intent(SearchView::scrollIntent)
                .subscribeOn(Schedulers.io())
                .map { BlogViewState.ScrollState(it) }

        val intents: Observable<BlogViewState> =
            Observable.merge(
                Observable.merge(initIntent, searchIntent, clearIntent),
                Observable.merge(favoriteIntent, scrollIntent))
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(intents, SearchView::render)
    }
}