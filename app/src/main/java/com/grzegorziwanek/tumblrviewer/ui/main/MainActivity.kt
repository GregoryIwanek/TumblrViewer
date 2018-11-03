package com.grzegorziwanek.tumblrviewer.ui.main

import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import com.grzegorziwanek.tumblrviewer.R
import com.grzegorziwanek.tumblrviewer.ui.base.BaseActivity
import com.grzegorziwanek.tumblrviewer.ui.home.HomeFragment
import com.grzegorziwanek.tumblrviewer.util.ScrollTransitionAnimator
import com.grzegorziwanek.tumblrviewer.util.ScrollTransitionAnimator.Companion.DIRECTION_BOTTOM
import com.grzegorziwanek.tumblrviewer.util.ScrollTransitionAnimator.Companion.DIRECTION_TOP
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : BaseActivity() {

    private val animator = ScrollTransitionAnimator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        changeFragment(HomeFragment.newInstance())
        setup()
    }

    private fun setup() {
        setSupportActionBar(toolbar)

        nsv_scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY < oldScrollY) {
                animator.animateShow(bnv_navigation, abl_search_bar)
            } else if (scrollY > oldScrollY) {
                animator.animateHide(DIRECTION_BOTTOM, bnv_navigation)
                animator.animateHide(DIRECTION_TOP, abl_search_bar)
            }
        })

        bnv_navigation.setOnNavigationItemSelectedListener { item ->
            supportActionBar?.title = item.title
            when(item.itemId) {
                R.id.nav_home -> changeFragment(HomeFragment.newInstance())
                R.id.nav_favorites -> changeFragment(HomeFragment.newInstance())
                R.id.nav_profile -> changeFragment(HomeFragment.newInstance())
            }
            true
        }
    }
}