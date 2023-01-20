package src.wrapperutil.uicomponent

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import src.wrapperutil.R
import src.wrapperutil.listener.OnOrientationChangeDCApp
import src.wrapperutil.utilities.ColorPicker

class ImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ImageView(context, attrs, defStyleAttr) {

    // var borderColor:String?=DCColorPicker.SEPERATOR_COLOR
    var stroke_width: Int = 0
    var isBorder: Boolean = false

    private var onOrientationChangeDCApp: OnOrientationChangeDCApp? = null

    private var TAG = ImageView::class.java.simpleName

    init {

        val array = context.obtainStyledAttributes(attrs, R.styleable.AttrImageView)
        // borderColor = array.getString(R.styleable.AttrImageView_border)
        stroke_width = array.getDimensionPixelOffset(R.styleable.AttrImageView_stroke_width, context.resources.getDimensionPixelOffset(R.dimen._1dp))
        isBorder = array.getBoolean(R.styleable.AttrImageView_isBorder, false)

        // Log.e(TAG,"isBorder"+isBorder)

        if (isBorder) {
            imageborder()
        }
        array.recycle()
    }

/*
    fun setImageButtonEnabled(ctxt: Context, enabled: Boolean,
                              item: ImageView, iconResId: Int) {

        item.isEnabled = enabled
        val originalIcon = ctxt.resources.getDrawable(iconResId)
        val icon = if (enabled) originalIcon else convertDrawableToGrayScale(originalIcon)
        setImageDrawable(icon)
    }


    fun convertDrawableToGrayScale(drawable: Drawable?): Drawable? {
        if (drawable == null)
            return null

        val res = drawable.mutate()
        res.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)
        return res
    }
*/

    fun imageborder() {
        background = ColorDrawable(Color.parseColor(ColorPicker.SEPERATOR_COLOR))
        setPadding(stroke_width, stroke_width, stroke_width, stroke_width)
        cropToPadding = true
       /* val gradientDrawable=GradientDrawable()
        gradientDrawable.setStroke(stroke_width,Color.parseColor(DCColorPicker.SEPERATOR_COLOR))*/
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.e(TAG, "onConfigurationChanged called" + newConfig?.orientation)
        onOrientationChangeDCApp?.orientationChange(newConfig?.orientation!!)
    }

    fun setOrientationChangeListner(onOrientationChangeDCApp: OnOrientationChangeDCApp) {
        this.onOrientationChangeDCApp = onOrientationChangeDCApp
    }
}
