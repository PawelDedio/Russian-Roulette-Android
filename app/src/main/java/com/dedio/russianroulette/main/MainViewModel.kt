package com.dedio.russianroulette.main

import android.util.Log
import com.dedio.russianroulette.base.BaseViewModel
import javax.inject.Inject

class MainViewModel : BaseViewModel  {

    @Inject constructor() {

    }

    fun print(text: String) {
        Log.e("MainViewModel", text)
    }
}