package com.dedio.russianroulette.views.roulette

import android.widget.ImageView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RouletteViewModel {

    val state: BulletState = BulletState(140, 0f)

    fun initPosition(view: RouletteView, imageView: ImageView) {
        var radius = calculateRadius(view, imageView)
        state.radius = radius

        Observable.interval(3, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    state.angle += 1
                })


    }

    private fun calculateRadius(view: RouletteView, imageView: ImageView): Int {
        var radius = view.width/2 - imageView.width
        return 400
    }
}