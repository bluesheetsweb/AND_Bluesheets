package src.wrapperutil.uicomponent

import android.content.Context
import android.graphics.PointF
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView.SmoothScroller

class LinearLayoutManagerWithSmoothScroller : LinearLayoutManager {
    constructor(context: Context?) : super(context, VERTICAL, false) {}
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    ) {
    }

    fun smoothScrollToPosition(
        recyclerView: RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State?,
        position: Int
    ) {
        val smoothScroller: SmoothScroller = TopSnappedSmoothScroller(recyclerView.context)
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    private inner class TopSnappedSmoothScroller(context: Context?) :
        LinearSmoothScroller(context) {
        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            return this@LinearLayoutManagerWithSmoothScroller
                .computeScrollVectorForPosition(targetPosition)
        }

        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }
}
