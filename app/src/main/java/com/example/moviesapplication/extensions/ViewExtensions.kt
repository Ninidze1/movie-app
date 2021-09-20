package com.example.moviesapplication.extensions

import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.core.view.children
import com.bumptech.glide.Glide
import com.example.moviesapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}


fun ImageView.loadImg(url: String) {

    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_placeholder)
        .into(this)

}

fun View.showIf(state: Boolean) {
    if (state)
        show()
    else
        setGone()
}

fun ViewGroup.changeByStep(position: Int, resource: Int) {
    getChildAt(position)
        .setBackgroundResource(resource)
}

fun ViewGroup.changeAll(resource: Int) {
    children.forEach { child ->
        child.setBackgroundResource(resource)
    }
}

fun ViewGroup.shake(resource: Int) {
    CoroutineScope(Dispatchers.Main).launch {
        startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
        delay(900)
        changeAll(resource)
    }
}

fun View.slideUp(duration: Int = 750) {

    val animate = TranslateAnimation(0f, 0f, this.height.toFloat(), 0f)
    animate.duration = duration.toLong()
    animate.fillAfter = true
    show()
    startAnimation(animate)
}


