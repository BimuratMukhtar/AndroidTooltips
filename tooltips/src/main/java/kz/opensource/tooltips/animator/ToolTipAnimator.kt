package kz.opensource.tooltips.animator

import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View

/**
 * Animator for the tooltip view.
 */
interface ToolTipAnimator {
    /**
     * Object animator for the tooltip view to pop-up.
     * @param view The tooltip view.
     * @param duration Duration for the animator.
     * @return ObjectAnimator
     */
    fun popup(view: View, duration: Long): ObjectAnimator

    /**
     * Object animator for the tooltip view to pop-out/hide.
     * @param view The tooltip view.
     * @param duration Duration for the animator.
     * @param animatorListenerAdapter The animator listener adapter to listen for animation event.
     * @return ObjectAnimator
     */
    fun popout(
        view: View,
        duration: Long,
        animatorListenerAdapter: AnimatorListenerAdapter
    ): ObjectAnimator
}
