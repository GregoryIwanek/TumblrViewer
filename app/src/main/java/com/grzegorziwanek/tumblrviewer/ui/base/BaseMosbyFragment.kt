package com.grzegorziwanek.tumblrviewer.ui.base

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.hannesdorfmann.mosby3.mvp.MvpView


/**
 * Generic implementation of Mosby MVI Fragment.
 *
 * Use this class as a base for fragments and views with ViewStates.
 */
abstract class BaseMosbyFragment<V : MvpView, P : MviBasePresenter<V, *>> : MviFragment<V, P>(){

    companion object {
        val BASE_MOSBY_RX_DELAY : Long = 500
        val BASE_MOSBY_RX_DEBOUNCE : Long = 750
    }
}