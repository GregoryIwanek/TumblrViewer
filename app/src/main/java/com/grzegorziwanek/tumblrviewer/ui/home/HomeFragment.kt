package com.grzegorziwanek.tumblrviewer.ui.home

import android.os.Bundle
import com.grzegorziwanek.tumblrviewer.ui.base.BaseMosbyFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeFragment : BaseMosbyFragment<HomveView, HomePresenter>(), HomveView {

    @Inject
    lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }


    override fun initIntent(): Observable<Unit> =
        Observable.just(Unit)
            .delay(BASE_MOSBY_RX_DELAY, TimeUnit.MILLISECONDS)

    override fun render(state: HomveViewState) {

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