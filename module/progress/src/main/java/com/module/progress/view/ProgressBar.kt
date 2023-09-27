package com.module.progress.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val TAG = "ProgressBar"
        const val BGColor = 0xffEAEAEA.toInt()
        const val ProgressBarColor = 0xff0000ff.toInt()
    }

    private var max: Int = 100
    private var currentPosition: Int = 0
        set(value) {
            field = value
            invalidate()
        }
    private var paint = Paint()
    private var progressColor: Int? = 0

    init {
        paint.color = BGColor
        paint.strokeWidth = 4f
        progressColor = ProgressBarColor
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (progressColor == null) {
            progressColor = ProgressBarColor
        }
        canvas?.let { c ->
            c.save()
            val percent = currentPosition.toFloat() / max.toFloat()
            val currentWidth = width.toFloat() * percent
            // 画底色
            paint.color = BGColor
            c.drawLine(currentWidth, 0f, width.toFloat(), 0f, paint)
            // 画进度色
            progressColor?.let { progressColor ->
                paint.color = progressColor
            }
            c.drawLine(0f, 0f, currentWidth, 0f, paint)
            c.restore()
        }
    }
}