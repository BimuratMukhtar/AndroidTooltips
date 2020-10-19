package kz.opensource.tooltips

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import kz.opensource.tooltips.animator.DefaultToolTipAnimator
import kz.opensource.tooltips.animator.ToolTipAnimator
import java.util.ArrayList
import java.util.HashMap

private const val DEFAULT_ANIM_DURATION = 400

class ToolTipsManager(
    var animationDuration: Int = DEFAULT_ANIM_DURATION,
    var toolTipAnimator: ToolTipAnimator = DefaultToolTipAnimator(),
    var tipListener: TipListener? = null
) {

    private val tipsMap: MutableMap<Int, View> = HashMap()
    private val tipViewFactory = TipViewFactory()

    interface TipListener {
        fun onTipDismissed(view: View?, anchorViewId: Int, byUser: Boolean)
    }

    fun show(toolTip: ToolTip): View? {
        val tipView = create(toolTip) ?: return null

        // animate tip visibility
        toolTipAnimator.popup(tipView, animationDuration.toLong()).start()
        return tipView
    }

    private fun create(toolTip: ToolTip): View? {
        // only one tip is allowed near an anchor view at the same time, thus
        // reuse tip if already exist
        if (tipsMap.containsKey(toolTip.anchorView.id)) {
            return tipsMap[toolTip.anchorView.id]
        }

        val tipView = tipViewFactory.createOrNull(toolTip) ?: return null

        tipView.setOnClickListener { view -> dismiss(view, true) }

        // bind tipView with anchorView id for later use
        val anchorViewId = toolTip.anchorView.id
        tipView.tag = anchorViewId
        tipsMap[anchorViewId] = tipView

        return tipView
    }

    fun dismiss(tipView: View?, byUser: Boolean): Boolean {
        if (tipView != null && isVisible(tipView)) {
            val key = tipView.tag as Int
            tipsMap.remove(key)
            animateDismiss(tipView, byUser)
            return true
        }
        return false
    }

    fun dismiss(key: Int): Boolean {
        return tipsMap.containsKey(key) && dismiss(tipsMap[key], false)
    }

    fun find(key: Int): View? {
        return if (tipsMap.containsKey(key)) {
            tipsMap[key]
        } else null
    }

    fun findAndDismiss(anchorView: View): Boolean {
        val view = find(anchorView.id)
        return view != null && dismiss(view, false)
    }

    fun dismissAll() {
        if (tipsMap.isNotEmpty()) {
            val entries: List<Map.Entry<Int, View>> =
                ArrayList<Map.Entry<Int, View>>(tipsMap.entries)
            for ((_, value) in entries) {
                dismiss(value, false)
            }
        }
        tipsMap.clear()
    }

    private fun animateDismiss(view: View, byUser: Boolean) {
        toolTipAnimator.popout(
            view,
            animationDuration.toLong(),
            object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    if (tipListener != null) {
                        tipListener!!.onTipDismissed(view, view.tag as Int, byUser)
                    }
                }
            }
        ).start()
    }

    fun isVisible(tipView: View): Boolean {
        return tipView.visibility == View.VISIBLE
    }
}
