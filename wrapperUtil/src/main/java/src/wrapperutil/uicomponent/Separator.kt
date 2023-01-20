package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import src.wrapperutil.R
import src.wrapperutil.utilities.ColorPicker
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

class Separator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, var defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    var sepratorMode: Int
    var sepratorType: Int = WrapperConstant.SEPARATOR_TYPE_HORIZONTAL

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.AttrSeparator, 0, 0)
        defStyleAttr = R.style.MyToolbarStyle
        sepratorMode = array.getInt(R.styleable.AttrSeparator_seprator_mode, 0)
        sepratorType = array.getInt(R.styleable.AttrSeparator_seprator_type, 0)
        updateMode(WrapperEnumAnnotation(sepratorMode))
        array.recycle()
        if (sepratorMode == WrapperConstant.SEPARATOR_MODE_LARGE || sepratorMode == WrapperConstant.SEPARATOR_MODE_MEDIUM ||
            sepratorMode == WrapperConstant.SEPARATOR_MODE_NORMAL
        )
            setBackgroundColor(Color.parseColor(ColorPicker.SEPERATOR_COLOR_TWO))
        else
            setBackgroundColor(Color.parseColor(ColorPicker.SEPERATOR_COLOR))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // val desiredHeight = resources.getDimensionPixelOffset(R.dimen._1dp)
        var finalWidth: Int = 0
        var finalHeight: Int = 0
        if (sepratorType.equals(WrapperConstant.SEPARATOR_TYPE_VERTICAL)) {
            val desiredHeight = View.MeasureSpec.getSize(heightMeasureSpec)
            val widthSize = updateMode(WrapperEnumAnnotation(sepratorMode))
            finalWidth = widthSize
            finalHeight = desiredHeight
        } else {
            val desiredHeight = updateMode(WrapperEnumAnnotation(sepratorMode))
            val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
            finalWidth = widthSize
            finalHeight = desiredHeight
        }

        super.setMeasuredDimension(finalWidth, finalHeight)
    }

    fun updateMode(mode: WrapperEnumAnnotation): Int {
        @WrapperEnumAnnotation.SeparatorMode val status = mode.state
        when (status) {
            WrapperConstant.SEPARATOR_MODE_TINY -> {
                var height = resources.getDimensionPixelOffset(R.dimen._1dp)
                return height
                // layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
            }
            WrapperConstant.SEPARATOR_MODE_TINY_PLUS -> {
                var height = resources.getDimensionPixelOffset(R.dimen._2dp)
                return height
                // layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
            }
            WrapperConstant.SEPARATOR_MODE_NORMAL -> {
                // var height = resources.getDimensionPixelOffset(R.dimen._5dp)
                var height = resources.getDimensionPixelOffset(R.dimen._8dp)
                return height
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
            }
            WrapperConstant.SEPARATOR_MODE_MEDIUM -> {
                // var height = resources.getDimensionPixelOffset(R.dimen._5dp)
                var height = resources.getDimensionPixelOffset(R.dimen._8dp)
                return height
                // layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
            }
            WrapperConstant.SEPARATOR_MODE_LARGE -> {
                var height = resources.getDimensionPixelOffset(R.dimen._16dp)
                return height
                // layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
            }
            else -> {
                var height = resources.getDimensionPixelOffset(R.dimen._1dp)
                return height
                // layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
            }
        }
    }
}
