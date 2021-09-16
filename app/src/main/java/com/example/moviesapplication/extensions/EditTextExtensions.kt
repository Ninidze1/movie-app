package com.example.moviesapplication.extensions

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import com.example.moviesapplication.R


fun EditText.resetField() {
    doOnTextChanged { text, _, _, _ ->
        if (text != null)
            if (text.length == 1)
                setBackgroundResource(R.drawable.costum_input)
    }
}

fun EditText.setErrorField(errorText: String? = null) {
    setBackgroundResource(R.drawable.costum_error_input)
    if (errorText != null) {
        context.showToast(errorText)
        text.clear()
    }

}