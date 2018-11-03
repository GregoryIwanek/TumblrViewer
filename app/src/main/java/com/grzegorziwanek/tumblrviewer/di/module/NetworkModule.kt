package com.grzegorziwanek.tumblrviewer.di.module

import android.content.Context
import com.grzegorziwanek.tumblrviewer.di.interceptor.FormatInterceptor
import com.grzegorziwanek.tumblrviewer.di.interceptor.UrlInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class NetworkModule {

    @Provides
    @Singleton
    fun cacheFile(context: Context) : File =
        File(context.cacheDir, "okhttp cache")

    @Provides
    @Singleton
    fun cache(cacheFile: File) : Cache =
        Cache(cacheFile, DISK_CACHE_SIZE)

    @Provides
    @Singleton
    fun okHttpClient(formatInterceptor: FormatInterceptor,
                     urlInterceptor: UrlInterceptor,
                     cache: Cache) : OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(urlInterceptor)
            .addInterceptor(formatInterceptor)
            .cache(cache)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .build()

    companion object {
        private const val DISK_CACHE_SIZE : Long = 20 * 1024 * 1024 // 20MB
    }
}