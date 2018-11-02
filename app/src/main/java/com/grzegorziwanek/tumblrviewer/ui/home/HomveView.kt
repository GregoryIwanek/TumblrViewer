package com.grzegorziwanek.tumblrviewer.ui.home

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface HomveView : MvpView {

    fun initIntent() : Observable<Unit>

    fun render(state: HomveViewState)
}