package com.grzegorziwanek.tumblrviewer.model.repository.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TumblrService {

    @GET("{name}$READ_JSON")
    fun getTumblrBlog(@Path("name") name: String): Observable<String>

    companion object {
        private const val READ_JSON = "/api/read/json"
    }
}