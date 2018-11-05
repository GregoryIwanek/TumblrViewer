package com.grzegorziwanek.tumblrviewer.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite
import com.grzegorziwanek.tumblrviewer.ui.base.BaseActivity
import com.grzegorziwanek.tumblrviewer.ui.base.BaseMosbyFragment
import com.grzegorziwanek.tumblrviewer.ui.common.BlogAdapter
import com.grzegorziwanek.tumblrviewer.ui.common.BlogViewState
import com.grzegorziwanek.tumblrviewer.ui.main.MainActivity
import com.grzegorziwanek.tumblrviewer.util.ImageLoader
import com.grzegorziwanek.tumblrviewer.util.ScrollTransitionAnimator
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchFragment : BaseMosbyFragment<SearchView, SearchPresenter>(), SearchView {

    @Inject
    lateinit var imageLoader: ImageLoader
    @Inject
    lateinit var presenter: SearchPresenter

    private lateinit var adapter: BlogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setupStates()
    }

    private fun setAdapter() {
        adapter = BlogAdapter(imageLoader)
        rv_suggestion.adapter = adapter
    }

    private fun setupStates() {
        val emptyMessage = listOf(Pair(R.id.tv_empty_title, R.string.no_results),
            Pair(R.id.tv_empty_message, R.string.add_blog_name))
        vsf_states.setProgressView(R.layout.state_progress)
        vsf_states.setEmptyViewWithMessage(R.layout.state_empty, emptyMessage)
        vsf_states.setErrorView(R.layout.state_error)
        vsf_states.showProgress()
    }

    override fun initIntent(): Observable<Unit> =
        Observable.just(Unit)
            .delay(BASE_MOSBY_RX_DELAY, TimeUnit.MILLISECONDS)

    override fun clearSearchIntent(): Observable<Unit> =
        btn_clear.clicks()
            .debounce(BASE_MOSBY_RX_DEBOUNCE, TimeUnit.MILLISECONDS)

    override fun favoriteIntent(): Observable<Favourite> =
        adapter.addFavoriteClicked()

    override fun searchIntent(): Observable<String> =
        RxTextView.editorActionEvents(et_search)
            .filter { it.actionId() == EditorInfo.IME_ACTION_SEARCH }
            .map { et_search.text.toString() }
            .doOnNext { (activity as BaseActivity).hideKeyboard() }

    override fun refreshIntent(): Observable<Unit> =
        Observable.just(Unit)
            .delay(BASE_MOSBY_RX_DELAY, TimeUnit.MILLISECONDS)

    override fun scrollIntent(): Observable<Int> =
        RxRecyclerView.scrollEvents(rv_suggestion)
            .debounce(MIN_MOSBY_RX_DEBOUNCE, TimeUnit.MILLISECONDS)
            .map { t: RecyclerViewScrollEvent -> t.dy() }

    override fun render(state: BlogViewState) {
        when(state) {
            is BlogViewState.ProgressState -> renderProgress()
            is BlogViewState.DataState -> renderData(state)
            is BlogViewState.EmptyState -> renderEmpty()
            is BlogViewState.ErrorState -> renderError()
            is BlogViewState.MessageState -> renderMessage(state)
            is BlogViewState.ScrollState -> renderScroll(state)
            is SearchViewState.EditState -> renderClear()
        }
    }

    private fun renderClear() {
        et_search.text.clear()
        (activity as BaseActivity).hideKeyboard()
    }

    private fun renderMessage(state: BlogViewState.MessageState) {
        Toast.makeText(context, getString(state.resId), Toast.LENGTH_SHORT).show()
    }

    private fun renderScroll(state: BlogViewState.ScrollState) {
        (activity as MainActivity).onChildFragmentScroll(state.deltaY)
        run { if (state.deltaY <= 0) animator.animateShow(abl_search_container)
        else animator.animateHide(ScrollTransitionAnimator.DIRECTION_TOP, abl_search_container) }
    }

    private fun renderProgress() {
        vsf_states.showProgress()
    }

    private fun renderData(state: BlogViewState.DataState) {
        adapter.setData(state.blog.posts)
        rv_suggestion.visibility = View.VISIBLE
        vsf_states.showContent()
    }

    private fun renderEmpty() {
        vsf_states.showEmpty()
    }

    private fun renderError() {
        vsf_states.showError()
    }

    override fun createPresenter(): SearchPresenter = presenter

    companion object {
        @JvmStatic
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }

        val TAG = SearchFragment::class.java.simpleName
    }
}