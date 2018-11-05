package com.grzegorziwanek.tumblrviewer.model.repository.datasource

import com.grzegorziwanek.tumblrviewer.model.data.entity.Blog
import io.reactivex.Observable

interface TumblrDataSource {

    fun getBlogByName(name: String): Observable<Blog>
}