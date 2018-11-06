package com.grzegorziwanek.tumblrviewer.ui.home

import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite
import com.grzegorziwanek.tumblrviewer.ui.common.BlogViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface HomeView : MvpView {

    fun initIntent() : Observable<Any>

    fun addFavoriteIntent() : Observable<Favourite>

    fun scrollIntent() : Observable<Int>

    fun render(state: BlogViewState)
}