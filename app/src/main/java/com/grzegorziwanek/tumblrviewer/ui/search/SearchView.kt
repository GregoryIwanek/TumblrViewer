package com.grzegorziwanek.tumblrviewer.ui.search

import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite
import com.grzegorziwanek.tumblrviewer.ui.common.BlogViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface SearchView : MvpView {

    fun initIntent() : Observable<Unit>

    fun refreshIntent() : Observable<Unit>

    fun clearSearchIntent() : Observable<Unit>

    fun favoriteIntent() : Observable<Favourite>

    fun searchIntent() : Observable<String>

    fun scrollIntent() : Observable<Int>

    fun render(state: BlogViewState)
}