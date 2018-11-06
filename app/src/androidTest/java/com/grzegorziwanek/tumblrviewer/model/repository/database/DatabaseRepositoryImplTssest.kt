package com.grzegorziwanek.tumblrviewer.model.repository.database

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import com.grzegorziwanek.tumblrviewer.model.data.dao.FavouriteDao
import com.grzegorziwanek.tumblrviewer.model.data.database.AppDatabase
import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class DatabaseRepositoryImplTssest {

    private lateinit var dao: FavouriteDao
    private lateinit var db: AppDatabase
    private lateinit var databaseRepository: DatabaseRepository

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.favouriteDao()
        databaseRepository = DatabaseRepositoryImpl(db)
    }

    @After
    fun closeDb() {
        if (this::db.isInitialized) db.close()
    }

    @Test
    fun getObjects_from_database() {
        val favourite = Favourite("name", "avatar")
        dao.insert(listOf(favourite))
        val favourites = dao.getObjects()
        assert(favourite.name == favourites.blockingGet().first().name)
    }

    @Test
    fun insertObject_into_database() {
        val favourite = Favourite("name", "avatar")
        dao.insert(listOf(favourite))
        var favourites = dao.getObjects()
        assert(favourite.name == favourites.blockingGet().first().name)
        assert(favourite.avatar == favourites.blockingGet().first().avatar)

        //check if replaced without error
        val favouriteTwo = Favourite("name", "something")
        dao.insert(listOf(favouriteTwo))
        favourites = dao.getObjects()
        assert(favourite.name == favourites.blockingGet().first().name)
        assert(favourite.avatar != favourites.blockingGet().first().avatar)
    }

    @Test
    fun deleteObject_from_database() {
        val rand = Random(System.currentTimeMillis())
        val favourites = List(5) {Favourite(rand.nextInt().toString(), "avatar")}
        val deleteItem = favourites[2]

        // make sure contains deletion item
        dao.insert(favourites)
        val items = dao.getObjects()
        assert(items.blockingGet().contains(deleteItem))

        // delete and make sure it doesn't contain anymore
        dao.delete(listOf(deleteItem))
        val newItems = dao.getObjects()
        assert(!newItems.blockingGet().contains(deleteItem))
    }

    @Test
    fun clearTable_in_database() {
        val rand = Random(System.currentTimeMillis())
        val favourites = List(5) {Favourite(rand.nextInt().toString(), "avatar")}

        dao.insert(favourites)
        val items = dao.getObjects()
        assert(items.blockingGet() == favourites)

        // clear table and make sure it doesn't contain it anymore
        dao.clearTable()
        val newItems = dao.getObjects()
        assert(newItems.blockingGet().isEmpty())
    }
}