package com.grzegorziwanek.tumblrviewer.ui.common.base

import com.grzegorziwanek.tumblrviewer.util.ScrollTransitionAnimator
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvp.MvpView


/**
 * Generic implementation of Mosby MVI Fragment.
 *
 * Use this class as a base for fragments and views with ViewStates.
 */
abstract class BaseMosbyFragment<V : MvpView, P : MviBasePresenter<V, *>> : MviFragment<V, P>(){

    protected val animator: ScrollTransitionAnimator by lazy { ScrollTransitionAnimator() }

    companion object {
        const val BASE_MOSBY_RX_DELAY : Long = 500
        const val BASE_MOSBY_RX_DEBOUNCE : Long = 750
        const val MIN_MOSBY_RX_DEBOUNCE : Long = 50
    }
}