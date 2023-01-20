package src.wrapperutil.uicomponent

import android.content.Context
import android.util.AttributeSet
import android.widget.Switch

class Switch : Switch {

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}
}
