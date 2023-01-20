package src.wrapperutil.uicomponent

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import src.wrapperutil.utilities.CustomItemAnimator

class RecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    androidx.recyclerview.widget.RecyclerView(context, attrs, defStyleAttr) {
    private val TAG = RecyclerView::class.java.simpleName
    //    val cornerRadius: Float
//    val stokeWidth: Int
    // val border: GradientDrawable

    init {
//        val array = context.obtainStyledAttributes(attrs, R.styleable.DCShape, 0, 0)
//        cornerRadius = array.getDimension(R.styleable.DCShape_corner_radius, 0f)
//        stokeWidth = array.getDimensionPixelOffset(R.styleable.DCShape_stroke_width, 0)
        // border = GradientDrawable()
        // border.setColor(Color.parseColor(DCColorPicker.GRAY_LIGHT))
        // background = border
//        array.recycle()

        prevnetBlinking()
    }

//    fun updateViewColor(backgroundColor: Int, stokeColor: Int) {
//        border.setColor(backgroundColor)
//        border.setStroke(stokeWidth, stokeColor)
//    }

    fun prevnetBlinking() {
        itemAnimator = CustomItemAnimator()
        (itemAnimator as CustomItemAnimator).supportsChangeAnimations = false
    }
}
