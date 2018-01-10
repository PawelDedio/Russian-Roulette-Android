package com.dedio.russianroulette.views.roulette

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dedio.russianroulette.MainApplication
import com.dedio.russianroulette.R
import com.dedio.russianroulette.base.BaseActivity
import com.dedio.russianroulette.databinding.ViewRouletteBinding
import com.dedio.russianroulette.di.components.ActivityComponent
import javax.inject.Inject

class RouletteView : ConstraintLayout {

    private lateinit var binding: ViewRouletteBinding

    @Inject
    lateinit var viewModelFactory: RouletteViewModelFactory

    private lateinit var viewModel: RouletteViewModel
    private lateinit var activityComponent: ActivityComponent

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        prepareComponent()
        viewModel = ViewModelProviders.of(context as BaseActivity, viewModelFactory).get(RouletteViewModel::class.java)
        val inflater = LayoutInflater.from(context)
        val root = inflate(context, R.layout.view_roulette, this) as ViewGroup
        binding = ViewRouletteBinding.inflate(inflater, root, true)
        binding.viewModel = viewModel
        post({
            binding.rouletteBullet.visibility = View.VISIBLE
            viewModel.initPosition(this, binding.rouletteBullet)
        })
    }

    private fun prepareComponent() {
        activityComponent = MainApplication.getApplication(context).createActivityComponent(context as BaseActivity)
        activityComponent.inject(this)
    }
}