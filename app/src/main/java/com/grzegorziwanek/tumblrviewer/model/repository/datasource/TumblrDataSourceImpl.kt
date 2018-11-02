package com.grzegorziwanek.tumblrviewer.model.repository.datasource

import com.grzegorziwanek.tumblrviewer.model.repository.network.TumblrService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TumblrDataSourceImpl @Inject constructor(private val tumblrService: TumblrService) : TumblrDataSource {

    override fun getTumblrBlog(name: String): Observable<String> =
        tumblrService.getTumblrBlog(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}