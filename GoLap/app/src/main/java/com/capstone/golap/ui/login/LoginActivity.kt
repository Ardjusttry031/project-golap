package com.capstone.golap.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.golap.MainActivity
import com.capstone.golap.databinding.ActivityLoginBinding
import com.capstone.golap.ui.Result
import com.capstone.golap.ui.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this, "AUTH")
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
        setupValidation()

        // Observe login result
        observeLoginResult()
    }

    private fun observeLoginResult() {
                viewModel.loginResult.observe(this) { result ->
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            // Token is now saved in UserPreferences
                            showSuccessDialog("Login success!")
                            // Proceed to main activity after successful login
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        is Result.Error -> {
                            showLoading(false)
                            // Accessing the error message here
                            showErrorDialog(result.error)
                        }
                    }
                }
            }

    private fun playAnimation() {
        val viewsToAnimate = listOf(
            binding.titleTextView,
            binding.messageTextView,
            binding.emailTextView,
            binding.emailEditTextLayout,
            binding.emailEditText,
            binding.passwordTextView,
            binding.passwordEditTextLayout,
            binding.passwordEditText,
            binding.loginButton
        )

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(viewsToAnimate.map { view ->
            ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f).setDuration(300)
        })
        animatorSet.start()
    }
    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // Validasi input
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Logika login melalui ViewModel
            viewModel.login(email, password)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupValidation() {
        binding.emailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateEmail(binding.emailEditText.text.toString())
        }
        binding.passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validatePassword(binding.passwordEditText.text.toString())
        }
    }

    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.emailEditText.error = "Email tidak boleh kosong"
            false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEditText.error = "Format email tidak valid"
            false
        } else {
            binding.emailEditText.error = null
            true
        }
    }

    private fun validatePassword(password: String): Boolean {
        return when {
            password.isEmpty() -> {
                binding.passwordEditText.error = "Password tidak boleh kosong"
                false
            }
            password.length < 8 -> {
                binding.passwordEditText.error = "Password minimal 8 karakter"
                false
            }
            else -> {
                binding.passwordEditText.error = null
                true
            }
        }
    }

    private fun showSuccessDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Success!")
            setMessage(message)
            setPositiveButton("Continue") { _, _ ->
                val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Error")
            setMessage(message)
            setPositiveButton("Try Again") { dialog, _ -> dialog.dismiss() }
            create()
            show()
        }
    }
}