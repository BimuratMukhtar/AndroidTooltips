package kz.opensource.tooltips

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import androidx.core.content.res.ResourcesCompat
import kz.opensource.tooltips.utils.RtlConfig

internal class ToolTipBackgroundConstructor(
    private val rtlConfig: RtlConfig
) {

    /**
     * Select which background will be assign to the tip view
     */
    fun setBackground(tipView: View, toolTip: ToolTip) {

        // show tool tip without arrow. no need to continue
        if (!toolTip.isArrowShown) {
            setToolTipNoArrowBackground(tipView, toolTip.backgroundColor)
            return
        }
        when (toolTip.position) {
            ToolTip.POSITION_ABOVE -> setToolTipAboveBackground(tipView, toolTip)
            ToolTip.POSITION_BELOW -> setToolTipBelowBackground(tipView, toolTip)
            ToolTip.POSITION_LEFT_TO -> setToolTipLeftToBackground(tipView, toolTip.backgroundColor)
            ToolTip.POSITION_RIGHT_TO -> setToolTipRightToBackground(
                tipView,
                toolTip.backgroundColor
            )
        }
    }

    private fun setToolTipAboveBackground(tipView: View, toolTip: ToolTip) {
        when (toolTip.align) {
            ToolTip.ALIGN_CENTER -> setTipBackground(
                tipView,
                R.drawable.tooltip_arrow_down,
                toolTip.backgroundColor
            )
            ToolTip.ALIGN_LEFT -> setTipBackground(
                tipView,
                if (!rtlConfig.isRtl) R.drawable.tooltip_arrow_down_left else R.drawable.tooltip_arrow_down_right,
                toolTip.backgroundColor
            )
            ToolTip.ALIGN_RIGHT -> setTipBackground(
                tipView,
                if (!rtlConfig.isRtl) R.drawable.tooltip_arrow_down_right else R.drawable.tooltip_arrow_down_left,
                toolTip.backgroundColor
            )
        }
    }

    private fun setToolTipBelowBackground(tipView: View, toolTip: ToolTip) {
        when (toolTip.align) {
            ToolTip.ALIGN_CENTER -> setTipBackground(
                tipView,
                R.drawable.tooltip_arrow_up,
                toolTip.backgroundColor
            )
            ToolTip.ALIGN_LEFT -> setTipBackground(
                tipView,
                if (!rtlConfig.isRtl) R.drawable.tooltip_arrow_up_left else R.drawable.tooltip_arrow_up_right,
                toolTip.backgroundColor
            )
            ToolTip.ALIGN_RIGHT -> setTipBackground(
                tipView,
                if (!rtlConfig.isRtl) R.drawable.tooltip_arrow_up_right else R.drawable.tooltip_arrow_up_left,
                toolTip.backgroundColor
            )
        }
    }

    private fun setToolTipLeftToBackground(tipView: View, color: Int) {
        setTipBackground(
            tipView,
            if (!rtlConfig.isRtl) R.drawable.tooltip_arrow_right else R.drawable.tooltip_arrow_left,
            color
        )
    }

    private fun setToolTipRightToBackground(tipView: View, color: Int) {
        setTipBackground(
            tipView,
            if (!rtlConfig.isRtl) R.drawable.tooltip_arrow_left else R.drawable.tooltip_arrow_right,
            color
        )
    }

    private fun setToolTipNoArrowBackground(tipView: View, color: Int) {
        setTipBackground(tipView, R.drawable.tooltip_no_arrow, color)
    }

    private fun setTipBackground(tipView: View, drawableRes: Int, color: Int) {
        val paintedDrawable = getTintedDrawable(
            tipView.context,
            drawableRes,
            color
        )
        setViewBackground(tipView, paintedDrawable)
    }

    private fun setViewBackground(view: View, drawable: Drawable?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.background = drawable
        } else {
            view.setBackgroundDrawable(drawable)
        }
    }

    private fun getTintedDrawable(context: Context, drawableRes: Int, color: Int): Drawable? {
        val drawable: Drawable?
        val res = context.resources
        drawable = ResourcesCompat.getDrawable(res, drawableRes, null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable?.setTint(color)
        } else {
            drawable?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
        return drawable
    }
}
