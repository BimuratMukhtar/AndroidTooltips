package kz.opensource.tooltips.app

import android.graphics.Typeface
import androidx.annotation.IntDef
import androidx.annotation.StyleRes
import kz.opensource.tooltips.ToolTip

data class TextConfig(
    val message: CharSequence,
    @StyleRes val textAppearanceStyle: Int = R.style.TooltipDefaultStyle,
    @Gravity private val textGravity: Int = ToolTip.GRAVITY_LEFT,
    val typeface: Typeface? = null
) {

    @IntDef(ToolTip.GRAVITY_CENTER, ToolTip.GRAVITY_LEFT, ToolTip.GRAVITY_RIGHT)
    annotation class Gravity

    val viewTextGravity: Int
        get() {
            return when (textGravity) {
                ToolTip.GRAVITY_CENTER -> android.view.Gravity.CENTER
                ToolTip.GRAVITY_LEFT -> android.view.Gravity.START
                ToolTip.GRAVITY_RIGHT -> android.view.Gravity.END
                else -> android.view.Gravity.CENTER
            }
        }

}