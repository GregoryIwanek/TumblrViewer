package com.grzegorziwanek.tumblrviewer.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.grzegorziwanek.tumblrviewer.model.repository.network.TumblrService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class RetrofitModule {

    @Provides
    @Singleton
    fun gson(): Gson =
        GsonBuilder()
            .serializeNulls()
            .create()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson) : Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideTumblrService(retrofit: Retrofit) : TumblrService =
        retrofit.create(TumblrService::class.java)

    companion object {
        const val API_VERSION_1 = "v1/"
        const val BASE_API_URL = "https://"
    }
}