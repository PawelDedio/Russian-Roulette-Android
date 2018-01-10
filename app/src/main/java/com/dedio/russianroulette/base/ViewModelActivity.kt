package com.dedio.russianroulette.base

import android.databinding.ObservableField
import android.os.Bundle
import com.dedio.russianroulette.main.User

abstract class ViewModelActivity<ViewModel : BaseViewModel> : BaseActivity() {

    protected lateinit var viewModel: ViewModel
    lateinit var user: ObservableField<User>

    abstract fun prepareActivityComponent()

    abstract fun createViewModel() : ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareActivityComponent()
        viewModel = createViewModel()
    }
}