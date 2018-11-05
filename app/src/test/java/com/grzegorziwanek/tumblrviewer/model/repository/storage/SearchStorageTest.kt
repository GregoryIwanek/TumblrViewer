package com.grzegorziwanek.tumblrviewer.model.repository.storage

import android.content.SharedPreferences
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@RunWith(MockitoJUnitRunner::class)
class SearchStorageTest {

    @Mock
    private lateinit var preferences: SharedPreferences
    @Mock
    private lateinit var editor: SharedPreferences.Editor
    @InjectMocks
    private lateinit var storage: SearchStorage

    @Test
    fun storeSearch() {
        val query = "name"
        whenever(preferences.edit())
            .thenReturn(editor)

        storage.storeSearch(query)
        verify(editor).putString(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
        verify(editor).apply()
    }

    @Test
    fun getLastSearch() {
        val query = "name"
        whenever(preferences.edit())
            .thenReturn(editor)
        storage.storeSearch(query)

        whenever(preferences.getString(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn(query)
        storage.getLastSearch()
        verify(preferences).getString(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
    }
}