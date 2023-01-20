package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import src.wrapperutil.utilities.ColorPicker
import src.wrapperutil.utilities.WrapperConstant.COLOR_BLACK
import src.wrapperutil.utilities.WrapperConstant.COLOR_BLACK_TRANSPARENT
import src.wrapperutil.utilities.WrapperConstant.COLOR_ERROR
import src.wrapperutil.utilities.WrapperConstant.COLOR_GRAY_25
import src.wrapperutil.utilities.WrapperConstant.COLOR_GRAY_50
import src.wrapperutil.utilities.WrapperConstant.COLOR_GRAY_75
import src.wrapperutil.utilities.WrapperConstant.COLOR_GRAY_LIGHT
import src.wrapperutil.utilities.WrapperConstant.COLOR_GREEN
import src.wrapperutil.utilities.WrapperConstant.COLOR_IMAGE
import src.wrapperutil.utilities.WrapperConstant.COLOR_PRIMARY
import src.wrapperutil.utilities.WrapperConstant.COLOR_RANDOM_1
import src.wrapperutil.utilities.WrapperConstant.COLOR_RANDOM_2
import src.wrapperutil.utilities.WrapperConstant.COLOR_RANDOM_3
import src.wrapperutil.utilities.WrapperConstant.COLOR_RANDOM_4
import src.wrapperutil.utilities.WrapperConstant.COLOR_RANDOM_5
import src.wrapperutil.utilities.WrapperConstant.COLOR_RANDOM_6
import src.wrapperutil.utilities.WrapperConstant.COLOR_SECONDARY
import src.wrapperutil.utilities.WrapperConstant.COLOR_WHITE

class Circle @kotlin.jvm.JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private var mCircle: Paint? = null
    private var strokePaint: Paint? = null

    private var mRadius: Float = 0.toFloat()
    private val mArcBounds = RectF()

    private val TAG = Circle::class.java.simpleName

    init {
        initPaints()
    }

    fun initPaints(style: Int? = 0) {
        // Log.e(TAG, "initPaints $style")
        when (style) {
            COLOR_GRAY_25 -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.GRAY_75)

                strokePaint = Paint()
                strokePaint?.style = Paint.Style.STROKE
                strokePaint?.color = Color.WHITE
                strokePaint?.strokeWidth = 0f
            }
            COLOR_SECONDARY -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.SECONDARY)

                strokePaint = Paint()
                strokePaint?.style = Paint.Style.STROKE
                strokePaint?.color = Color.WHITE
                strokePaint?.strokeWidth = 0f
            }
            COLOR_ERROR -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.ERROR)
            }
            COLOR_PRIMARY -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.PRIMARY)
            }
            COLOR_GRAY_50 -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.GRAY_50)
            }
            COLOR_GRAY_75 -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.GRAY_75)
            }
            COLOR_GRAY_LIGHT -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.GRAY_LIGHT)
            }
            COLOR_BLACK -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.BLACK)
            }
            COLOR_WHITE -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.WHITE)
            }
            COLOR_WHITE -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.WHITE)
            }
            COLOR_RANDOM_6 -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.RANDOM_COLOR_6)
            }
            COLOR_RANDOM_1 -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.RANDOM_COLOR_1)
            }
            COLOR_RANDOM_2 -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.RANDOM_COLOR_2)
            }
            COLOR_RANDOM_3 -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.RANDOM_COLOR_3)
            }
            COLOR_RANDOM_4 -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.RANDOM_COLOR_4)
            }
            COLOR_RANDOM_5 -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.RANDOM_COLOR_5)
            }
            COLOR_IMAGE -> {
                strokePaint = null
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.TRANSPARENT)
            }
            COLOR_BLACK_TRANSPARENT -> {
                strokePaint = null
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.TRANSPARENT_BLACK)
            }
            COLOR_GREEN -> {
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.GREEN)
            }
            else -> {
                strokePaint = null
                mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
                mCircle!!.style = Paint.Style.FILL
//        mCircle!!.strokeWidth = 15 * resources.displayMetrics.density
                mCircle!!.strokeCap = Paint.Cap.SQUARE
                mCircle!!.color = Color.parseColor(ColorPicker.TRANSPARENT)
            }
        }
        invalidate()
    }

    fun setBgColor(color: String) {
        mCircle = Paint(Paint.ANTI_ALIAS_FLAG)
        mCircle!!.style = Paint.Style.FILL

        mCircle!!.strokeCap = Paint.Cap.SQUARE
        mCircle!!.color = Color.parseColor(color)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRadius = Math.min(w, h) / 2f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)

        val size = Math.min(w, h)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val x = width / 2f
        val y = height / 2f
        canvas.drawCircle(x, y, mRadius, mCircle!!)
        // val widthf = (width/(1.4)).toFloat()
        if (strokePaint != null) {
            canvas.drawCircle(x, y, mRadius, strokePaint!!)
        }
    }
}
