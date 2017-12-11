package com.dedio.russianroulette.views.roulette

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dedio.russianroulette.R
import com.dedio.russianroulette.databinding.ViewRouletteBinding

class RouletteView : ConstraintLayout {

    private lateinit var binding: ViewRouletteBinding
    private lateinit var viewModel: RouletteViewModel

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
        val inflater = LayoutInflater.from(context)
        val root = inflate(context, R.layout.view_roulette, this) as ViewGroup
        binding = ViewRouletteBinding.inflate(inflater, root, true)
        viewModel = RouletteViewModel()
        binding.viewModel = viewModel
        post({
            viewModel.initPosition(this, binding.rouletteBullet)
        })
    }
}