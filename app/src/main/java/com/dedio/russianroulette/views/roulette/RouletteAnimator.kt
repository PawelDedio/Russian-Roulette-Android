package com.dedio.russianroulette.views.roulette

import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.animation.FloatValueHolder

class RouletteAnimator {

    private var throwAnim: FlingAnimation? = null

    fun start(holder: FloatValueHolder, velocity: Float, listener: DynamicAnimation.OnAnimationUpdateListener) {
        throwAnim = FlingAnimation(holder)
                .setStartVelocity(velocity)
                .setFriction(0.18f)
                .addUpdateListener(listener)
        throwAnim?.start()
    }

    fun cancel() {
        throwAnim?.cancel()
    }
}