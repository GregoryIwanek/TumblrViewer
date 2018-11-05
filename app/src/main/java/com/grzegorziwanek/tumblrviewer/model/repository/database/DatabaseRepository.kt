package com.grzegorziwanek.tumblrviewer.model.repository.database

import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite
import io.reactivex.Observable

interface DatabaseRepository {

    fun getObjects(): Observable<List<Favourite>>

    fun clearTable(): Observable<Int>

    fun insert(items: List<Favourite>): Observable<List<Favourite>>

    fun delete(items: List<Favourite>):Observable<Int>
}