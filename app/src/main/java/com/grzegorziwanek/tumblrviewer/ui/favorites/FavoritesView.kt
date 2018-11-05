package com.grzegorziwanek.tumblrviewer.ui.favorites

import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface FavoritesView : MvpView {

    fun initIntent() : Observable<Unit>

    fun deleteIntent() : Observable<Favourite>

    fun scrollIntent() : Observable<Int>

    fun render(state: FavoritesViewState)
}