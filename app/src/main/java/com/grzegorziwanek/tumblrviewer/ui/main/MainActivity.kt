package com.grzegorziwanek.tumblrviewer.ui.main

import android.os.Bundle
import android.view.MenuItem
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.ui.common.base.BaseActivity
import com.grzegorziwanek.tumblrviewer.ui.favorites.FavoritesFragment
import com.grzegorziwanek.tumblrviewer.ui.home.HomeFragment
import com.grzegorziwanek.tumblrviewer.ui.search.SearchFragment
import com.grzegorziwanek.tumblrviewer.util.ScrollTransitionAnimator
import com.grzegorziwanek.tumblrviewer.util.ScrollTransitionAnimator.Companion.DIRECTION_BOTTOM
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val animator = ScrollTransitionAnimator()
    private val composite = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) changeFragment(HomeFragment.newInstance())
        setup()
    }

    private fun setup() {
        composite.clear()
        composite += RxBottomNavigationView.itemSelections(bnv_navigation)
            .filter { bnv_navigation.selectedItemId != it.itemId }
            .map { item: MenuItem -> setFragment(item)}
            .subscribe()
    }

    private fun setFragment(item: MenuItem) {
        when(item.itemId) {
            R.id.nav_home -> changeFragment(HomeFragment.newInstance())
            R.id.nav_search -> changeFragment(SearchFragment.newInstance())
            R.id.nav_favorites -> changeFragment(FavoritesFragment.newInstance())
        }
    }

    fun onChildFragmentScroll(deltaY: Int) {
        if (deltaY <= 0) {
            animator.animateShow(bnv_navigation)
        } else {
            animator.animateHide(DIRECTION_BOTTOM, bnv_navigation)
        }
    }

    override fun onDestroy() {
        composite.dispose()
        super.onDestroy()
    }
}