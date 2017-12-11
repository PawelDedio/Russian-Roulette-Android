package com.dedio.russianroulette.base

import android.databinding.ObservableField
import com.dedio.russianroulette.main.User

abstract class ViewModelActivity<out ViewModel : BaseViewModel> : BaseActivity() {

    private lateinit var viewModel: ViewModel
    lateinit var user: ObservableField<User>

    abstract fun getViewModel() : ViewModel
}