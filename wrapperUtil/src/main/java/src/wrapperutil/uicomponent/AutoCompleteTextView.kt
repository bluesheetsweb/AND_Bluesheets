package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.AutoCompleteTextView
import src.wrapperutil.R
import src.wrapperutil.utilities.ColorPicker
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

class AutoCompleteTextView : AutoCompleteTextView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        intiAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        intiAttributes(attrs)
    }

    companion object {
        val TAG = AutoCompleteTextView::class.java.simpleName
    }

    init {
        var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
        var paddingOther = resources.getDimensionPixelOffset(R.dimen._5dp)
        setPadding(paddingOther, padding, paddingOther, padding)
//        background.mutate().setColorFilter(Color.parseColor(DCColorPicker.GRAY_50),
//                PorterDuff.Mode.SRC_ATOP)
    }

    private fun intiAttributes(attrs: AttributeSet) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.AttrEditText, 0, 0)
        var textMode = array.getInt(R.styleable.AttrEditText_edit_mode, 0)
        setHintTextColor(Color.parseColor(ColorPicker.GRAY_50))
        var padding = resources.getDimensionPixelOffset(R.dimen._15dp)
        var paddingOther = resources.getDimensionPixelOffset(R.dimen._3dp)
        setPadding(paddingOther, padding, paddingOther, padding)
        if (background != null) {
            if (isFocused)
                background.mutate().setColorFilter(
                    Color.parseColor(ColorPicker.BLACK),
                    PorterDuff.Mode.SRC_ATOP
                )
            else
                background.mutate().setColorFilter(
                    Color.parseColor(ColorPicker.GRAY_25),
                    PorterDuff.Mode.SRC_ATOP
                )
        }
        updateMode(WrapperEnumAnnotation(textMode))
        setTextColor(Color.parseColor(ColorPicker.BLACK))
        array.recycle()
    }

    fun updateMode(mode: WrapperEnumAnnotation) {
        @WrapperEnumAnnotation.EditMode val status = mode.state
        when (status) {
            WrapperConstant.EDIT_MODE_LARGE -> {
                try {
                    var fontSize = resources.getDimension(R.dimen.txt_size_20sp)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                    typeface = Typeface.DEFAULT_BOLD
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.EDIT_MODE_SMALL -> {
                try {
                    var fontSize = resources.getDimension(R.dimen.txt_size_12sp)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.EDIT_MODE_MEDIUM -> {
                try {
                    var fontSize = resources.getDimension(R.dimen.txt_size_15sp)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.EDIT_MODE_ERROR -> {
                try {
                    background.mutate().setColorFilter(
                        Color.parseColor(ColorPicker.PRIMARY),
                        PorterDuff.Mode.SRC_ATOP
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            else -> {
                try {
                    var fontSize = resources.getDimension(R.dimen.txt_size_15sp)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
                    typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
