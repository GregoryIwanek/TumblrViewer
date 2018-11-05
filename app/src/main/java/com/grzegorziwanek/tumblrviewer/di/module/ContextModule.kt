package com.grzegorziwanek.tumblrviewer.di.module

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import com.grzegorziwanek.tumblrviewer.model.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val context: Context) {

    @Provides
    @Singleton
    fun context(): Context = context.applicationContext

    @Provides
    @Singleton
    fun preferences(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    internal fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "tumblr_viewer_db").build()

    companion object {
        const val PREF_NAME = "com.grzegorziwanek.tumblrviewer"
    }
}