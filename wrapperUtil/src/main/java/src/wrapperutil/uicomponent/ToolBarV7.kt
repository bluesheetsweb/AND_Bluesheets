package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.Toolbar
import src.wrapperutil.R
import src.wrapperutil.utilities.ColorPicker

class ToolBarV7 @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    Toolbar(context, attrs, defStyleAttr) {

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.AttrToolBarV7, 0, 0)
        setBackgroundColor(Color.parseColor(ColorPicker.WHITE))
        setTitleTextColor(Color.parseColor(ColorPicker.BLACK))
        // popupTheme=R.style.ThemeOverlay.AppCompat.Light
        // popupTheme=android.R.style.ThemeOverlay.App.Light
        getContext().setTheme(R.style.MyToolbarStyle)
        array.recycle()
    }
}
