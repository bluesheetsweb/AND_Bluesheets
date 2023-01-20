package src.wrapperutil.uicomponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

public class CustomNestedRecyclerView extends RecyclerView {
    public CustomNestedRecyclerView(Context context) {
        super(context);
    }

    public CustomNestedRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNestedRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(event);
    }

}
