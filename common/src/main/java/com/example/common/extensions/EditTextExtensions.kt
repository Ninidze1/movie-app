package com.example.common.extensions

import android.content.Context
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import dev.royss.common.R


fun EditText.resetField() {
    doOnTextChanged { text, _, _, _ ->
        if (text != null)
            if (text.length == 1)
                setBackgroundResource(R.drawable.costum_input)
    }
}

fun EditText.setErrorField(errorText: String? = null) {
    setBackgroundResource(R.drawable.costum_error_input)
    text.clear()
    if (errorText != null) {
        context.showToast(errorText)
    }

}

fun EditText.setDrawableEnd(context: Context, drawable: Int) {
    setCompoundDrawablesWithIntrinsicBounds(
        null,
        null,
        ContextCompat.getDrawable(context, drawable),
        null
    )
}

fun EditText.removeDrawableEnd() {
    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
}
