package com.grzegorziwanek.tumblrviewer.model.repository.datasource

import io.reactivex.Observable

interface TumblrDataSource {

    fun getBlogByName(name: String): Observable<String>
}