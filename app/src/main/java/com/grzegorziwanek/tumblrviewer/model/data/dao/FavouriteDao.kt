package com.grzegorziwanek.tumblrviewer.model.data.dao

import android.arch.persistence.room.*
import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite
import io.reactivex.Maybe

/**
 * Consists of DAO of favourite blog stored in Room database.
 */
@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourite")
    fun getObjects(): Maybe<List<Favourite>>

    @Query("DELETE FROM favourite")
    fun clearTable(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<Favourite>): List<Long>

    @Delete
    fun delete(items: List<Favourite>): Int
}