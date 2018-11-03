package com.grzegorziwanek.tumblrviewer.model.domain.interactor

import com.grzegorziwanek.tumblrviewer.model.repository.datasource.TumblrDataSource
import com.grzegorziwanek.tumblrviewer.ui.home.HomveViewState
import io.reactivex.Observable
import javax.inject.Inject

class HomeInteractorImpl @Inject constructor(private val dataSource: TumblrDataSource) : HomeInteractor {

    override fun getBlogByName(name: String): Observable<HomveViewState> =
        dataSource.getBlogByName(name)
            .map<HomveViewState> { HomveViewState.DataState(it) }
            .startWith(HomveViewState.ProgressState(Any()))
            .onErrorReturn { HomveViewState.ErrorState(Any()) }
}