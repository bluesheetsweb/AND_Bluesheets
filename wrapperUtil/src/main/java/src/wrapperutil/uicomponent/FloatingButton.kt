package src.wrapperutil.uicomponent

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import src.wrapperutil.R
import src.wrapperutil.utilities.ColorPicker
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

class FloatingButton : FloatingActionButton {

    // var gradientDrawable: GradientDrawable?=null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        intiAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        intiAttributes(attrs)
    }

    private fun intiAttributes(attrs: AttributeSet) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.AttrFloatingButton, 0, 0)
        var floatinMode = array.getInt(R.styleable.AttrFloatingButton_floating_mode, 0)
        //  gradientDrawable= GradientDrawable()
        updateMode(WrapperEnumAnnotation(floatinMode))
        array.recycle()
    }

    fun updateMode(mode: WrapperEnumAnnotation) {
        @WrapperEnumAnnotation.FloatingMode val status = mode.state
        when (status) {
            WrapperConstant.FLOATING_TYPE_CIRCLE -> {
                try {
                    // TODO need to change for background
                   /* gradientDrawable?.shape=GradientDrawable.OVAL
                    gradientDrawable?.setColor(Color.parseColor(DCColorPicker.SECONDARY))*/
                    backgroundTintList = ColorStateList.valueOf(Color.parseColor(ColorPicker.SECONDARY))
                    // setPadding(resources.getDimensionPixelOffset(R.dimen._20dp),resources.getDimensionPixelOffset(R.dimen._20dp),resources.getDimensionPixelOffset(R.dimen._20dp),resources.getDimensionPixelOffset(R.dimen._20dp))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WrapperConstant.FLOATING_TYPE_RECTANGLE -> {
                try {
                  /*  gradientDrawable?.shape=GradientDrawable.RECTANGLE
                    gradientDrawable?.cornerRadius=20f
                    gradientDrawable?.setColor(Color.parseColor(DCColorPicker.SECONDARY))*/
                    backgroundTintList = ColorStateList.valueOf(Color.parseColor(ColorPicker.SECONDARY))
                    // background=gradientDrawable
                    setPadding(resources.getDimensionPixelOffset(R.dimen._10dp), resources.getDimensionPixelOffset(R.dimen._10dp), resources.getDimensionPixelOffset(R.dimen._10dp), resources.getDimensionPixelOffset(R.dimen._10dp))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            else -> {
                try {
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
