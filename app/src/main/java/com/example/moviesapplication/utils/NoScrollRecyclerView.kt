package com.example.moviesapplication.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class NoScrollRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun onInterceptTouchEvent(e : MotionEvent) : Boolean {
        if (layoutManager?.isSmoothScrolling == true || scrollState == SCROLL_STATE_SETTLING) {
            return true
        }
        return super.onInterceptTouchEvent(e)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e :MotionEvent) : Boolean {
        if (layoutManager?.isSmoothScrolling == true || scrollState == SCROLL_STATE_SETTLING) {
            return true
        }
        return super.onTouchEvent(e)
    }
}