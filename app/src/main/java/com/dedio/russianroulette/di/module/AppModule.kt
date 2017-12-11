package com.dedio.russianroulette.di.module

import android.content.Context
import com.dedio.russianroulette.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: MainApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = context.baseContext
}