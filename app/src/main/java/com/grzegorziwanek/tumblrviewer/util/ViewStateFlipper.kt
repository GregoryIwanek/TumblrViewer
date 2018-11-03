package com.grzegorziwanek.tumblrviewer.util

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ViewFlipper

/**
 * Class responsible for switching between 4 state views.
 *
 * Progress, content, error, empty states.
 */
class ViewStateFlipper : ViewFlipper {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private lateinit var errorView: View
    private lateinit var emptyView: View
    private lateinit var progressView: View

    private var baseViewsCount = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (baseViewsCount < childCount -1) {
            throw IllegalStateException("ViewStateWrapper can only host one direct child!")
        }
    }

    private fun printChildInfo() =
        Log.e("ViewStateWrapper", "child count: $childCount, base child count: $baseViewsCount")

    fun showContent() =
        if (baseViewsCount < childCount) displayedChild = childCount
        else throw IllegalStateException("ViewStateWrapper can only host one direct child!")

    fun showError() =
        if (this::errorView.isInitialized) displayedChild = indexOfChild(errorView)
        else throw IllegalStateException("ViewStateWrapper error view hasn't been initialized")

    fun showEmpty() =
        if (this::emptyView.isInitialized) displayedChild = indexOfChild(emptyView)
        else throw IllegalStateException("ViewStateWrapper empty view hasn't been initialized")

    fun showProgress() =
        if (this::progressView.isInitialized) displayedChild = indexOfChild(progressView)
        else throw IllegalStateException("ViewStateWrapper progress view hasn't been initialized")

    fun setErrorView(errorView: View) {
        if (!this::errorView.isInitialized) {
            baseViewsCount++
        }
        this.errorView = errorView
        addView(errorView)
    }

    fun setErrorView(layoutResId: Int) {
        if (!this::errorView.isInitialized) {
            baseViewsCount++
        }
        this.errorView = inflater.inflate(layoutResId, this, false)
        addView(errorView)
    }

    fun setEmptyView(emptyView: View) {
        if (!this::emptyView.isInitialized) {
            baseViewsCount++
        }
        this.emptyView = emptyView
        addView(emptyView)
    }

    fun setEmptyView(layoutResId: Int) {
        if (!this::emptyView.isInitialized) {
            baseViewsCount++
        }
        this.emptyView = inflater.inflate(layoutResId, this, false)
        addView(emptyView)
    }

    fun setProgressView(progressView: View) {
        if (!this::progressView.isInitialized) {
            baseViewsCount++
        }
        this.progressView = progressView
        addView(progressView)
    }

    fun setProgressView(layoutResId: Int) {
        if (!this::progressView.isInitialized) {
            baseViewsCount++
        }
        this.progressView = inflater.inflate(layoutResId, this, false)
        addView(progressView)
    }
}