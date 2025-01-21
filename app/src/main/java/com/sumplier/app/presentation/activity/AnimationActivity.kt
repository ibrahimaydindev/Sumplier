package com.sumplier.app.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.sumplier.app.databinding.ActivityAnimationBinding

class AnimationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationBinding
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler.postDelayed({
            val intent = Intent(this@AnimationActivity, ReceptionActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}