package com.dedio.russianroulette.views.roulette

import android.widget.ImageView
import com.winterbe.expekt.should
import io.mockk.every
import io.mockk.mockk
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class RouletteViewModelTest : Spek({

    lateinit var mockedView: RouletteView

    lateinit var mockedImageView: ImageView

    lateinit var viewModel: RouletteViewModel


    beforeEachTest {
        mockedView = mockk(relaxed = true)
        mockedImageView = mockk(relaxed = true)
        viewModel = RouletteViewModel()
    }

    describe("initPosition") {
        it("should set correct angle") {
            viewModel.initPosition(mockedView, mockedImageView)

            viewModel.state.angle.should.equal(90f)
        }

        it("should set correct radius") {
            every { (mockedView.width) } returns 400
            every { (mockedView.height) } returns 800
            every { (mockedImageView.width) } returns 50
            every { (mockedImageView.height) } returns 50

            viewModel.initPosition(mockedView, mockedImageView)

            viewModel.state.radius.should.equal(400/2 - 50/2)
        }
    }
})