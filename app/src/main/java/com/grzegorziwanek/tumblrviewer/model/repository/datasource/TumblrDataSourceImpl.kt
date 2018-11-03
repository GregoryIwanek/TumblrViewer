package com.grzegorziwanek.tumblrviewer.model.repository.datasource

import com.grzegorziwanek.tumblrviewer.model.data.entity.Blog
import com.grzegorziwanek.tumblrviewer.model.repository.network.TumblrService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TumblrDataSourceImpl @Inject constructor(private val tumblrService: TumblrService) : TumblrDataSource {

    override fun getBlogByName(name: String): Observable<Blog> =
        tumblrService.getBlogByName(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}