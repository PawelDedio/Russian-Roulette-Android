package com.dedio.russianroulette.di.components

import com.dedio.russianroulette.main.MainActivity
import com.dedio.russianroulette.di.module.ActivityModule
import com.dedio.russianroulette.di.module.ViewModelModule
import com.dedio.russianroulette.di.scopes.ActivityScope
import com.dedio.russianroulette.views.roulette.RouletteView
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class, ViewModelModule::class))
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun inject(view: RouletteView)
}