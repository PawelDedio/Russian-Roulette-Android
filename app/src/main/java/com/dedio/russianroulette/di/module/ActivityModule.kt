package com.dedio.russianroulette.di.module

import com.dedio.russianroulette.base.BaseActivity
import com.dedio.russianroulette.views.roulette.RouletteAnimator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    fun provideRouletteAnimator(): RouletteAnimator {
        return RouletteAnimator()
    }
}