package com.dedio.russianroulette.views.roulette

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.support.constraint.ConstraintLayout
import android.support.constraint.Guideline
import android.widget.ImageView
import com.dedio.russianroulette.BR

data class BulletState(var _radius: Int, var _angle: Float) : BaseObservable() {

    var radius: Int
        @Bindable get() = _radius
        set(value) {
            _radius = value
            notifyPropertyChanged(BR.radius)
        }

    var angle: Float
        @Bindable get() = _angle
        set(value) {
            _angle = value
            notifyPropertyChanged(BR.angle)
        }
}