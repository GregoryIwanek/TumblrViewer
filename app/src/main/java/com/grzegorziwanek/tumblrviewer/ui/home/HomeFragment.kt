package com.grzegorziwanek.tumblrviewer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.ui.base.BaseMosbyFragment
import com.grzegorziwanek.tumblrviewer.ui.main.MainActivity
import com.grzegorziwanek.tumblrviewer.util.ImageLoader
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeFragment : BaseMosbyFragment<HomeView, HomePresenter>(), HomeView {

    @Inject
    lateinit var imageLoader: ImageLoader
    @Inject
    lateinit var presenter: HomePresenter

    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setupStates()
    }

    private fun setAdapter() {
        adapter = HomeAdapter(imageLoader)
        rv_posts.adapter = adapter
    }

    private fun setupStates() {
        vsf_states.setProgressView(R.layout.state_progress)
        vsf_states.setEmptyView(R.layout.state_empty)
        vsf_states.setErrorView(R.layout.state_error)
        vsf_states.showProgress()
    }

    override fun initIntent(): Observable<Unit> =
        Observable.just(Unit)
            .delay(BASE_MOSBY_RX_DELAY, TimeUnit.MILLISECONDS)

    override fun refreshIntent(): Observable<Unit> =
        Observable.just(Unit)
            .delay(BASE_MOSBY_RX_DELAY, TimeUnit.MILLISECONDS)

    override fun scrollIntent(): Observable<Int> =
        RxRecyclerView.scrollEvents(rv_posts)
            .debounce(MIN_MOSBY_RX_DEBOUNCE, TimeUnit.MILLISECONDS)
            .map { t: RecyclerViewScrollEvent -> t.dy() }

    override fun render(state: HomveViewState) {
        when(state) {
            is HomveViewState.ProgressState -> renderProgress()
            is HomveViewState.DataState -> renderData(state)
            is HomveViewState.ErrorState -> renderError()
            is HomveViewState.ScrollState -> renderScroll(state)
        }
    }

    private fun renderScroll(state: HomveViewState.ScrollState) {
        (activity as MainActivity).onChildFragmentScroll(state.deltaY)
    }

    private fun renderProgress() {
        vsf_states.showProgress()
    }

    private fun renderData(state: HomveViewState.DataState) {
        adapter.setData(state.blog.posts)
        vsf_states.showContent()
    }

    private fun renderError() {
        vsf_states.showError()
    }

    override fun createPresenter(): HomePresenter = presenter

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }

        val TAG = HomeFragment::class.java.simpleName
    }
}