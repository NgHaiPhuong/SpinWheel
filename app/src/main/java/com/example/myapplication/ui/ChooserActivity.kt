package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.custom.CircleCustomView
import com.example.myapplication.databinding.ActivityChooserBinding
import kotlin.random.Random

class ChooserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooserBinding
    private lateinit var container: FrameLayout
    private val circles = mutableListOf<CircleCustomView>()
    private var numberWin = 1
    private var w = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        w = resources.displayMetrics.widthPixels / 100f

        binding.vUnClick.setOnClickListener {

        }

        container = binding.fragmentContainer
        setupView()
        Handler(Looper.getMainLooper()).postDelayed({
            selectRandomCircleToShow()
        }, 3000)
        setupMenu()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupView() {
        binding.fragmentContainer.setOnTouchListener { view, motionEvent ->
            Log.d("2tdp", "setupView: ${motionEvent.actionMasked}")
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_POINTER_DOWN -> {
                    val pointerIndex = motionEvent.actionIndex
                    addImageViewAtPosition(
                        motionEvent.getX(pointerIndex),
                        motionEvent.getY(pointerIndex)
                    )
                }

                MotionEvent.ACTION_UP -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.vUnClick.visibility = View.VISIBLE
                        selectRandomCircleToShow()
                    }, 3000)
                    circles.clear()
                    container.removeAllViews()
                }

                MotionEvent.ACTION_CANCEL -> {
                    binding.vUnClick.visibility = View.VISIBLE
                }
                else -> false
            }
            true

        }
    }

    fun addImageViewAtPosition(x: Float, y: Float) {
        val circleView = CircleCustomView(this)

        val size = 34f * w
        val layoutParams = FrameLayout.LayoutParams(size.toInt(), size.toInt())

        layoutParams.topMargin = y.toInt()  - size.toInt() / 2
        layoutParams.leftMargin = x.toInt() - size.toInt() / 2

        circleView.layoutParams = layoutParams
        container.addView(circleView)
        circles.add(circleView)
    }

    fun addImageWin(x: Float, y: Float) {
        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.win)

        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )

        layoutParams.leftMargin = x.toInt()
        layoutParams.topMargin = y.toInt()

        imageView.layoutParams = layoutParams
        container.addView(imageView)
    }

    private fun setupMenu() {
        binding.btnback.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnmenu.setOnClickListener {
            binding.btnmenu.setImageResource(R.drawable.menu)
            val popupMenu = PopupMenu(this@ChooserActivity, it)
            val inflater: MenuInflater = popupMenu.menuInflater
            inflater.inflate(R.menu.menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
                when (item?.itemId) {
                    R.id.item1 -> {
                        setupView()
                        binding.btnmenu.setImageResource(R.drawable.number1)
                        numberWin = 1
                        true
                    }

                    R.id.item2 -> {
                        setupView()
                        binding.btnmenu.setImageResource(R.drawable.number2)
                        numberWin = 2
                        true
                    }

                    R.id.item3 -> {
                        setupView()
                        binding.btnmenu.setImageResource(R.drawable.number3)
                        numberWin = 3
                        true
                    }

                    R.id.item4 -> {
                        setupView()
                        binding.btnmenu.setImageResource(R.drawable.number4)
                        numberWin = 4
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    fun selectRandomCircleToShow() {
        if (circles.size > numberWin && circles.size > 1) {
            val selectedIndexes = mutableListOf<Int>()
            while (selectedIndexes.size < numberWin) {
                val randomIndex = Random.nextInt(circles.size)
                if (!selectedIndexes.contains(randomIndex)) {
                    selectedIndexes.add(randomIndex)
                    circles[randomIndex].visibility = View.VISIBLE
                    val circleLocation = calculateCircleLocation(circles[randomIndex])
                    addImageWin(circleLocation.x.toFloat() + 110, circleLocation.y.toFloat() - 230f)
                }
            }

            circles.forEachIndexed { index, circle ->
                if (!selectedIndexes.contains(index))
                    circle.visibility = View.GONE
            }
        }
        binding.vUnClick.visibility = View.GONE

    }

    fun calculateCircleLocation(circle: CircleCustomView): Point {
        val location = IntArray(2)
        circle.getLocationOnScreen(location)
        return Point(location[0], location[1])
    }
}