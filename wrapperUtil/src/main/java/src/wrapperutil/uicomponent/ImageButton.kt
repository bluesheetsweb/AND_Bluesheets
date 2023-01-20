package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton

class ImageButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatImageButton(context, attrs, defStyleAttr) {

    init {
        setBackgroundColor(Color.TRANSPARENT)
    }

    fun updateBackgroundColor(color: Int) {
        setBackgroundColor(color)
    }
}
