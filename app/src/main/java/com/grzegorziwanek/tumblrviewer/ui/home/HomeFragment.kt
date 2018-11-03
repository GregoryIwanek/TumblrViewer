package com.grzegorziwanek.tumblrviewer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.ui.base.BaseMosbyFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeFragment : BaseMosbyFragment<HomeView, HomePresenter>(), HomeView {

    @Inject
    lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun initIntent(): Observable<Unit> =
        Observable.just(Unit)
            .delay(BASE_MOSBY_RX_DELAY, TimeUnit.MILLISECONDS)

    override fun render(state: HomveViewState) {
        when(state) {
            is HomveViewState.ProgressState -> renderProgress()
            is HomveViewState.DataState -> renderData()
            is HomveViewState.ErrorState -> renderError()
        }
    }

    private fun renderProgress() {

    }

    private fun renderData() {

    }

    private fun renderError() {

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