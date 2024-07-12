package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.custom.CircleCustomView
import com.example.myapplication.custom.LineCustomView
import com.example.myapplication.databinding.ActivityHomograftBinding
import kotlin.math.log
import kotlin.math.sqrt
import kotlin.random.Random

class HomograftActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomograftBinding
    private var circles = mutableListOf<CircleCustomView>()
    private lateinit var container: FrameLayout
    private var numberHomo = 2
    private var w = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomograftBinding.inflate(layoutInflater)
        setContentView(binding.root)
        w = resources.displayMetrics.widthPixels / 100f

        container = binding.fContainer
        setupView()
        Handler(Looper.getMainLooper()).postDelayed({
            selectRandomHomograft()
        }, 3000)
        setupMenu()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupView() {
        binding.btnback.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.fContainer.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_POINTER_DOWN -> {
                    val pointerIndex = event.actionIndex
                    addCircleViewAtPosition(
                        event.getX(pointerIndex),
                        event.getY(pointerIndex)
                    )
                }

                MotionEvent.ACTION_UP -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        selectRandomHomograft()
                    }, 3000)
                    Handler(Looper.getMainLooper()).postDelayed({
                        circles.clear()
                        container.removeAllViews()
                    }, 1000)
                }

                else -> false
            }
            true
        }
    }

    fun getRandomColor(): String {
        val red = (0..255).random()
        val green = (0..255).random()
        val blue = (0..255).random()

        val redHex = red.toString(16).padStart(2, '0')
        val greenHex = green.toString(16).padStart(2, '0')
        val blueHex = blue.toString(16).padStart(2, '0')

        return "#$redHex$greenHex$blueHex"
    }

    private fun selectRandomHomograft() {
        val listIndex = mutableListOf<Float>()
        val colorCircle1 = getRandomColor()
        val colorCircle2 = getRandomColor()
        var k = 0
        val part1 = circles.subList(0, circles.size / 2)
        val part2 = circles.subList(circles.size / 2, circles.size)

        circles.forEach { item ->
            listIndex.add(item.x)
            listIndex.add(item.y)
        }

        if (part1.size == 2) {
            addLine(listIndex[k], listIndex[k + 1], listIndex[k + 2], listIndex[k + 3], colorCircle1)
            k += 2
        } else if (part1.size == 1) {

        } else {
            part1.forEach {
                if (k > part1.size) {
                    addLine(listIndex[k], listIndex[k + 1], listIndex[0], listIndex[1], colorCircle1)
                    return@forEach
                }
                addLine(listIndex[k], listIndex[k + 1], listIndex[k + 2], listIndex[k + 3], colorCircle1)
                k += 2
            }
        }

        val k1 = k + 2

        if (part2.size == 2) {
            k += 2
            addLine(listIndex[k], listIndex[k + 1], listIndex[k + 2], listIndex[k + 3], colorCircle2)
        } else if (part2.size == 1) {

        } else {
            k += 2
            part2.forEach {
                if (k > part2.size + k1) {
                    addLine(listIndex[k], listIndex[k + 1], listIndex[k1], listIndex[k1 + 1], colorCircle2)
                    return@forEach
                }
                addLine(listIndex[k], listIndex[k + 1], listIndex[k + 2], listIndex[k + 3], colorCircle2)
                k += 2
            }
        }

        part1.forEach { item->
            item.colorCircle = colorCircle1
        }

        part2.forEach { item ->
            item.colorCircle = colorCircle2
        }
    }

 /*   private fun selectRandomHomograft2() {
        circles.shuffle()

        for (index in 0..<circles.size step 2) {
            val v = circles[index]
            val v1 = circles[index + 1]
            if (index < circles.size - 2) addLine(v.x, v.y, v1.x, v1.y)
        }
    }*/

    private fun addCircleViewAtPosition(x: Float, y: Float) {
        val circleView = CircleCustomView(this)
        val size = 34f * w
        val layoutParams = FrameLayout.LayoutParams(size.toInt(), size.toInt())

        layoutParams.topMargin = y.toInt() - size.toInt() / 2
        layoutParams.leftMargin = x.toInt() - size.toInt() / 2

        circleView.layoutParams = layoutParams
        container.addView(circleView)
        circles.add(circleView)
    }

    private fun addLine(startX: Float, startY: Float, endX: Float, endY: Float, color : String) {
        val lineView = LineCustomView(this)
        lineView.positionStartX = startX
        lineView.positionStartY = startY
        lineView.positionEndX = endX
        lineView.positionEndY = endY
        lineView.colorLine = color

        val centerX = startX + (endX - startX) / 2
        val centerY = startY + (endY - startY) / 2

        val layoutParams = FrameLayout.LayoutParams(centerX.toInt() * 2, centerY.toInt() * 2)
        layoutParams.topMargin = 120
        layoutParams.leftMargin = 120
        lineView.layoutParams = layoutParams
        container.addView(lineView)
    }

    private fun setupMenu() {
        binding.btnmenu.setOnClickListener {
            binding.btnmenu.setImageResource(R.drawable.menu)
            val popupMenu = PopupMenu(this@HomograftActivity, it)
            val inflater: MenuInflater = popupMenu.menuInflater
            inflater.inflate(R.menu.menu_homo, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
                when (item?.itemId) {
                    R.id.homo2 -> {
                        binding.btnmenu.setImageResource(R.drawable.number2)
                        numberHomo = 2
                        true
                    }

                    R.id.homo3 -> {
                        binding.btnmenu.setImageResource(R.drawable.number3)
                        numberHomo = 3
                        true
                    }

                    R.id.homo4 -> {
                        binding.btnmenu.setImageResource(R.drawable.number4)
                        numberHomo = 4
                        true
                    }

                    R.id.homo5 -> {
                        binding.btnmenu.setImageResource(R.drawable.number5)
                        numberHomo = 5
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }
    }
}