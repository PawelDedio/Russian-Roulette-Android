package com.dedio.russianroulette.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.dedio.russianroulette.MainApplication
import com.dedio.russianroulette.R
import com.dedio.russianroulette.base.ViewModelActivity
import com.dedio.russianroulette.di.components.ActivityComponent
import javax.inject.Inject

class MainActivity : ViewModelActivity<MainViewModel>() {

    private lateinit var activityComponent: ActivityComponent

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prepareActivityComponent()
        mainViewModel.print("xDDDDD")
    }

    override fun getViewModel(): MainViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private fun prepareActivityComponent() {
        activityComponent = MainApplication.getApplication(this).createActivityComponent(this)
        activityComponent.inject(this)
    }
}
