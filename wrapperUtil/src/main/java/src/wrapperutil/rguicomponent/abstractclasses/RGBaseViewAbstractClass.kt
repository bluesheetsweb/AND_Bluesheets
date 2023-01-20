package src.wrapperutil.rguicomponent.abstractclasses

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

open class RGBaseViewAbstractClass(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    var title: String = ""
    var icon: String = ""
    var error: String = ""
}
