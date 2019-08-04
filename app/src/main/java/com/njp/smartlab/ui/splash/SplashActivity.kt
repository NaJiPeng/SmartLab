package com.njp.smartlab.ui.splash

import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.njp.smartlab.R

class SplashActivity : AppCompatActivity() {

    private lateinit var imageSplash: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        imageSplash = findViewById(R.id.img_splash)
        ObjectAnimator.ofFloat(imageSplash, "alpha", 0f).setDuration(500).start()
    }
}
