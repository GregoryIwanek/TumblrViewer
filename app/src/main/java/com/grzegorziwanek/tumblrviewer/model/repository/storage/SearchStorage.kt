package com.grzegorziwanek.tumblrviewer.model.repository.storage

import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Consists of storage which main purpose is to keep blog name of the last successful search.
 */
class SearchStorage @Inject constructor(private val preferences: SharedPreferences) {

    fun storeSearch(query: String) {
        val editor = preferences.edit()
        editor.putString(KEY_LAST_SEARCH, query)
        editor.apply()
    }

    fun getLastSearch(): String = preferences.getString(KEY_LAST_SEARCH, "")

    companion object {
        private const val KEY_LAST_SEARCH = "key_last_search"
    }
}