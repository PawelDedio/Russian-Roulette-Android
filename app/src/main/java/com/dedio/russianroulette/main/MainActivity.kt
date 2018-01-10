package com.dedio.russianroulette.main

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
    lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.print("xDDDDD")
    }

    override fun createViewModel(): MainViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun prepareActivityComponent() {
        activityComponent = MainApplication.getApplication(this).createActivityComponent(this)
        activityComponent.inject(this)
    }
}
