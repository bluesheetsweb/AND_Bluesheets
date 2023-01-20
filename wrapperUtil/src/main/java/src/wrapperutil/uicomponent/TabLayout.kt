package src.wrapperutil.uicomponent

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.tabs.TabLayout

class TabLayout
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, var defStyleAttr: Int = 0) :
    TabLayout(context, attrs, defStyleAttr) {

    companion object {
        val TAG = "TabLayout"
    }

//    init {
//        Log.e(TAG, "init called")
////        setTabTextColors(Color.parseColor(ColorPicker.GRAY_75), Color.parseColor(ColorPicker.SECONDARY))
////        setSelectedTabIndicatorColor(Color.parseColor(ColorPicker.SECONDARY))
////        setSelectedTabIndicatorHeight(0)
//        // defStyleAttr=R.style.tabLayoutStyle
//    }

//    fun disableTabColor() {
////        setTabTextColors(Color.parseColor(ColorPicker.BLACK), Color.parseColor(ColorPicker.TRANSPARENT))
////        setSelectedTabIndicatorColor(Color.parseColor(ColorPicker.TRANSPARENT))
//    }
}
