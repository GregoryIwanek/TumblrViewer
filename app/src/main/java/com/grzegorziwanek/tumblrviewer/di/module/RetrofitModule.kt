package com.grzegorziwanek.tumblrviewer.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.grzegorziwanek.tumblrviewer.model.data.database.AppDatabase
import com.grzegorziwanek.tumblrviewer.model.domain.interactor.*
import com.grzegorziwanek.tumblrviewer.model.repository.database.DatabaseRepository
import com.grzegorziwanek.tumblrviewer.model.repository.database.DatabaseRepositoryImpl
import com.grzegorziwanek.tumblrviewer.model.repository.datasource.TumblrDataSource
import com.grzegorziwanek.tumblrviewer.model.repository.datasource.TumblrDataSourceImpl
import com.grzegorziwanek.tumblrviewer.model.repository.network.TumblrService
import com.grzegorziwanek.tumblrviewer.model.repository.storage.SearchStorage
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

    @Provides
    @Singleton
    fun provideDatabaseRepository(appDatabase: AppDatabase): DatabaseRepository =
        DatabaseRepositoryImpl(appDatabase)

    @Provides
    @Singleton
    fun provideTumblrDataSource(tumblrService: TumblrService): TumblrDataSource =
        TumblrDataSourceImpl(tumblrService)

    @Provides
    @Singleton
    fun provideBlogInteractor(tumblrDataSource: TumblrDataSource,
                              storage: SearchStorage): BlogInteractor =
        BlogInteractorImpl(tumblrDataSource, storage)

    @Provides
    @Singleton
    fun provideSearchInteractor(storage: SearchStorage): SearchInteractor =
        SearchInteractorImpl(storage)

    @Provides
    @Singleton
    fun provideFavoriteInteractor(databaseRepository: DatabaseRepository): FavoritesInteractor =
        FavoritesInteractorImpl(databaseRepository)

    companion object {
        const val BASE_API_URL = "https://localhost"
    }
}