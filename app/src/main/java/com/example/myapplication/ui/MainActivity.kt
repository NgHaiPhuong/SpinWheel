package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleEvent()
    }

    private fun handleEvent() {
        binding.itemchoose.setOnClickListener {
            val intent = Intent(this, ChooserActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.itemhomograft.setOnClickListener {
            val intent = Intent(this, HomograftActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}