package com.fitaleks.heroesthesaurus.ui.widget

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout




/**
 * Created by alex206512252 on 7/27/17.
 */
class ElasticDragDismissFrameLayout : FrameLayout {

    private val dragger: ViewDragHelper

    private val minimumFlingVelocity: Int

    private var callback: Callback? = null

    constructor(context: Context?) : super(context, null, 0, 0)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        dragger = ViewDragHelper.create(this, 1/8f, ViewDragCallback())
        minimumFlingVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity
    }

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragger.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragger.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (dragger.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    interface Callback {

        fun onPullStart()

        fun onPull(progress: Float)

        fun onPullCancel()

        fun onPullComplete()

    }

    private inner class ViewDragCallback : ViewDragHelper.Callback() {

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return 0
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return Math.max(0, top)
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return 0
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return height
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            callback?.onPullStart()
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            callback?.onPull(top.toFloat() / height.toFloat())
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            val slop = if (yvel > minimumFlingVelocity) height / 6 else height / 3
            if (releasedChild.top > slop) {
                callback?.onPullComplete()
            } else {
                callback?.onPullCancel()

                dragger.settleCapturedViewAt(0, 0)
                invalidate()
            }
        }

    }
}