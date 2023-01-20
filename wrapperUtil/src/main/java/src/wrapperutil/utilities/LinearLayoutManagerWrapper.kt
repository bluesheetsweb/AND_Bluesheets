package src.wrapperutil.utilities

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class LinearLayoutManagerWrapper : androidx.recyclerview.widget.LinearLayoutManager {

    private val TAG = LinearLayoutManagerWrapper::class.java.simpleName

    constructor(context: Context) : super(context) {}

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {}

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.e(TAG, "meet a IOOBE in RecyclerView", e)
        } catch (e: Exception) {
            Log.w(TAG, "ex", e)
        }
    }
}
