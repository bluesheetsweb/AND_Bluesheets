package src.wrapperutil.uicomponent

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatRadioButton

class RadioButton : AppCompatRadioButton {

    constructor(context: Context) : super(context) {
        initData()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initData()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initData()
    }

    fun initData() {
    }

//    override fun setChecked(t: Boolean) {
//        if (t) {
//            buttonDrawable = null
////            this.setBackgroundResource(R.drawable.ic_toggle_tick)
//        } else {
//            buttonDrawable = null
////            this.setBackgroundResource(R.drawable.ic_blank_toggle)
//        }
//        super.setChecked(t)
//    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val desiredWidth = 30
        val desiredHeight = 30

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        // Measure Width
        if (widthMode == View.MeasureSpec.EXACTLY) {
            // Must be this size
            width = widthSize
        } else if (widthMode == View.MeasureSpec.AT_MOST) {
            // Can't be bigger than...
            width = Math.min(desiredWidth, widthSize)
        } else {
            // Be whatever you want
            width = desiredWidth
        }

        // Measure Height
        if (heightMode == View.MeasureSpec.EXACTLY) {
            // Must be this size
            height = heightSize
        } else if (heightMode == View.MeasureSpec.AT_MOST) {
            // Can't be bigger than...
            height = Math.min(desiredHeight, heightSize)
        } else {
            // Be whatever you want
            height = desiredHeight
        }

        // MUST CALL THIS
        setMeasuredDimension(width, height)
    }
}
