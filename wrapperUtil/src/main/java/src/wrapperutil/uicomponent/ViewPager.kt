package src.wrapperutil.uicomponent

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager
import src.wrapperutil.R

class ViewPager : ViewPager {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    fun trimPagerFromBothSide(padding: Int = resources.getDimensionPixelOffset(R.dimen._15dp)) {
        clipToPadding = false
        setPadding(padding, 0, padding, 0)
    }

    fun addSpaceBetweenPages(margin: Int = resources.getDimensionPixelOffset(R.dimen._10dp)) {
        pageMargin = margin
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        var height = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            val h = child.measuredHeight
            if (h > height) height = h
        }
        if (height != 0) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}
