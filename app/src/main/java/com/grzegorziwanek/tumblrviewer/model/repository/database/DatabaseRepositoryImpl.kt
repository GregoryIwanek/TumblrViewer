package com.grzegorziwanek.tumblrviewer.model.repository.database

import com.grzegorziwanek.tumblrviewer.model.data.database.AppDatabase
import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) : DatabaseRepository {

    private fun favouriteDao() = appDatabase.favouriteDao()

    override fun getObjects(): Observable<List<Favourite>> =
        Observable.just(favouriteDao().getObjects())
            .flatMap { it.toObservable() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun clearTable(): Observable<Int> =
        Observable.just(favouriteDao())
            .subscribeOn(Schedulers.io())
            .map { favouriteDao().clearTable() }
            .observeOn(AndroidSchedulers.mainThread())

    override fun insert(items: List<Favourite>): Observable<List<Favourite>> =
        Observable.just(favouriteDao())
            .subscribeOn(Schedulers.io())
            .map { it.insert(items) }
            .observeOn(AndroidSchedulers.mainThread())
            .map { items }

    override fun delete(items: List<Favourite>): Observable<Int> =
        Observable.just(favouriteDao())
            .subscribeOn(Schedulers.io())
            .map { favouriteDao().delete(items) }
            .observeOn(AndroidSchedulers.mainThread())
}
