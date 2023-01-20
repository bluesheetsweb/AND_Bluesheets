package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.Log
import android.widget.SeekBar
import src.wrapperutil.utilities.ColorPicker

class SeekBar : SeekBar {

    companion object {
        val TAG = SeekBar::class.java.simpleName
    }

    constructor(context: Context) : super(context) {
        initView()
    }

    private fun initView() {
        Log.e(TAG, "initview")

        getProgressDrawable().setColorFilter(
            Color.parseColor(ColorPicker.PRIMARY),
            PorterDuff.Mode.SRC_ATOP
        )
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }
}
