package com.example.moviesapplication.extensions

import android.util.TypedValue
import android.widget.TextView


fun TextView.changeSp(size: Float) {
    setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
}

fun TextView.titleAdjust() {
    when (text.length) {
        in 1..16 -> {changeSp(22f)}
        in 19..27 -> {changeSp(15f)}
        in 28..35 -> {changeSp(11f)}
    }
}
