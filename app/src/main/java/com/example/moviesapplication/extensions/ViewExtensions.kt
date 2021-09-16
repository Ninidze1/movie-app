package com.example.moviesapplication.extensions

import android.view.View
import android.view.animation.TranslateAnimation
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.ismaeldivita.chipnavigation.ChipNavigationBar


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

//fun View.slideDown(duration: Int = 400) {
//    show()
//    val animate = TranslateAnimation(0f, 0f, 0f, this.height.toFloat())
//    animate.duration = duration.toLong()
//    animate.fillAfter = true
//    startAnimation(animate)
//    setGone()
//}
