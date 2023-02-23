package com.example.common.extensions

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.children
import com.bumptech.glide.Glide
import dev.royss.common.R

fun View.show() {
    visibility = View.VISIBLE
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
    startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
    Handler(Looper.getMainLooper()).postDelayed({
        changeAll(resource)
    }, 900)
}


