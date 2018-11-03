package com.grzegorziwanek.tumblrviewer.model.domain.interactor

import com.grzegorziwanek.tumblrviewer.ui.home.HomveViewState
import io.reactivex.Observable

interface HomeInteractor {

    fun getBlogByName(name: String): Observable<HomveViewState>
}