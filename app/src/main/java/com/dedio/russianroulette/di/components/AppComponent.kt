package com.dedio.russianroulette.di.components

import android.content.Context
import com.dedio.russianroulette.di.module.ActivityModule
import com.dedio.russianroulette.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun plus(module: ActivityModule): ActivityComponent
    fun context(): Context
}