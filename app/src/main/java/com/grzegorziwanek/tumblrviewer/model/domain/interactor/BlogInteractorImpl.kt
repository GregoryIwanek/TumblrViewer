package com.grzegorziwanek.tumblrviewer.model.domain.interactor

import com.grzegorziwanek.tumblrviewer.model.repository.datasource.TumblrDataSource
import com.grzegorziwanek.tumblrviewer.model.repository.storage.SearchStorage
import com.grzegorziwanek.tumblrviewer.ui.common.BlogViewState
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BlogInteractorImpl @Inject constructor(private val dataSource: TumblrDataSource,
                                             private val storage: SearchStorage) : BlogInteractor {

    override fun getBlogByName(name: String): Observable<BlogViewState> =
        dataSource.getBlogByName(name)
            .map<BlogViewState> { BlogViewState.DataState(it) }
            .onErrorReturn { BlogViewState.ErrorState(Any()) }
            .startWith(BlogViewState.ProgressState(Any()))

    override fun getBlogLastSearch(): Observable<BlogViewState> =
        Observable.just(storage.getLastSearch())
            .subscribeOn(Schedulers.io())
            .flatMap<BlogViewState> { name ->
                if (name.isNullOrBlank()) Observable.just(BlogViewState.EmptyState(Any()))
                else dataSource.getBlogByName(name).map { blog -> BlogViewState.DataState(blog) }
            }
            .onErrorReturn { BlogViewState.EmptyState(Any()) }
            .startWith(BlogViewState.ProgressState(Any()))
}