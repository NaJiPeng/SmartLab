package com.njp.smartlab.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.dynamicanimation.animation.SpringForce.*
import com.njp.smartlab.R
import com.njp.smartlab.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private lateinit var imageSplash: ImageView
    private lateinit var tvLogo: TextView
    private lateinit var tvSlogan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        imageSplash = findViewById(R.id.img_splash)
        tvLogo = findViewById(R.id.tv_logo)
        tvSlogan = findViewById(R.id.tv_slogan)

        val animationSplash = SpringAnimation(img_splash, DynamicAnimation.TRANSLATION_Y).apply {
            setStartValue(-150f)
            spring = SpringForce().apply {
                dampingRatio = DAMPING_RATIO_MEDIUM_BOUNCY
                stiffness = STIFFNESS_LOW
            }
        }

        val animationLogo = SpringAnimation(tvLogo, DynamicAnimation.TRANSLATION_Y).apply {
            setStartValue(-150f)
            spring = SpringForce().apply {
                dampingRatio = DAMPING_RATIO_MEDIUM_BOUNCY
                stiffness = STIFFNESS_LOW
            }
        }

        val animationSlogan = SpringAnimation(tv_slogan, DynamicAnimation.TRANSLATION_Y).apply {
            setStartValue(-100f)
            spring = SpringForce().apply {
                dampingRatio = DAMPING_RATIO_HIGH_BOUNCY
                stiffness = STIFFNESS_LOW
            }
        }

        animationSplash.addEndListener { _, _, _, _ ->
            animationSlogan.animateToFinalPosition(0f)
        }

        animationSlogan.addEndListener { _, _, _, _ ->
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        setOf(animationLogo, animationSplash).forEach { it.animateToFinalPosition(0f) }


    }

    override fun onBackPressed() {
    }
}
