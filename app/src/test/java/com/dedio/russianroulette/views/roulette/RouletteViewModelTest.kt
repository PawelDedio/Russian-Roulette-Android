package com.dedio.russianroulette.views.roulette

import android.view.MotionEvent
import android.view.MotionEvent.*
import android.widget.ImageView
import com.winterbe.expekt.should
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import kotlin.math.roundToInt

class RouletteViewModelTest : Spek({

    lateinit var mockedView: RouletteView

    lateinit var mockedImageView: ImageView

    lateinit var mockedAnimator: RouletteAnimator

    lateinit var viewModel: RouletteViewModel


    beforeEachTest {
        mockedView = mockk(relaxed = true)
        mockedImageView = mockk(relaxed = true)
        mockedAnimator = mockk(relaxed = true)
        viewModel = RouletteViewModel(mockedAnimator)
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

    describe("onBulletTouchListener") {

        var defaultX = 0f
        var defaultY = 0f

        fun mockMotionEvent(rawX: Float, rawY: Float, action: Int): MotionEvent {
            var event: MotionEvent = mockk(relaxed = true)
            every { event.rawX } returns rawX
            every { event.rawY } returns rawY
            every { event.action } returns action

            return event
        }

        fun actionDown(rawX: Float = defaultX, rawY: Float = defaultY) {
            var mockedEvent = mockMotionEvent(rawX, rawY, ACTION_DOWN)

            viewModel.onBulletTouchListener(mockedView, mockedEvent)
        }

        fun actionMove(rawX: Float = defaultX, rawY: Float = defaultY) {
            var mockedEvent = mockMotionEvent(rawX, rawY, ACTION_MOVE)

            viewModel.onBulletTouchListener(mockedView, mockedEvent)
        }

        fun actionUp(rawX: Float = defaultX, rawY: Float = defaultY) {
            var mockedEvent = mockMotionEvent(rawX, rawY, ACTION_UP)

            viewModel.onBulletTouchListener(mockedView, mockedEvent)
        }

        fun init(viewWidth: Int, viewHeight: Int, imageViewWidth: Int, imageViewHeight: Int) {
            every { (mockedView.width) } returns viewWidth
            every { (mockedView.height) } returns viewHeight
            every { (mockedImageView.width) } returns imageViewWidth
            every { (mockedImageView.height) } returns imageViewHeight

            viewModel.initPosition(mockedView, mockedImageView)
        }

        beforeEachTest {
            init(400, 800, 50, 50)
        }

        context("action down") {

            it("should cancel animation") {
                actionDown()

                verify { mockedAnimator.cancel() }
            }
        }

        context("action move") {

            context("touch distance out of tolerance") {
                defaultX = 200f
                defaultY = 400f

                it("should not change angle") {
                    var oldAngle = viewModel.state.angle

                    actionMove()

                    var newAngle = viewModel.state.angle

                    oldAngle.should.equals(newAngle)
                }
            }

            context("touch distance in tolerance") {
                defaultX = 375f
                defaultY = 400f

                it("should not change angle") {
                    var oldAngle = viewModel.state.angle

                    actionMove()

                    var newAngle = viewModel.state.angle

                    oldAngle.should.not.equals(newAngle)
                }
            }

            it("should not cancel animation") {
                actionMove()

                verify(inverse = true) { mockedAnimator.cancel() }
            }

            context("first quarter") {

                it("should set correct angle at the start") {
                    defaultX = 202f
                    defaultY = 227f

                    actionMove()

                    viewModel.state.angle.roundToInt().should.equal(1)
                }

                it("should set correct angle in middle") {
                    defaultX = 323f
                    defaultY = 277f

                    actionMove()

                    viewModel.state.angle.should.equal(45f)
                }

                it("should set correct angle at the end") {
                    defaultX = 375f
                    defaultY = 400f

                    actionMove()

                    viewModel.state.angle.should.equal(90f)
                }
            }

            context("second quarter") {
                it("should set correct angle at the start") {
                    defaultX = 374f
                    defaultY = 402.8889f

                    actionMove()

                    viewModel.state.angle.should.equal(91f)
                }

                it("should set correct angle in middle") {
                    defaultX = 320f
                    defaultY = 520f

                    actionMove()

                    viewModel.state.angle.should.equal(135f)
                }

                it("should set correct angle at the end") {
                    defaultX = 200f
                    defaultY = 575f

                    actionMove()

                    viewModel.state.angle.should.equal(180f)
                }
            }

            context("third quarter") {
                it("should set correct angle at the start") {
                    defaultX = 198f
                    defaultY = 573f

                    actionMove()

                    viewModel.state.angle.roundToInt().should.equal(181)
                }

                it("should set correct angle in middle") {
                    defaultX = 80f
                    defaultY = 520f

                    actionMove()

                    viewModel.state.angle.should.equal(225f)
                }

                it("should set correct angle at the end") {
                    defaultX = 200f
                    defaultY = 225f

                    actionMove()

                    viewModel.state.angle.should.equal(0f)
                }
            }

            context("fourth quarter") {
                it("should set correct angle at the start") {
                    defaultX = 24.97f
                    defaultY = 396.08f

                    actionMove()

                    viewModel.state.angle.roundToInt().should.equal(271)
                }

                it("should set correct angle in middle") {
                    defaultX = 77f
                    defaultY = 277f

                    actionMove()

                    viewModel.state.angle.should.equal(315f)
                }

                it("should set correct angle at the end") {
                    defaultX = 200f
                    defaultY = 225f

                    actionMove()

                    viewModel.state.angle.should.equal(0f)
                }
            }
        }

        context("action up") {
            context("last move time more than 10 millis ago") {

                beforeEachTest {
                    actionMove()
                    Thread.sleep(10)
                }

                it("should not start animation") {
                    actionUp()

                    verify(inverse = true) { mockedAnimator.start(any(), any(), any()) }
                }
            }

            context("last move time less than 10 millis ago") {
                beforeEachTest {
                    actionMove()
                }

                it("should start animation") {
                    actionUp()

                    verify { mockedAnimator.start(any(), any(), any()) }
                }
            }
        }
    }
})