package com.capstone.golap.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        // Menambahkan TextWatcher untuk memvalidasi input
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Validasi panjang password
                if (s.toString().length < 8) {
                    error = "Password must be at least 8 characters"
                } else {
                    error = null // Menghilangkan pesan error jika valid
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}