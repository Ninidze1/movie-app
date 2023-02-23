package com.example.common.extensions

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

fun MutableList<AppCompatImageView>.createConstraint(
    stickTo: View,
    parentRoot: ConstraintLayout,
) {
    var previousStar: AppCompatImageView? = null
    val set = ConstraintSet()
    set.clone(parentRoot)

    for (each in this) {
        previousStar = if (previousStar == null) {
            set.apply {
                connect(each.id, ConstraintSet.LEFT, stickTo.id, ConstraintSet.RIGHT, 15)
                connect(each.id, ConstraintSet.BOTTOM, stickTo.id, ConstraintSet.BOTTOM )
                set.connect(each.id, ConstraintSet.TOP, stickTo.id, ConstraintSet.TOP)
            }
            each
        } else {
            set.connect(each.id, ConstraintSet.LEFT, previousStar.id, ConstraintSet.RIGHT, 0)
            set.connect(each.id, ConstraintSet.TOP, previousStar.id, ConstraintSet.TOP, 0)
            set.connect(each.id, ConstraintSet.BOTTOM, previousStar.id, ConstraintSet.BOTTOM, 0)
            each
        }
    }
    set.applyTo(parentRoot)
}