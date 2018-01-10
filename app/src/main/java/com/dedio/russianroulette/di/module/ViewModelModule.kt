package com.dedio.russianroulette.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.dedio.russianroulette.base.ViewModelKey
import com.dedio.russianroulette.di.scopes.ActivityScope
import com.dedio.russianroulette.main.MainViewModel
import com.dedio.russianroulette.main.MainViewModelFactory
import com.dedio.russianroulette.views.roulette.RouletteViewModel
import com.dedio.russianroulette.views.roulette.RouletteViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {

    @Provides
    @ActivityScope
    fun provideMainViewModelFactory(): MainViewModelFactory {
        return MainViewModelFactory()
    }
}