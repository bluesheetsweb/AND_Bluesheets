package src.wrapperutil.uicomponent

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class WebView : WebView {
    private var mOnScrollChangedCallback: OnScrollChangedCallback? = null

    init {
        setVerticalScrollBarEnabled(false)
        isHorizontalScrollBarEnabled = false
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override fun performClick(): Boolean {
        super.performClick()

        return true
    }

    private fun init() {
    }

    fun registerOnScrollChangedCallback(onScrollChangedCallback: OnScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (mOnScrollChangedCallback != null) mOnScrollChangedCallback?.onScroll(l, t, oldl, oldt)
    }

    interface OnScrollChangedCallback {
        fun onScroll(l: Int, t: Int, oldl: Int, oldt: Int)
    }
}
