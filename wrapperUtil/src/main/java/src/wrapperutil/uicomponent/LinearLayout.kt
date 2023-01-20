package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import src.wrapperutil.R
import src.wrapperutil.utilities.ColorPicker
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

class LinearLayout
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {
    private val TAG = LinearLayout::class.java.simpleName
    val cornerRadius: Float
    val stokeWidth: Int
    val backgroundType: Int
    val strokeColor: Int
    val cornerType: Int
    val isTransparentBackground: Boolean
    val border: GradientDrawable

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.Shape, 0, 0)
        cornerRadius = array.getDimension(R.styleable.Shape_corner_radius, 0f)
        stokeWidth = array.getDimensionPixelOffset(R.styleable.Shape_stroke_width, 0)
        backgroundType = array.getInt(R.styleable.Shape_background_state, 0)
        strokeColor = array.getInt(R.styleable.Shape_stroke_color, 0)
        cornerType = array.getInt(R.styleable.Shape_corner_type, WrapperConstant.CORNER_ALL)

        isTransparentBackground = array.getBoolean(R.styleable.Shape_transparent_background, false)
        border = GradientDrawable()

        background = border

        /*if (isTransparentBackground){
            border.setColor(Color.parseColor(DCColorPicker.TRANSPARENT))
        }else{

        }*/

