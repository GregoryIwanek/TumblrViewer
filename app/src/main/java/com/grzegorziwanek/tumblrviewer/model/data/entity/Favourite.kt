package com.grzegorziwanek.tumblrviewer.model.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Consists of favourite blog entity which can be stored in Room database.
 */
@Entity
data class Favourite(@PrimaryKey
                     val name: String,
                     @ColumnInfo(name = "avatar")
                     val avatar: String)