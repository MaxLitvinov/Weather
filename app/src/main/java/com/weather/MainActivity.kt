package com.weather

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {

        private const val SPLASH_SCREEN_ANIMATION_DURATION = 2200L
    }

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        /* TODO: Keep splash screen displayed?
        splashScreen.setKeepOnScreenCondition(object : SplashScreen.KeepOnScreenCondition {
            override fun shouldKeepOnScreen(): Boolean {
                return viewModel.shouldKeepSplashScreenVisible
            }
        })*/
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            initAnimation(splashScreenViewProvider) {
                // TODO: On animation end action
                viewModel.onAnimationEnd()
            }
        }
    }

    private fun initAnimation(
        provider: SplashScreenViewProvider,
        onAnimationEnd: () -> Unit,
    ) = with(provider) {
        val rotation = rotationAnimation(iconView)
        val moveLeft = moveLeftAnimation(view, iconView)
        val fadeOut = fadeOutAnimation(iconView)

        AnimatorSet().apply {
            play(rotation).with(moveLeft)
            play(fadeOut)
            doOnEnd {
                remove()
                onAnimationEnd()
            }
            start()
        }
    }

    private fun rotationAnimation(targetView: View): ObjectAnimator =
        ObjectAnimator.ofFloat(
            targetView,
            View.ROTATION,
            0F,
            -3600F
        ).apply {
            interpolator = AnticipateInterpolator()
            duration = SPLASH_SCREEN_ANIMATION_DURATION
        }

    private fun moveLeftAnimation(view: View, iconView: View): ObjectAnimator =
        ObjectAnimator.ofFloat(
            iconView,
            View.TRANSLATION_X,
            0F,
            -(view.width.toFloat() + iconView.width.toFloat() / 2)
        ).apply {
            interpolator = AnticipateInterpolator()
            duration = SPLASH_SCREEN_ANIMATION_DURATION
        }

    private fun fadeOutAnimation(targetView: View): ObjectAnimator =
        ObjectAnimator.ofFloat(
            targetView,
            View.ALPHA,
            1F,
            1F,
            1F,
            0F
        ).apply {
            duration = SPLASH_SCREEN_ANIMATION_DURATION
        }
}
