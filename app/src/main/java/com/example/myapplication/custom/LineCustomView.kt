package com.example.myapplication.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.myapplication.ui.HomograftActivity
import kotlin.random.Random

class LineCustomView : View {
    var positionStartX = 0f
    var positionStartY = 0f
    var positionEndX = 0f
    var positionEndY = 0f
    var colorLine: String = "#FF66FF"
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
    }

    constructor(context: Context) : super(context) {

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        lineDraw(canvas)
    }

    private fun lineDraw(canvas: Canvas) {
        paint.color = Color.parseColor(colorLine)
        paint.strokeWidth = 10f
        paint.alpha = 255
        canvas.drawLine(positionStartX, positionStartY, positionEndX, positionEndY, paint)
        paint.alpha = 255
        canvas.drawCircle(positionStartX, positionStartY, 15f, paint)
        canvas.drawCircle(positionEndX, positionEndY, 15f, paint)
    }
}





