package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import src.wrapperutil.R
import src.wrapperutil.utilities.ColorPicker
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

class Tag
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    TextView(context, attrs, defStyleAttr) {

    val tagMode: Int
    val gradientDrawable: GradientDrawable
    var cornerRadius: Int

    companion object {
        private val TAG = Tag::class.java.simpleName
    }

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.AttrTag, 0, 0)
        tagMode = array.getInt(R.styleable.AttrTag_tag_mode, 0)
        gradientDrawable = GradientDrawable()
        background = gradientDrawable
        updateMode(WrapperEnumAnnotation(tagMode))
        gravity = Gravity.CENTER
        cornerRadius = 0
        array.recycle()
    }

    fun updateMode(mode: WrapperEnumAnnotation) {
        @WrapperEnumAnnotation.TagMode val status = mode.state
        Log.e(TAG, "status" + status)
        when (status) {
            WrapperConstant.TAG_MODE_GREY -> {
                var gradientDrawable = GradientDrawable()
                background = gradientDrawable
                cornerRadius = resources.getDimensionPixelOffset(R.dimen._15dp)
                gradientDrawable.setColor(Color.parseColor(ColorPicker.GRAY_LIGHT))
                gradientDrawable.setStroke(
                    resources.getDimensionPixelOffset(R.dimen._1dp),
                    Color.parseColor(ColorPicker.GRAY_25)
                )
                setTextColor(Color.parseColor(ColorPicker.GRAY_25))
                var paddingLeftRight = resources.getDimensionPixelOffset(R.dimen._10dp)
                var paddingTopBottom = resources.getDimensionPixelOffset(R.dimen._5dp)
                setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom)
                isEnabled = true
                gradientDrawable.cornerRadius = cornerRadius.toFloat()
                Log.e(TAG, "status TAG_MODE_GREY")
            }
            WrapperConstant.TAG_MODE_GREY_75 -> {
                var gradientDrawable = GradientDrawable()
                background = gradientDrawable
                cornerRadius = resources.getDimensionPixelOffset(R.dimen._15dp)
                gradientDrawable.setColor(Color.parseColor(ColorPicker.GRAY_75))
                // gradientDrawable.setStroke(resources.getDimensionPixelOffset(R.dimen._1dp),Color.parseColor(DCColorPicker.GRAY_25))
                setTextColor(Color.parseColor(ColorPicker.WHITE))
                var paddingLeftRight = resources.getDimensionPixelOffset(R.dimen._10dp)
                var paddingTopBottom = resources.getDimensionPixelOffset(R.dimen._5dp)
                setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom)
                isEnabled = true
                gradientDrawable.cornerRadius = cornerRadius.toFloat()
                Log.e(TAG, "status TAG_MODE_GREY_75")
            }
            WrapperConstant.TAG_MODE_GREY_50 -> {
                var gradientDrawable = GradientDrawable()
                background = gradientDrawable
                cornerRadius = resources.getDimensionPixelOffset(R.dimen._15dp)
                gradientDrawable.setColor(Color.parseColor(ColorPicker.GRAY_LIGHT))
                // gradientDrawable.setStroke(resources.getDimensionPixelOffset(R.dimen._1dp),Color.parseColor(DCColorPicker.GRAY_25))
                setTextColor(Color.parseColor(ColorPicker.GRAY_75))
                var paddingLeftRight = resources.getDimensionPixelOffset(R.dimen._10dp)
                var paddingTopBottom = resources.getDimensionPixelOffset(R.dimen._5dp)
                setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom)
                isEnabled = true
                gradientDrawable.cornerRadius = cornerRadius.toFloat()
                Log.e(TAG, "status TAG_MODE_GREY_75")
            }

            WrapperConstant.TAG_MODE_WHITE -> {
                var gradientDrawable = GradientDrawable()
                background = gradientDrawable
                cornerRadius = resources.getDimensionPixelOffset(R.dimen._15dp)
                gradientDrawable.setColor(Color.parseColor(ColorPicker.WHITE))
                gradientDrawable.setStroke(
                    resources.getDimensionPixelOffset(R.dimen._1dp),
                    Color.parseColor(ColorPicker.GRAY_25)
                )
                setTextColor(Color.parseColor(ColorPicker.GRAY_25))
                var paddingLeftRight = resources.getDimensionPixelOffset(R.dimen._15dp)
                var paddingTopBottom = resources.getDimensionPixelOffset(R.dimen._5dp)
                setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom)
                isEnabled = true
                gradientDrawable.cornerRadius = cornerRadius.toFloat()
                Log.e(TAG, "status TAG_MODE_WHITE")
            }
            WrapperConstant.TAG_MODE_GREY_BORDER -> {
                var gradientDrawable = GradientDrawable()
                background = gradientDrawable
                cornerRadius = resources.getDimensionPixelOffset(R.dimen._15dp)
                gradientDrawable.setColor(Color.parseColor(ColorPicker.WHITE))
                gradientDrawable.setStroke(
                    resources.getDimensionPixelOffset(R.dimen._1dp),
                    Color.parseColor(ColorPicker.GRAY_75)
                )
                setTextColor(Color.parseColor(ColorPicker.GRAY_75))
                var paddingLeftRight = resources.getDimensionPixelOffset(R.dimen._15dp)
                var paddingTopBottom = resources.getDimensionPixelOffset(R.dimen._5dp)
                setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom)
                isEnabled = true
                gradientDrawable.cornerRadius = cornerRadius.toFloat()
                Log.e(TAG, "status TAG_MODE_WHITE")
            }
            WrapperConstant.TAG_MODE_YELLOW -> {
                var gradientDrawable = GradientDrawable()
                background = gradientDrawable
                cornerRadius = resources.getDimensionPixelOffset(R.dimen._15dp)
                gradientDrawable.setColor(Color.parseColor(ColorPicker.ERROR))
                setTextColor(Color.parseColor(ColorPicker.WHITE))
                isEnabled = true
                gradientDrawable.cornerRadius = cornerRadius.toFloat()
                Log.e(TAG, "status TAG_MODE_WHITE")
            }
            else -> {
                Log.e(TAG, "status else")
                background = gradientDrawable
            }
        }
    }

    fun dynamicColorBackground(
        backgroundColor: String,
        cornerRadius: Float = resources.getDimension(R.dimen._15dp),
        verticalPadding: Int = resources.getDimensionPixelOffset(R.dimen._4dp),
        horizontalPadding: Int = resources.getDimensionPixelOffset(R.dimen._15dp),
        textSize: Float = 14f
    ) {
        gradientDrawable.setColor(Color.parseColor(backgroundColor))
        setTextColor(Color.parseColor(ColorPicker.WHITE))
        setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        gradientDrawable.cornerRadius = cornerRadius
        setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
        background = gradientDrawable
    }
}
