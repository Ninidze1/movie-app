package com.example.moviesapplication.extensions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.moviesapplication.R


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

//fun View.slideDown(duration: Int = 400) {
//    show()
//    val animate = TranslateAnimation(0f, 0f, 0f, this.height.toFloat())
//    animate.duration = duration.toLong()
//    animate.fillAfter = true
//    startAnimation(animate)
//    setGone()
//}
