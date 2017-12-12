package com.dedio.russianroulette.views.roulette

import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import android.widget.ImageView
import java.lang.Math.abs
import java.lang.Math.pow

class RouletteViewModel {

    val state: BulletState = BulletState(0, 0f)

    private var downTouchX = 0f
    private var downTouchY = 0f
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private val moveTolerance = 0.2
    private val locationInWindow: IntArray = IntArray(2)
    private var imageWidth = 0
    private var imageHeight = 0


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
                downTouchX = event.rawX
                downTouchY = event.rawY
            }

            ACTION_MOVE -> {
                makeMove(event)
            }

            ACTION_UP -> {
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
}