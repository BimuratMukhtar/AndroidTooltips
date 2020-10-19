package kz.opensource.tooltips

import android.annotation.SuppressLint
import android.graphics.Outline
import android.graphics.Point
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import kz.opensource.tooltips.model.Coordinates
import kz.opensource.tooltips.utils.RtlConfig

internal class TipViewFactory {

    private val rtlConfig = RtlConfig()
    private val toolTipBackgroundConstructor = ToolTipBackgroundConstructor(rtlConfig)
    private val toolTipCoordinatesFinder = ToolTipCoordinatesFinder(rtlConfig)

    fun createOrNull(toolTip: ToolTip): View? {
        val tipView = toolTip.contentView

        setTipViewElevation(tipView, toolTip)
        if (rtlConfig.isRtl) {
            switchToolTipSidePosition(toolTip)
        }
        toolTipBackgroundConstructor.setBackground(tipView, toolTip)

        // add tip to root layout
        toolTip.rootView.addView(tipView)

        val tipViewPosition = toolTipCoordinatesFinder.getCoordinates(tipView, toolTip)
        moveTipToCorrectPosition(tipView, tipViewPosition)

        return tipView
    }

    private fun switchToolTipSidePosition(toolTip: ToolTip) {
        if (toolTip.positionedLeftTo()) {
            toolTip.position = ToolTip.POSITION_RIGHT_TO
        } else if (toolTip.positionedRightTo()) {
            toolTip.position = ToolTip.POSITION_LEFT_TO
        }
    }

    private fun moveTipToCorrectPosition(tipView: View, point: Point) {
        val tipViewCoordinates = Coordinates(tipView)
        val translationX = point.x - tipViewCoordinates.left
        val translationY = point.y - tipViewCoordinates.top
        tipView.translationX =
            if (!rtlConfig.isRtl) translationX.toFloat() else -translationX.toFloat()
        tipView.translationY = translationY.toFloat()
    }

    private fun setTipViewElevation(tipView: View, toolTip: ToolTip) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (toolTip.elevation > 0) {
                val viewOutlineProvider: ViewOutlineProvider = object : ViewOutlineProvider() {
                    @SuppressLint("NewApi")
                    override fun getOutline(view: View, outline: Outline) {
                        outline.setEmpty()
                    }
                }
                tipView.outlineProvider = viewOutlineProvider
                tipView.elevation = toolTip.elevation
            }
        }
    }
}
