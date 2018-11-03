package com.grzegorziwanek.tumblrviewer.ui.home

import com.grzegorziwanek.tumblrviewer.model.domain.interactor.HomeInteractorImpl
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePresenter @Inject constructor(private val interactor: HomeInteractorImpl)
    : MviBasePresenter<HomeView, HomveViewState>() {

    override fun bindIntents() {

        val initIntent: Observable<HomveViewState> =
            intent(HomeView::initIntent)
                .subscribeOn(Schedulers.io())
                .flatMap { interactor.getBlogByName("derek") }
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(initIntent, HomeView::render)
    }
}