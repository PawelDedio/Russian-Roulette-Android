package com.dedio.russianroulette

import android.databinding.BindingAdapter
import android.support.constraint.ConstraintLayout
import android.widget.ImageView

@BindingAdapter("layout_constraintCircleRadius")
fun setLayoutConstraintCircleRadius(guideline: ImageView, radius: Int) {
    val params = guideline.getLayoutParams() as ConstraintLayout.LayoutParams
    params.circleRadius = radius
    guideline.setLayoutParams(params)
}

@BindingAdapter("layout_constraintCircleAngle")
fun setLayoutConstraintCircleAngle(guideline: ImageView, angle: Float) {
    val params = guideline.getLayoutParams() as ConstraintLayout.LayoutParams
    params.circleAngle = angle
    guideline.setLayoutParams(params)
}