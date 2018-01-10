package com.dedio.russianroulette.views.roulette

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class RouletteViewModelFactory : ViewModelProvider.Factory {

    val animator: RouletteAnimator

    @Inject
    constructor(animator: RouletteAnimator) {
        this.animator = animator
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RouletteViewModel(animator) as T
    }
}