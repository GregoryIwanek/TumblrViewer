package com.grzegorziwanek.tumblrviewer.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.model.data.entity.Favourite
import com.grzegorziwanek.tumblrviewer.ui.base.BaseMosbyFragment
import com.grzegorziwanek.tumblrviewer.ui.main.MainActivity
import com.grzegorziwanek.tumblrviewer.util.ImageLoader
import com.grzegorziwanek.tumblrviewer.util.ScrollTransitionAnimator
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_favourite.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FavoritesFragment : BaseMosbyFragment<FavoritesView, FavoritesPresenter>(), FavoritesView {

    @Inject
    lateinit var presenter: FavoritesPresenter
    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var adapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setAdapter()
        setupStates()
    }

    private fun setToolbar() {
        toolbar.title = getString(R.string.favorites)
    }

    private fun setAdapter() {
        adapter = FavoritesAdapter(imageLoader)
        rv_favorite.adapter = adapter
    }

    private fun setupStates() {
        val emptyMessage = listOf(Pair(R.id.tv_empty_title, R.string.no_results),
            Pair(R.id.tv_empty_message, R.string.add_favorites_first))
        vsf_states.setProgressView(R.layout.state_progress)
        vsf_states.setEmptyViewWithMessage(R.layout.state_empty, emptyMessage)
        vsf_states.setErrorView(R.layout.state_error)
        vsf_states.showProgress()
    }

    override fun initIntent(): Observable<Unit> =
        Observable.just(Unit)
            .delay(BASE_MOSBY_RX_DELAY, TimeUnit.MILLISECONDS)

    override fun deleteIntent(): Observable<Favourite> =
        adapter.clickedFavorite()

    override fun scrollIntent(): Observable<Int> =
        RxRecyclerView.scrollEvents(rv_favorite)
            .debounce(MIN_MOSBY_RX_DEBOUNCE, TimeUnit.MILLISECONDS)
            .map { t: RecyclerViewScrollEvent -> t.dy() }

    override fun render(state: FavoritesViewState) {
        when(state) {
            is FavoritesViewState.ProgressState -> renderProgress()
            is FavoritesViewState.DataState -> renderData(state)
            is FavoritesViewState.ErrorState -> renderError()
            is FavoritesViewState.ScrollState -> renderScroll(state)
        }
    }

    private fun renderProgress() {
        vsf_states.showProgress()
    }

    private fun renderData(state: FavoritesViewState.DataState) {
        adapter.setData(state.items)
        rv_favorite.visibility = View.VISIBLE
        if (state.items.isEmpty()) vsf_states.showEmpty()
        else vsf_states.showContent()
    }

    private fun renderError() {
        vsf_states.showError()
    }

    private fun renderScroll(state: FavoritesViewState.ScrollState) {
        (activity as MainActivity).onChildFragmentScroll(state.deltaY)
        run { if (state.deltaY <= 0) animator.animateShow(abl_toolbar_container)
        else animator.animateHide(ScrollTransitionAnimator.DIRECTION_TOP, abl_toolbar_container) }
    }

    override fun createPresenter(): FavoritesPresenter = presenter

    companion object {
        @JvmStatic
        fun newInstance(): FavoritesFragment {
            return FavoritesFragment()
        }
    }
}