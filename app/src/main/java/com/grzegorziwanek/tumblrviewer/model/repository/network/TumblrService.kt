package com.grzegorziwanek.tumblrviewer.model.repository.network

import io.reactivex.Observable
import retrofit2.http.GET

interface TumblrService {

    @GET("json")
    fun getTumblrTest(): Observable<Any>
}