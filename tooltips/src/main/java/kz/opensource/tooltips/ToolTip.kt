package kz.opensource.tooltips

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef

private const val DEFAULT_OFFSET = 0

class ToolTip(
    val anchorView: View,
    val rootView: ViewGroup,
    val contentView: View,
    @Position var position: Int,
    @Align val align: Int = ALIGN_CENTER,
    val isArrowShown: Boolean = true,
    val backgroundColor: Int = Color.CYAN,
    val elevation: Float = 0F,
    val offsetX: Int = DEFAULT_OFFSET,
    val offsetY: Int = DEFAULT_OFFSET
) {
    @IntDef(POSITION_ABOVE, POSITION_BELOW, POSITION_LEFT_TO, POSITION_RIGHT_TO)
    annotation class Position

    @IntDef(ALIGN_CENTER, ALIGN_LEFT, ALIGN_RIGHT)
    annotation class Align

    fun positionedLeftTo(): Boolean {
        return POSITION_LEFT_TO == position
    }

    fun positionedRightTo(): Boolean {
        return POSITION_RIGHT_TO == position
    }

    fun positionedAbove(): Boolean {
        return POSITION_ABOVE == position
    }

    fun positionedBelow(): Boolean {
        return POSITION_BELOW == position
    }

    fun alignedCenter(): Boolean {
        return ALIGN_CENTER == align
    }

    fun alignedLeft(): Boolean {
        return ALIGN_LEFT == align
    }

    fun alignedRight(): Boolean {
        return ALIGN_RIGHT == align
    }

    companion object {
        const val POSITION_ABOVE = 0
        const val POSITION_BELOW = 1
        const val POSITION_LEFT_TO = 3
        const val POSITION_RIGHT_TO = 4
        const val ALIGN_CENTER = 0
        const val ALIGN_LEFT = 1
        const val ALIGN_RIGHT = 2
        const val GRAVITY_CENTER = 0
        const val GRAVITY_LEFT = 1
        const val GRAVITY_RIGHT = 2
    }
}
