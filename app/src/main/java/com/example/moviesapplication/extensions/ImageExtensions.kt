package com.example.moviesapplication.extensions

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.facebook.shimmer.ShimmerFrameLayout
import jp.wasabeef.blurry.Blurry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun AppCompatImageView.blurImg(context: Context, view: AppCompatImageView, shimmer: ShimmerFrameLayout) {
    CoroutineScope(Dispatchers.Main).launch {
        delay(500)
        shimmer.setGone()
        Blurry.with(context)
            .radius(5)
            .sampling(3)
            .async()
            .animate(500)
            .capture(view)
            .into(view)

    }

}