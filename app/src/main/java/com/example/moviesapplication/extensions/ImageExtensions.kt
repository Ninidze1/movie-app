package com.example.moviesapplication.extensions

import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun AppCompatImageView.blurImg(shimmer: ShimmerFrameLayout) {
    CoroutineScope(Dispatchers.Main).launch {
        delay(500)
        shimmer.setGone()
    }

}

fun AppCompatImageButton.setDrawable(drawable: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(context, drawable))

}