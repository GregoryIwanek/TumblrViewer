package com.grzegorziwanek.tumblrviewer.model.repository.datasource

import io.reactivex.Observable

interface TumblrDataSource {

    fun getTumblrBlog(name: String): Observable<String>
}