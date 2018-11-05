package com.grzegorziwanek.tumblrviewer.model.domain.interactor

import android.content.SharedPreferences
import com.grzegorziwanek.tumblrviewer.model.repository.storage.SearchStorage
import com.grzegorziwanek.tumblrviewer.ui.common.BlogViewState
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit
import org.mockito.Mockito.`when` as whenever

@RunWith(MockitoJUnitRunner::class)
class SearchInteractorImplTest {

    @Mock
    private lateinit var preferences: SharedPreferences
    @Mock
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var storage: SearchStorage
    private lateinit var interactor: SearchInteractor

    @Before
    fun setup() {
        storage = SearchStorage(preferences)
        interactor = SearchInteractorImpl(storage)
    }

    @Test
    fun storeLastSearch_success() {
        whenever(preferences.edit())
            .thenReturn(editor)
        whenever(editor.putString(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn(editor)

        val observer = TestObserver<BlogViewState>()
        interactor.storeLastSearch("name").subscribe(observer)
        observer.await(5, TimeUnit.SECONDS)
        observer.assertValueAt(0) { it is BlogViewState.MessageState}
        observer.assertNoErrors()
        observer.assertComplete()
        verify(editor).putString(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())
        verify(editor).apply()
    }
}