package src.wrapperutil.uicomponent

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView

class CardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    androidx.cardview.widget.CardView(context, attrs, defStyleAttr) {
    private val TAG = CardView::class.java.simpleName
//    val cornerRadius: Float
//    val stokeWidth: Int
//    val border: GradientDrawable

    init {
//        val array = context.obtainStyledAttributes(attrs, R.styleable.DCShape, 0, 0)
//        cornerRadius = array.getDimension(R.styleable.DCShape_corner_radius, 0f)
//        stokeWidth = array.getDimensionPixelOffset(R.styleable.DCShape_stroke_width, 0)
//        border = GradientDrawable()
//        border.setColor(Color.parseColor(DCColorPicker.WHITE))
//        border.cornerRadius = cornerRadius
//        border.setStroke(stokeWidth, Color.parseColor(DCColorPicker.GRAY_50))
//        background = border
//        array.recycle()
    }

//    fun updateViewColor(backgroundColor: Int, stokeColor: Int) {
//        border.setColor(backgroundColor)
//        border.setStroke(stokeWidth, stokeColor)
//    }
}