//        Log.e(TAG, "cornerType" + cornerType)
//        Log.e(TAG, "cornerRadius" + cornerRadius)
//        Log.e(TAG, "stokeWidth" + stokeWidth)
        // Log.e(TAG, "strokeColor" + strokeColor)
        try {
            /* when(strokeColor){
                 DCConstant.COLOR_SECONDARY->{
                     border.setStroke(stokeWidth, Color.parseColor(DCColorPicker.SECONDARY))
                 }else->{
                 border.setStroke(stokeWidth, Color.parseColor(DCColorPicker.SEPERATOR_COLOR)) }

             }*/
            setStroke(WrapperEnumAnnotation(strokeColor))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        setBorder(WrapperEnumAnnotation(cornerType))
        setBackground(WrapperEnumAnnotation(backgroundType))
//        isClickable = true
//        setStroke(DCEnumAnnotation(strokeColor))
//        background = border
        // border.setColor(Color.parseColor(DCColorPicker.BLACK))
        array.recycle()
    }

    fun setBorder(mode: WrapperEnumAnnotation) {
        @WrapperEnumAnnotation.CornerType val type = mode.state
        when (type) {
            WrapperConstant.CORNER_ALL -> {
                border.cornerRadius = cornerRadius
            }
            WrapperConstant.CORNER_SKIP_TOP_LEFT -> {
                Log.e(TAG, "CORNER_SKIP_TOP_LEFT")
                // border.cornerRadii = floatArrayOf( 0f, cornerRadius, cornerRadius, cornerRadius)
                border.cornerRadii = floatArrayOf(
                    0f,
                    0f,
                    cornerRadius,
                    cornerRadius,
                    cornerRadius,
                    cornerRadius,
                    cornerRadius,
                    cornerRadius
                )
            }
            WrapperConstant.CORNER_SKIP_TOP_RIGHT -> {
                Log.e(TAG, "CORNER_SKIP_TOP_RIGHT")
                border.cornerRadii = floatArrayOf(
                    cornerRadius,
                    cornerRadius,
                    0f,
                    0f,
                    cornerRadius,
                    cornerRadius,
                    cornerRadius,
                    cornerRadius
                )

                // border.cornerRadii = floatArrayOf( 0f, cornerRadius, cornerRadius, cornerRadius)
            }
            WrapperConstant.CORNER_SKIP_BOTTOM_RIGHT -> {
                Log.e(TAG, "CORNER_SKIP_BOTTOM_RIGHT")
                border.cornerRadii = floatArrayOf(
                    cornerRadius,
                    cornerRadius,
                    cornerRadius,
                    cornerRadius,
                    0f,
                    0f,
                    cornerRadius,
                    cornerRadius
                )
            }
            WrapperConstant.CORNER_NONE -> {
                Log.e(TAG, "CORNER_SKIP_BOTTOM_RIGHT")
                border.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
            }
            WrapperConstant.CORNER_ONLY_BOTTOM -> {
                Log.e(TAG, "CORNER_SKIP_BOTTOM_RIGHT")
                border.cornerRadii = floatArrayOf(
                    0f,
                    0f,
                    0f,
                    0f,
                    cornerRadius,
                    cornerRadius,
                    cornerRadius,
                    cornerRadius
                )
            }
            else -> {
                Log.e(TAG, "mode else ")
                border.cornerRadius = cornerRadius
            }
        }
    }

    fun setBackground(mode: WrapperEnumAnnotation) {
        @WrapperEnumAnnotation.BackgroundType val type = mode.state
        when (type) {
            WrapperConstant.BACKGROUND_BLACK -> border.setColor(Color.parseColor(ColorPicker.BLACK))
            WrapperConstant.BACKGROUND_BLACK_PURE -> border.setColor(Color.parseColor(ColorPicker.BLACK_PURE))
            WrapperConstant.BACKGROUND_WHITE -> border.setColor(Color.parseColor(ColorPicker.WHITE))
            WrapperConstant.BACKGROUND_PRIMARY -> border.setColor(Color.parseColor(ColorPicker.PRIMARY))
            WrapperConstant.BACKGROUND_SECONDARY -> border.setColor(Color.parseColor(ColorPicker.SECONDARY))
            WrapperConstant.BACKGROUND_GRAY_25 -> border.setColor(Color.parseColor(ColorPicker.GRAY_25))
            WrapperConstant.BACKGROUND_GRAY_50 -> border.setColor(Color.parseColor(ColorPicker.GRAY_50))
            WrapperConstant.BACKGROUND_GRAY_75 -> border.setColor(Color.parseColor(ColorPicker.GRAY_75))
            WrapperConstant.BACKGROUND_GRAY_LIGHT -> border.setColor(Color.parseColor(ColorPicker.GRAY_LIGHT))
            WrapperConstant.BACKGROUND_TRANSPARENT -> border.setColor(Color.parseColor(ColorPicker.TRANSPARENT))
            WrapperConstant.BACKGROUND_TRANSPARENT_BLACK -> border.setColor(
                Color.parseColor(
                    ColorPicker.TRANSPARENT_BLACK
                )
            )
            WrapperConstant.BACKGROUND_ERROR -> border.setColor(Color.parseColor(ColorPicker.ERROR))
            WrapperConstant.BACKGROUND_TRANSPARENT_WHITE -> border.setColor(
                Color.parseColor(
                    ColorPicker.TRANSPARENT_WHITE
                )
            )
            WrapperConstant.BACKGROUND_SEPERATOR_COLOR -> border.setColor(
                Color.parseColor(
                    ColorPicker.SEPERATOR_COLOR
                )
            )
            WrapperConstant.BACKGROUND_TRANSPARENT_SECONDARY -> border.setColor(
                Color.parseColor(
                    ColorPicker.TRANSPARENT_SECONDARY
                )
            )
            WrapperConstant.BACKGROUND_TRANSPARENT_WHITE_90 -> border.setColor(
                Color.parseColor(
                    ColorPicker.TRANSPARENT_WHITE_90
                )
            )
            WrapperConstant.BACKGROUND_SEPERATOR_COLOR_TWO -> border.setColor(
                Color.parseColor(
                    ColorPicker.SEPERATOR_COLOR_TWO
                )
            )
            WrapperConstant.BACKGROUND_GREEN_DARK -> border.setColor(Color.parseColor(ColorPicker.GREEN_DARK))
            WrapperConstant.BACKGROUND_BLACK_TRANSPARENT_80 -> border.setColor(
                Color.parseColor(
                    ColorPicker.BLACK_TRANSPARENT_80
                )
            )
            WrapperConstant.BACKGROUND_GRAY_PALE -> border.setColor(Color.parseColor(ColorPicker.GRAY_PALE))

            else -> {
                border.setColor(Color.parseColor(ColorPicker.TRANSPARENT))
            }
        }
    }

    fun setStroke(mode: WrapperEnumAnnotation) {
        @WrapperEnumAnnotation.BackgroundType val type = mode.state
        when (type) {
            WrapperConstant.BACKGROUND_BLACK -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.BLACK)
            )
            WrapperConstant.BACKGROUND_WHITE -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.WHITE)
            )
            WrapperConstant.BACKGROUND_PRIMARY -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.PRIMARY)
            )
            WrapperConstant.BACKGROUND_SECONDARY -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.SECONDARY)
            )
            WrapperConstant.BACKGROUND_GRAY_25 -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.GRAY_25)
            )
            WrapperConstant.BACKGROUND_GRAY_50 -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.GRAY_50)
            )
            WrapperConstant.BACKGROUND_GRAY_75 -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.GRAY_75)
            )
            WrapperConstant.BACKGROUND_GRAY_LIGHT -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.GRAY_LIGHT)
            )
            WrapperConstant.BACKGROUND_TRANSPARENT -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.TRANSPARENT)
            )
            WrapperConstant.BACKGROUND_TRANSPARENT_BLACK -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.TRANSPARENT_BLACK)
            )
            WrapperConstant.BACKGROUND_ERROR -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.ERROR)
            )
            WrapperConstant.BACKGROUND_SEPERATOR_COLOR -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.SEPERATOR_COLOR)
            )
            WrapperConstant.BACKGROUND_TRANSPARENT_SECONDARY -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.TRANSPARENT_SECONDARY)
            )
            WrapperConstant.BACKGROUND_GREEN_DARK -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.GREEN_DARK)
            )
            WrapperConstant.BACKGROUND_BLACK_TRANSPARENT_80 -> border.setStroke(
                stokeWidth,
                Color.parseColor(ColorPicker.BLACK_TRANSPARENT_80)
            )
            WrapperConstant.BACKGROUND_GRAY_PALE -> border.setColor(Color.parseColor(ColorPicker.GRAY_PALE))
            else -> {
                border.setStroke(stokeWidth, Color.parseColor(ColorPicker.SEPERATOR_COLOR))
                // border.setColor(Color.parseColor(DCColorPicker.TRANSPARENT))
            }
        }
    }

    fun updateViewColor(
        backgroundColor: Int,
        stokeColor: Int = Color.parseColor(ColorPicker.SEPERATOR_COLOR)
    ) {
        border.setColor(backgroundColor)
        border.setStroke(stokeWidth, stokeColor)
    }

    fun updateStokeColor(stokeColor: Int = Color.parseColor(ColorPicker.SEPERATOR_COLOR)) {
        border.setStroke(stokeWidth, stokeColor)
    }

    fun setShadow() {
        // setBackground(DCViewUtil.generateBackgroundWithShadow(this,))
        Log.e(TAG, "setShadow called")
        setBackground(
            ViewUtil.generateBackgroundWithShadow(
                this, R.color.white,
                R.dimen._1dp, R.color.shadow_color, R.dimen._5dp, Gravity.TOP
            )
        )
    }
}
