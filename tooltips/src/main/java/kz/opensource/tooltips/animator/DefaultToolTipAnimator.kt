package kz.opensource.tooltips.animator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.OvershootInterpolator

internal class DefaultToolTipAnimator : ToolTipAnimator {

    override fun popup(view: View, duration: Long): ObjectAnimator {
        view.alpha = 0f
        view.visibility = View.VISIBLE
        val popup = ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
            PropertyValuesHolder.ofFloat("scaleX", 0f, 1f),
            PropertyValuesHolder.ofFloat("scaleY", 0f, 1f)
        )
        popup.duration = duration
        popup.interpolator = OvershootInterpolator()

        return popup
    }

    override fun popout(
        view: View,
        duration: Long,
        animatorListenerAdapter: AnimatorListenerAdapter
    ): ObjectAnimator {
        val popout = ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat("alpha", 1f, 0f),
            PropertyValuesHolder.ofFloat("scaleX", 1f, 0f),
            PropertyValuesHolder.ofFloat("scaleY", 1f, 0f)
        )
        popout.duration = duration
        popout.addListener(
            object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.visibility = View.GONE
                    animatorListenerAdapter.onAnimationEnd(animation)
                }
            }
        )
        popout.interpolator = AnticipateOvershootInterpolator()

        return popout
    }
}
