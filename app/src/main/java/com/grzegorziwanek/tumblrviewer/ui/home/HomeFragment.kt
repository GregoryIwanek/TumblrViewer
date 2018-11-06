package com.grzegorziwanek.tumblrviewer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite
import com.grzegorziwanek.tumblrviewer.ui.common.BlogAdapter
import com.grzegorziwanek.tumblrviewer.ui.common.BlogViewState
import com.grzegorziwanek.tumblrviewer.ui.common.base.BaseMosbyFragment
import com.grzegorziwanek.tumblrviewer.ui.main.MainActivity
import com.grzegorziwanek.tumblrviewer.util.ImageLoader
import com.grzegorziwanek.tumblrviewer.util.ScrollTransitionAnimator.Companion.DIRECTION_TOP
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeFragment : BaseMosbyFragment<HomeView, HomePresenter>(), HomeView {

    @Inject
    lateinit var imageLoader: ImageLoader
    @Inject
    lateinit var presenter: HomePresenter

    private lateinit var adapter: BlogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
         inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setAdapter()
        setupStates()
    }

    private fun setToolbar() {
        toolbar.title = getString(R.string.home)
    }

    private fun setAdapter() {
        adapter = BlogAdapter(imageLoader)
        rv_posts.adapter = adapter
    }

    private fun setupStates() {
        vsf_states.setProgressView(R.layout.state_progress)
        vsf_states.setEmptyView(R.layout.state_empty)
        vsf_states.setErrorView(R.layout.state_error)
        vsf_states.showProgress()
    }

    override fun initIntent(): Observable<Any> =
        Observable.merge(Observable.just(Any())
            .delay(BASE_MOSBY_RX_DELAY, TimeUnit.MILLISECONDS),
            RxSwipeRefreshLayout.refreshes(srl_refresh)
                .map {Any()})

    override fun addFavoriteIntent(): Observable<Favourite> =
        adapter.addFavoriteClicked()

    override fun scrollIntent(): Observable<Int> =
        RxRecyclerView.scrollEvents(rv_posts)
            .debounce(MIN_MOSBY_RX_DEBOUNCE, TimeUnit.MILLISECONDS)
            .map { t: RecyclerViewScrollEvent -> t.dy() }

    override fun render(state: BlogViewState) {
        when(state) {
            is BlogViewState.ProgressState -> renderProgress()
            is BlogViewState.DataState -> renderData(state)
            is BlogViewState.ErrorState -> renderError()
            is BlogViewState.ScrollState -> renderScroll(state)
            is BlogViewState.MessageState -> renderMessage(state)
        }
    }

    private fun renderMessage(state: BlogViewState.MessageState) {
        Toast.makeText(context, getString(state.resId), Toast.LENGTH_SHORT).show()
    }

    private fun renderScroll(state: BlogViewState.ScrollState) {
        (activity as MainActivity).onChildFragmentScroll(state.deltaY)
        run { if (state.deltaY <= 0) animator.animateShow(abl_toolbar_container)
        else animator.animateHide(DIRECTION_TOP, abl_toolbar_container) }
    }

    private fun renderProgress() {
        vsf_states.showProgress()
    }

    private fun renderData(state: BlogViewState.DataState) {
        adapter.setData(state.blog.posts)
        srl_refresh.isRefreshing = false
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
    }
}