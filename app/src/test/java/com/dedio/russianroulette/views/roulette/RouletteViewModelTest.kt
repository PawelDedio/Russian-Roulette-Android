package com.dedio.russianroulette.views.roulette

import android.widget.ImageView
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.winterbe.expekt.should
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class RouletteViewModelTest : Spek({

    lateinit var mockedView: RouletteView

    lateinit var mockedImageView: ImageView

    lateinit var viewModel: RouletteViewModel


    beforeEachTest {
        mockedView = mock()
        mockedImageView = mock()
        viewModel = RouletteViewModel()
    }

    describe("initPosition") {
        it("should set correct angle") {
            viewModel.initPosition(mockedView, mockedImageView)

            viewModel.state.angle.should.equal(90f)
        }

        it("should set correct radius") {
            whenever(mockedView.width).thenReturn(400)
            whenever(mockedView.height).thenReturn(800)
            whenever(mockedImageView.width).thenReturn(50)
            whenever(mockedImageView.height).thenReturn(50)

            viewModel.initPosition(mockedView, mockedImageView)

            viewModel.state.radius.should.equal(400/2 - 50/2)
        }
    }
})