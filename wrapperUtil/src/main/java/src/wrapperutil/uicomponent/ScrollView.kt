package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.ScrollView
import src.wrapperutil.R
import src.wrapperutil.utilities.ColorPicker

class ScrollView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ScrollView(context, attrs, defStyleAttr) {
    private val TAG = "DCRelativeLayout"
    val cornerRadius: Float
    val stokeWidth: Int
    val border: GradientDrawable

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.Shape, 0, 0)
        cornerRadius = array.getDimension(R.styleable.Shape_corner_radius, 0f)
        stokeWidth = array.getDimensionPixelOffset(R.styleable.Shape_stroke_width, 0)
        border = GradientDrawable()
        border.setColor(Color.parseColor(ColorPicker.WHITE))
        border.cornerRadius = cornerRadius
        border.setStroke(stokeWidth, Color.parseColor(ColorPicker.GRAY_50))
        background = border
        array.recycle()
    }

    fun updateViewColor(backgroundColor: Int, stokeColor: Int) {
        border.setColor(backgroundColor)
        border.setStroke(stokeWidth, stokeColor)
    }
}
