package com.capstone.golap.ui.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.capstone.golap.MainActivity
import com.capstone.golap.databinding.ActivityWelcomeBinding
import com.capstone.golap.ui.login.LoginActivity
import com.capstone.golap.ui.signup.SignupActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private val preferences: SharedPreferences by lazy {
        getSharedPreferences("user_preferences", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isLoggedIn = preferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.circularImage, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 0f, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 0f, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.titleText, View.ALPHA, 0f, 1f).setDuration(100)
        val desc = ObjectAnimator.ofFloat(binding.subtitleText, View.ALPHA, 0f, 1f).setDuration(100)

        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }
        title.startDelay = 400
        desc.startDelay = 500

        AnimatorSet().apply {
            playSequentially(title, desc, together)
            start()
        }
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
    }
}