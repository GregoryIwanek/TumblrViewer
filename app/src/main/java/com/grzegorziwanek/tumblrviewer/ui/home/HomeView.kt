package com.grzegorziwanek.tumblrviewer.ui.home

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface HomeView : MvpView {

    fun initIntent() : Observable<Unit>

    fun refreshIntent() : Observable<Unit>

    fun scrollIntent() : Observable<Int>

    fun render(state: HomveViewState)
}