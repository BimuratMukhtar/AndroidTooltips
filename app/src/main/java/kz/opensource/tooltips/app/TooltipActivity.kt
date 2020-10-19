package kz.opensource.tooltips.app

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kz.opensource.tooltips.ToolTip
import kz.opensource.tooltips.ToolTip.Companion.ALIGN_CENTER
import kz.opensource.tooltips.ToolTip.Companion.GRAVITY_CENTER
import kz.opensource.tooltips.ToolTipsManager
import kz.opensource.tooltips.ToolTipsManager.TipListener

class TooltipActivity : AppCompatActivity(), TipListener, View.OnClickListener {

    private lateinit var toolTipsManager: ToolTipsManager
    private lateinit var parentLayout: ViewGroup
    private lateinit var anchorView: TextView
    private lateinit var aboveBtn: Button
    private lateinit var belowBtn: Button
    private lateinit var leftToBtn: Button
    private lateinit var rightToBtn: Button
    private lateinit var alignRight: RadioButton
    private lateinit var alignLeft: RadioButton
    private lateinit var alignCenter: RadioButton

    @ToolTip.Align
    var mAlign = ALIGN_CENTER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        parentLayout = findViewById(R.id.root_layout)
        anchorView = findViewById(R.id.text_view)
        toolTipsManager = ToolTipsManager(tipListener = this)

        aboveBtn = findViewById(R.id.button_above)
        belowBtn = findViewById(R.id.button_below)
        leftToBtn = findViewById(R.id.button_left_to)
        rightToBtn = findViewById(R.id.button_right_to)
        alignCenter = findViewById(R.id.button_align_center)
        alignRight = findViewById(R.id.button_align_right)
        alignLeft = findViewById(R.id.button_align_left)

        aboveBtn.setOnClickListener(this)
        belowBtn.setOnClickListener(this)
        leftToBtn.setOnClickListener(this)
        rightToBtn.setOnClickListener(this)
        anchorView.setOnClickListener(this)
        alignCenter.setOnClickListener(this)
        alignLeft.setOnClickListener(this)
        alignRight.setOnClickListener(this)
        alignCenter.setChecked(true)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        // This tip is shows the first time the sample app is loaded. Use a message that gives user
        // guide on how to use the sample app. Also try to showcase the ability of the app.
        val initialGuideTex: CharSequence = Html.fromHtml("Click on the <a href='#'>Buttons</a> " +
            "and the <a href='#'>Radio Buttons</a> bellow to test various tool tip configurations." +
            "<br><br><font color='grey'><small>GOT IT</small></font>")
        toolTipsManager.show(ToolTip(
            anchorView = anchorView,
            rootView = parentLayout,
            align = mAlign,
            position = ToolTip.POSITION_BELOW,
            backgroundColor = Color.DKGRAY,
            contentView = getTextView(TextConfig(
                message = initialGuideTex,
            ))
        ))
    }

    override fun onTipDismissed(view: View?, anchorViewId: Int, byUser: Boolean) {
        // Do something when
    }

    override fun onClick(view: View) {
        val text = TIP_TEXT
        when (view.id) {
            R.id.button_above -> {
                toolTipsManager.findAndDismiss(anchorView)
                toolTipsManager.show(ToolTip(
                    anchorView = anchorView,
                    rootView = parentLayout,
                    align = mAlign,
                    position = ToolTip.POSITION_ABOVE,
                    contentView = getTextView(TextConfig(
                        message = text
                    ))
                ))
            }
            R.id.button_below -> {
                toolTipsManager.findAndDismiss(anchorView)
                val customView = layoutInflater.inflate(R.layout.custom_view, parentLayout, false)
                val customTooltip = ToolTip(
                    anchorView = anchorView,
                    rootView = parentLayout,
                    align = ALIGN_CENTER,
                    position = ToolTip.POSITION_BELOW,
                    backgroundColor = resources.getColor(R.color.colorOrange),
                    contentView = customView
                )
                toolTipsManager.show(customTooltip)
            }
            R.id.button_left_to -> {
                toolTipsManager.findAndDismiss(anchorView)
                toolTipsManager.show(ToolTip(
                    anchorView = anchorView,
                    rootView = parentLayout,
                    align = mAlign,
                    position = ToolTip.POSITION_LEFT_TO,
                    backgroundColor = resources.getColor(R.color.colorLightGreen),
                    contentView = getTextView(TextConfig(
                        message = text,
                        textAppearanceStyle = R.style.TooltipTextAppearance_Small_Black,
                        textGravity = GRAVITY_CENTER
                    ))
                ))
            }
            R.id.button_right_to -> {
                toolTipsManager.findAndDismiss(anchorView)
                toolTipsManager.show(ToolTip(
                    anchorView = anchorView,
                    rootView = parentLayout,
                    align = mAlign,
                    position = ToolTip.POSITION_RIGHT_TO,
                    backgroundColor = resources.getColor(R.color.colorDarkRed),
                    contentView = getTextView(TextConfig(
                        message = TIP_TEXT,
                        textAppearanceStyle = R.style.TooltipTextAppearance_Large,
                        textGravity = GRAVITY_CENTER
                    ))
                ))
            }
            R.id.button_align_center -> mAlign = ALIGN_CENTER
            R.id.button_align_left -> mAlign = ToolTip.ALIGN_LEFT
            R.id.button_align_right -> mAlign = ToolTip.ALIGN_RIGHT
            R.id.text_view -> toolTipsManager.dismissAll()
        }
    }

    private fun getTextView(textConfig: TextConfig): View {
        return TextViewProvider(textConfig).getView(this)
    }

    companion object {
        const val TIP_TEXT = "Tool Tip"
    }
}