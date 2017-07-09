package com.fitaleks.heroesthesaurus.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.fitaleks.heroesthesaurus.R

/**
 * Created by Alexander on 08.07.17.
 */
class AspectLockedImageView : ImageView {

    private var aspectRatio = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.AspectLockedImageView)
        aspectRatio = a.getFloat(R.styleable.AspectLockedImageView_imageAspectRatio, 0f)
        a.recycle()
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {

        if (aspectRatio.toDouble() == 0.0) {
            super.onMeasure(widthSpec, heightSpec)
        } else {
            var lockedWidth = View.MeasureSpec.getSize(widthSpec)
            var lockedHeight = View.MeasureSpec.getSize(heightSpec)

            if (lockedWidth == 0 && lockedHeight == 0) {
                throw IllegalArgumentException(
                        "Both width and height cannot be zero -- watch out for scrollable containers")
            }

            // Get the padding of the border background.
            val hPadding = paddingLeft + paddingRight
            val vPadding = paddingTop + paddingBottom

            // Resize the preview frame with correct aspect ratio.
            lockedWidth -= hPadding
            lockedHeight -= vPadding

            if (lockedHeight > 0 && lockedWidth > lockedHeight * aspectRatio) {
                lockedWidth = (lockedHeight * aspectRatio + .5).toInt()
            } else {
                lockedHeight = (lockedWidth / aspectRatio + .5).toInt()
            }

            // Add the padding of the border.
            lockedWidth += hPadding
            lockedHeight += vPadding

            // Ask children to follow the new preview dimension.
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(lockedWidth, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(lockedHeight, View.MeasureSpec.EXACTLY))
        }
    }
}