package com.dedio.russianroulette.views.roulette

import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.animation.FloatValueHolder
import android.util.Log
import android.util.TimeUtils
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import android.widget.ImageView
import java.lang.Math.abs
import java.lang.Math.pow
import java.util.concurrent.TimeUnit

class RouletteViewModel {

    val state: BulletState = BulletState(0, 0f)

    private var downTouchX = 0f
    private var downTouchY = 0f
    private var centerX = 0f
    private var centerY = 0f
    private val moveTolerance = 0.1
    private val locationInWindow: IntArray = IntArray(2)
    private var imageWidth = 0
    private var imageHeight = 0

    private var oldMoveTime: Long = 0
    private var oldMoveAngle = 0f
    private var lastMoveTime: Long = 0
    private var lastMoveAngle = 0f

    private var throwAnim: FlingAnimation? = null


    fun initPosition(view: RouletteView, imageView: ImageView) {
        centerX = view.width/2f
        centerY = view.height / 2f + locationInWindow[1]
        view.getLocationInWindow(locationInWindow)
        imageWidth = imageView.width
        imageHeight = imageView.height

        var radius = calculateRadius(view, imageView)
        state.radius = radius
        state.angle = 90f

    }

    private fun calculateRadius(view: RouletteView, imageView: ImageView): Int {
        var radius = view.width/2 - imageView.width/2
        return radius
    }

    fun onBulletTouchListener(view: View, event: MotionEvent): Boolean {

        when(event.action) {
            ACTION_DOWN -> {
                throwAnim?.cancel()
                downTouchX = event.rawX
                downTouchY = event.rawY
                log("event: down, x: ${event.rawX} y: ${event.rawY}")
            }

            ACTION_MOVE -> {
                oldMoveAngle = state.angle.plus(0)
                makeMove(event)
                lastMoveAngle = state.angle.plus(0)

                oldMoveTime = lastMoveTime
                lastMoveTime = System.currentTimeMillis()
                log("event: move, x: ${event.rawX} y: ${event.rawY} angle: ${state.angle}")
            }

            ACTION_UP -> {
                log("event: up, x: ${event.rawX} y: ${event.rawY}")
                if (System.currentTimeMillis() - lastMoveTime > 10) {
                    log("event up: ############## without throw ##############")
                } else {
                    log("event up: ############## throw ##############")
                    animateThrow()
                }
            }
        }
        return true
    }

    private fun makeMove(event: MotionEvent) {
        val xPosition = event.rawX
        val yPosition = event.rawY

        val distanceFromCenter = distanceFromCenter(xPosition, yPosition)
        val mistake = abs(state.radius - distanceFromCenter)

        if(mistake < state.radius * moveTolerance) {
            val quarter = recognizeQuarter(xPosition, yPosition)
            doActionForQuarter(quarter, xPosition, yPosition)
        }
    }

    private fun distanceFromCenter(xPosition: Float, yPosition: Float): Double {
        val xPow = pow((xPosition - centerX).toDouble(), 2.0)
        val yPow = pow((yPosition - centerY).toDouble(), 2.0)
        return pow((xPow + yPow), 0.5)
    }

    private fun recognizeQuarter(xPosition: Float, yPosition: Float): Int {
        return if(xPosition >= centerX) {
            if(yPosition >= centerY) {
                2
            } else {
                1
            }
        } else {
            if(yPosition >= centerY) {
                3
            } else {
                4
            }
        }
    }

    private fun doActionForQuarter(quarter: Int, xPosition: Float, yPosition: Float) {
        val startDegree: Float
        val ratioX: Float
        val ratioY: Float
        val ratio: Float
        val degree: Float
        val startX = centerX - state.radius
        val endX = centerX + state.radius
        val startY = centerY - state.radius + locationInWindow[1]
        val endY = centerY + state.radius

        when(quarter) {
            1 -> {
                ratioX = (xPosition - centerX) / (endX - centerX)
                ratioY = (yPosition - startY) / (centerY - startY)
                startDegree = 0f
            }

            2 -> {
                ratioX = 1 - (xPosition - centerX) / (endX - centerX)
                ratioY = (yPosition - centerY) / (endY - centerY)
                startDegree = 90f
            }

            3 -> {
                ratioX = 1 - (xPosition - startX) / (centerX - startX)
                ratioY = 1 - (yPosition - centerY) / (endY - centerY)
                startDegree = 180f
            }

            4 -> {
                ratioX = (xPosition - startX) / (centerX - startX)
                ratioY = 1 - (yPosition - startY) / (centerY - startY)
                startDegree = 270f
            }
            else -> throw IllegalArgumentException("Wrong quarter")
        }

        ratio = (ratioX + ratioY) / 2
        degree = 90 * ratio + startDegree
        state.angle = degree
    }

    private fun animateThrow() {
        val holder = FloatValueHolder(state.angle)
        val distance = abs(lastMoveAngle - oldMoveAngle)
        val time: Float = (lastMoveTime - oldMoveTime).toFloat() / TimeUnit.SECONDS.toMillis(1)
        var velocity: Float = distance / time
        if (lastMoveAngle < oldMoveAngle) {
            velocity *= -1
        }

        throwAnim = FlingAnimation(holder)
                .setStartVelocity(velocity)
                .setFriction(0.18f)
                .addUpdateListener { dynamicAnimation, value, velocity ->
                    state.angle = value % 360
                }
        throwAnim?.start()
    }

    private fun log(text: String) {
        Log.e("RouletteViewModel", text)
    }
}