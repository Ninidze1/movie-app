package com.example.common.extensions

import android.os.Handler
import android.os.Looper
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import com.facebook.shimmer.ShimmerFrameLayout

fun ShimmerFrameLayout.blurImg() {
    Handler(Looper.getMainLooper()).postDelayed({
        setGone()
    }, 500)

}

fun AppCompatImageButton.setDrawable(drawable: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(context, drawable))

}