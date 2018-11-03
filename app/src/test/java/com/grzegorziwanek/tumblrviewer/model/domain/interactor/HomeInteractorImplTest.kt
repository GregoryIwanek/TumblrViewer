package com.grzegorziwanek.tumblrviewer.model.domain.interactor

import com.grzegorziwanek.tumblrviewer.MockRepository
import com.grzegorziwanek.tumblrviewer.model.data.entity.Blog
import com.grzegorziwanek.tumblrviewer.model.repository.datasource.TumblrDataSource
import com.grzegorziwanek.tumblrviewer.ui.home.HomveViewState
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@RunWith(MockitoJUnitRunner::class)
class HomeInteractorImplTest {

    @Mock
    lateinit var tumblrDataSource: TumblrDataSource

    private lateinit var interactor: HomeInteractor

    @Before
    fun setup() {
        interactor = HomeInteractorImpl(tumblrDataSource)
    }

    @Test
    fun getBlogByName_success() {
        whenever(tumblrDataSource.getBlogByName(ArgumentMatchers.anyString()))
            .thenReturn(Observable.just(MockRepository.mockBlog()))

        val observer = TestObserver<HomveViewState>()
        interactor.getBlogByName("name").subscribe(observer)
        observer.assertValueAt(0) { it is HomveViewState.ProgressState }
        observer.assertValueAt(1) { it is HomveViewState.DataState }
        observer.assertNoErrors()
        observer.assertComplete()
    }

    @Test
    fun getBlogByName_error() {
        whenever(tumblrDataSource.getBlogByName(ArgumentMatchers.anyString()))
            .thenReturn(Observable.just(Throwable("exception")) as Observable<Blog>)

        val observer = TestObserver<HomveViewState>()
        interactor.getBlogByName("name").subscribe(observer)
        observer.assertValueAt(0) { it is HomveViewState.ProgressState }
        observer.assertValueAt(1) { it is HomveViewState.ErrorState }
        observer.assertComplete()
    }
}