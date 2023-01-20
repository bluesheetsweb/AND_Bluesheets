package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.annotation.Nullable
import src.wrapperutil.utilities.ColorPicker

class CircularProgressView(context: Context, @Nullable attrs: AttributeSet) : View(context, attrs) {
    private var strokeWidth: Float
    private val totalPaint: Paint
    private val progressPaint: Paint
    private var progress = 0f
    private var total = 360f

    companion object {
        private val TAG = CircularProgressView::class.java.simpleName
    }

    init {

        strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, context.getResources().getDisplayMetrics())

        totalPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        totalPaint.setStyle(Paint.Style.STROKE)
        totalPaint.setColor(Color.parseColor(ColorPicker.SEPERATOR_COLOR))
        totalPaint.setStrokeWidth(strokeWidth)

        progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        progressPaint.setStyle(Paint.Style.STROKE)
        progressPaint.setColor(Color.parseColor(ColorPicker.SECONDARY))
        progressPaint.setStrokeWidth(strokeWidth)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()

        drawProgress(canvas, 360f.toInt(), totalPaint)
        if (total != 0f && progress != 0f)
            drawProgress(canvas, if (total == progress) 360 else (360f / total * progress).toInt(), progressPaint)

        Log.e(TAG, "onDraw progress" + progress)
        Log.e(TAG, "onDraw total" + total)

        canvas.restore()
    }

    private fun drawProgress(canvas: Canvas, total: Int, paint: Paint) {
        canvas.drawArc(RectF(strokeWidth, strokeWidth, getWidth() - strokeWidth, getHeight() - strokeWidth), -90f, total.toFloat(), false, paint)
    }

    fun setProgress(progress: Int) {
        this.progress = progress.toFloat()
        this.progress = progress * 3.6f
        Log.e(TAG, "progress" + progress)
        invalidate()
    }

    fun setProgressColor(rgb: Int) {
        progressPaint.setColor(rgb)
        invalidate()
    }

    fun setStokeWidth(value: Float) {
        strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics())
        totalPaint.setStrokeWidth(strokeWidth)
        progressPaint.setStrokeWidth(strokeWidth)
        invalidate()
    }

    /*fun setTotal(total: Int) {
        this.total = total.toFloat()
        if (textView != null)
            textView!!.text = total.toString()
        invalidate()
    }

    fun setTotalTextView(view: TextView) {
        this.textView = view
        textView!!.text = total.toInt().toString()
    }*/
}
