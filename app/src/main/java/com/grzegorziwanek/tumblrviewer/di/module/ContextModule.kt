package com.grzegorziwanek.tumblrviewer.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val context: Context) {

    @Provides
    @Singleton
    fun context(): Context = context.applicationContext
}