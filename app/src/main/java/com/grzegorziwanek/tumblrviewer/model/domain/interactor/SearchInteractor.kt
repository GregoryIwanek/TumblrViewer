package com.grzegorziwanek.tumblrviewer.model.domain.interactor

import com.grzegorziwanek.tumblrviewer.ui.common.BlogViewState
import io.reactivex.Observable

interface SearchInteractor {

    fun storeLastSearch(query: String) : Observable<BlogViewState>
}