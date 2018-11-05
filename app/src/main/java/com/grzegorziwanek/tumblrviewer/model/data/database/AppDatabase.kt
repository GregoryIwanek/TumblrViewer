package com.grzegorziwanek.tumblrviewer.model.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.grzegorziwanek.tumblrviewer.model.data.dao.FavouriteDao
import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite

@Database(entities = [ Favourite::class ], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao
}