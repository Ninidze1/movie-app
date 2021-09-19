package com.example.moviesapplication.extensions

import androidx.appcompat.widget.AppCompatImageView
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