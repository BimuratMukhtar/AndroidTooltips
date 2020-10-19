package kz.opensource.tooltips.app

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.TextView

class TextViewProvider(
    private val textConfig: TextConfig
) {

    fun getView(
        context: Context
    ): TextView {
        val tipView = TextView(context)
        tipView.text = textConfig.message
        tipView.visibility = View.INVISIBLE
        tipView.gravity = textConfig.viewTextGravity
        setTextAppearance(tipView, context)
        setTextTypeFace(tipView)
        return tipView
    }

    private fun setTextAppearance(tipView: TextView, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tipView.setTextAppearance(textConfig.textAppearanceStyle)
        } else {
            tipView.setTextAppearance(context, textConfig.textAppearanceStyle)
        }
    }

    /**
     * Sets the custom typeface on the tipView if it was provided via Tooltip.
     */
    private fun setTextTypeFace(tipView: TextView) {
        if (textConfig.typeface != null) {
            val existingTypeFace = tipView.typeface
            if (existingTypeFace != null) {
                // Preserve the text style defined in the text appearance style if available
                tipView.setTypeface(textConfig.typeface, existingTypeFace.style)
            } else {
                tipView.typeface = textConfig.typeface
            }
        }
    }
}
