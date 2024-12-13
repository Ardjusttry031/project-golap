package com.capstone.golap.ui.signup

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.capstone.golap.databinding.ActivitySignupBinding
import com.capstone.golap.ui.ViewModelFactory
import com.capstone.golap.ui.login.LoginActivity
import com.capstone.golap.ui.Result

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupViewModel() {
        signupViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext, "AUTH")
        )[SignupViewModel::class.java]

        signupViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        signupViewModel.signupResult.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    // Handle success, navigate to LoginActivity
                    showToast(result.data.message)
                    navigateToLoginActivity()
                }
                is Result.Error -> {
                    // Handle error and show error message
                    showToast(result.error ?: "An error occurred")
                }
                is Result.Loading -> {
                }
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
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
        binding.signupButton.setOnClickListener {
            validateInputs()
        }
    }

    private fun playAnimation() {
        val views = listOf(
            binding.emailTextView, binding.emailEditTextLayout,
            binding.passwordTextView, binding.passwordEditTextLayout,
            binding.signupButton
        )
        views.forEachIndexed { index, view ->
            ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f).apply {
                duration = 500
                startDelay = (index * 100).toLong()
                start()
            }
        }
    }

    private fun validateInputs() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (!validateEmail(email)) {
            binding.emailEditText.error = "Email is required"
            return
        }
        if (!validatePassword(password)) return

        signupViewModel.register(email, password)
    }

    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.emailEditText.error = "Email is required"
            false
        } else {
            binding.emailEditText.error = null
            true
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.isEmpty()) {
            binding.passwordEditText.error = "Password is required"
            false
        } else if (password.length < 8) {
            binding.passwordEditText.error = "Password must be at least 8 characters"
            false
        } else {
            binding.passwordEditText.error = null
            true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Navigate to LoginActivity after successful registration
    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()  // Optionally finish the SignupActivity to remove it from the back stack
    }
}




