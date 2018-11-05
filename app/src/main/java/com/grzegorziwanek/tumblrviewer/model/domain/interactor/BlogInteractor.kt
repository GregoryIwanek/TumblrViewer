package com.grzegorziwanek.tumblrviewer.model.domain.interactor

import com.grzegorziwanek.tumblrviewer.ui.common.BlogViewState
import io.reactivex.Observable

interface BlogInteractor {

    fun getBlogByName(name: String): Observable<BlogViewState>

    fun getBlogLastSearch(): Observable<BlogViewState>
}