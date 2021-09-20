package com.example.moviesapplication.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class NoScrollVerticalLayoutManager(context: Context) : LinearLayoutManager(context, RecyclerView.VERTICAL, false) {

    override fun canScrollHorizontally(): Boolean {
        return false
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        val linearSmoothScroller = ForceHorizontalLinearSmoothScroller(recyclerView.context)
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }
}

class CustomLinearLayoutManager(context: Context?) :
    LinearLayoutManager(context, RecyclerView.VERTICAL, false) {
    override fun canScrollVertically(): Boolean {
        return false
    }
}

class ForceHorizontalLinearSmoothScroller(context: Context) : LinearSmoothScroller(context) {

    override fun calculateDxToMakeVisible(view: android.view.View, snapPreference: Int): Int {
        val layoutManager = layoutManager ?: return 0
        val params = view.layoutParams as RecyclerView.LayoutParams
        val left = layoutManager.getDecoratedLeft(view) - params.leftMargin
        val right = layoutManager.getDecoratedRight(view) + params.rightMargin
        val start = layoutManager.paddingLeft
        val end = layoutManager.width - layoutManager.paddingRight
        return calculateDtToFit(start, end, left, right, snapPreference)
    }
}