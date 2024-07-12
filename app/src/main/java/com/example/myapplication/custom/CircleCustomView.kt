package com.example.myapplication.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import kotlin.random.Random

class CircleCustomView : View {
    private var radius = 70f
    private var scaleForCenter = 1f
    private var scaleFactor = 1f
    var colorCircle = "#FF66FF"
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
    }

    constructor(context: Context) : super(context) {

    }

    init {
        startCircleAnimation()
    }

    private fun startCircleAnimation() {
        val animator = ValueAnimator.ofFloat(1f, 0.8f)
        animator.duration = 300
        animator.repeatCount = ValueAnimator.INFINITE  // lặp lại vô hạn
        animator.repeatMode = ValueAnimator.REVERSE
        animator.interpolator = FastOutLinearInInterpolator()
        animator.addUpdateListener {
            scaleFactor = it.animatedValue as Float
            scaleForCenter = it.animatedValue as Float
            invalidate() // vẽ lại
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircle(canvas)
    }

    private fun drawCircle(canvas: Canvas) {
        paint.color = Color.parseColor(colorCircle)
        paint.alpha = (0.2 * 225).toInt()
        canvas.drawCircle(width / 2f, height / 2f, (radius + radius * 0.6f) * scaleFactor, paint)
        paint.alpha = (0.4 * 225).toInt()
        canvas.drawCircle(width / 2f, height / 2f, (radius + radius * 0.4f) * scaleFactor, paint)
        paint.alpha = (0.6 * 225).toInt()
        canvas.drawCircle(width / 2f, height / 2f, (radius + radius * 0.2f) * scaleFactor, paint)
        paint.alpha = (0.8 * 225).toInt()
        canvas.drawCircle(width / 2f, height / 2f, radius * scaleForCenter, paint)
    }
}





