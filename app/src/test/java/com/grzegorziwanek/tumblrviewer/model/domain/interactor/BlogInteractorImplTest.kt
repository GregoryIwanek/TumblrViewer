package com.grzegorziwanek.tumblrviewer.model.domain.interactor

import android.content.SharedPreferences
import com.grzegorziwanek.tumblrviewer.MockRepository
import com.grzegorziwanek.tumblrviewer.model.data.entity.Blog
import com.grzegorziwanek.tumblrviewer.model.repository.datasource.TumblrDataSource
import com.grzegorziwanek.tumblrviewer.model.repository.storage.SearchStorage
import com.grzegorziwanek.tumblrviewer.ui.common.BlogViewState
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit
import org.mockito.Mockito.`when` as whenever

@RunWith(MockitoJUnitRunner::class)
class BlogInteractorImplTest {

    @Mock
    private lateinit var tumblrDataSource: TumblrDataSource
    @Mock
    private lateinit var preferences: SharedPreferences

    private lateinit var storage: SearchStorage
    private lateinit var interactor: BlogInteractor

    @Before
    fun setup() {
        storage = SearchStorage(preferences)
        interactor = BlogInteractorImpl(tumblrDataSource, storage)
    }

    @Test
    fun getBlogByName_endsWithData() {
        whenever(tumblrDataSource.getBlogByName(ArgumentMatchers.anyString()))
            .thenReturn(Observable.just(MockRepository.mockBlog()))

        val observer = TestObserver<BlogViewState>()
        interactor.getBlogByName("name").subscribe(observer)
        observer.assertValueAt(0) { it is BlogViewState.ProgressState }
        observer.assertValueAt(1) { it is BlogViewState.DataState }
        observer.assertNoErrors()
        observer.assertComplete()
    }

    @Test
    fun getBlogByName_endsWithError() {
        whenever(tumblrDataSource.getBlogByName(ArgumentMatchers.anyString()))
            .thenReturn(Observable.just(Throwable("exception")) as Observable<Blog>)

        val observer = TestObserver<BlogViewState>()
        interactor.getBlogByName("name").subscribe(observer)
        observer.assertValueAt(0) { it is BlogViewState.ProgressState }
        observer.assertValueAt(1) { it is BlogViewState.ErrorState }
        observer.assertComplete()
    }

    @Test
    fun getBlogLastSearch_endsWithData() {
        whenever(tumblrDataSource.getBlogByName(ArgumentMatchers.anyString()))
            .thenReturn(Observable.just(MockRepository.mockBlog()))
        whenever(preferences.getString(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn("name")

        val observer = TestObserver<BlogViewState>()
        interactor.getBlogLastSearch().subscribe(observer)
        observer.assertValueAt(0) { it is BlogViewState.ProgressState }
        observer.await(5, TimeUnit.SECONDS)
        observer.assertValueAt(1) { it is BlogViewState.DataState }
        observer.assertValueCount(2)
        observer.assertNoErrors()
        observer.assertComplete()
    }

    @Test
    fun getBlogLastSearch_endsWithEmptyOnError() {
        whenever(tumblrDataSource.getBlogByName(ArgumentMatchers.anyString()))
            .thenReturn(Observable.just(Throwable("exception")) as Observable<Blog>)
        whenever(preferences.getString(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn("name")

        val observer = TestObserver<BlogViewState>()
        interactor.getBlogLastSearch().subscribe(observer)
        observer.assertValueAt(0) { it is BlogViewState.ProgressState }
        observer.await(5, TimeUnit.SECONDS)
        observer.assertValueAt(1) { it is BlogViewState.EmptyState }
        observer.assertValueCount(2)
        observer.assertComplete()

        whenever(preferences.getString(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn("")
        val observerEmpty = TestObserver<BlogViewState>()
        interactor.getBlogLastSearch().subscribe(observerEmpty)
        observerEmpty.assertValueAt(0) { it is BlogViewState.ProgressState }
        observerEmpty.await(5, TimeUnit.SECONDS)
        observerEmpty.assertValueAt(1) { it is BlogViewState.EmptyState }
        observerEmpty.assertValueCount(2)
        observerEmpty.assertComplete()
    }
}