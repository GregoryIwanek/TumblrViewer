package com.grzegorziwanek.tumblrviewer.model.domain.interactor

import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.model.repository.storage.SearchStorage
import com.grzegorziwanek.tumblrviewer.ui.common.BlogViewState
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchInteractorImpl @Inject constructor(private val storage: SearchStorage) : SearchInteractor {

    override fun storeLastSearch(query: String): Observable<BlogViewState> =
        Observable.just(storage.storeSearch(query))
            .subscribeOn(Schedulers.io())
            .map<BlogViewState> { BlogViewState.MessageState(R.string.search_saved) }
            .onErrorReturn { BlogViewState.ErrorState(Any()) }
}