package com.dedio.russianroulette.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class MainViewModelFactory : ViewModelProvider.Factory {

    @Inject
    constructor()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}