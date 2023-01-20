package src.wrapperutil.uicomponent

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import src.wrapperutil.R
import src.wrapperutil.utilities.ColorPicker

class ProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ProgressBar(context, attrs, defStyleAttr) {

    companion object {
        val TAG = ProgressBar::class.java.simpleName
    }

    init {
        initView()
    }

    private fun initView() {
        Log.e(TAG, "initview")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.e(TAG, "if initview")
            progressTintList = ColorStateList.valueOf(Color.parseColor(ColorPicker.SECONDARY))
            progressBackgroundTintList =
                ColorStateList.valueOf(Color.parseColor(ColorPicker.SEPERATOR_COLOR))
        } else {
            // getProgressDrawable().setColorFilter(Color.parseColor(DCColorPicker.SECONDARY), android.graphics.PorterDuff.Mode.SRC_IN);

            // getIndeterminateDrawable().setColorFilter(Color.parseColor(DCColorPicker.SEPERATOR_COLOR), PorterDuff.Mode.SRC_ATOP);
            // setBackgroundColor(Color.parseColor(DCColorPicker.SEPERATOR_COLOR))
            /*TODO will change this code later for below lolipop devices*/
            progressDrawable =
                ContextCompat.getDrawable(context, R.drawable.dc_progressbar_background)

            // ColorDrawable(R.drawable.dc_progressbar_background)
        }
        // background=ColorDrawable(Color.parseColor(DCColorPicker.SEPERATOR_COLOR))
        // progressDrawable=ColorDrawable(Color.parseColor(DCColorPicker.SECONDARY))

        // getIndeterminateDrawable().setColorFilter(Color.parseColor(DCColorPicker.SEPERATOR_COLOR), PorterDuff.Mode.SRC_IN);
        // getProgressDrawable().setColorFilter(Color.parseColor(DCColorPicker.SEPERATOR_COLOR), PorterDuff.Mode.SRC_IN);
    }
}
