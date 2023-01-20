package src.wrapperutil.uicomponent;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class WrapContentLinearLayoutManager extends LinearLayoutManager {
    //... constructor


    private static final  String TAG="WrapContentLinearLayout";

    public WrapContentLinearLayoutManager(Context context){
        super(context);
    }
    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout){
        super(context,orientation,reverseLayout);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }



    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            //Log.e(TAG,"onLayoutChildren called");
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, "meet a IOOBE in onLayoutChildren");
        }catch (Exception ex){
            Log.e(TAG, "meet a exception in onLayoutChildren");
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
           // Log.e(TAG,"scrollVerticallyBy called");
            return super.scrollVerticallyBy(dy, recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, "meet a IOOBE in scrollVerticallyBy");
        }catch (Exception ex){
            Log.e(TAG, "meet a exception in scrollVerticallyBy");
        }
        return 0;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            //Log.e(TAG,"scrollHorizontallyBy called");
            return super.scrollHorizontallyBy(dx, recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, "meet a IOOBE in scrollHorizontallyBy");
        }catch (Exception ex){
            Log.e(TAG, "meet a exception in scrollHorizontallyBy");
        }
        return 0;
    }

    @Override
    public View onFocusSearchFailed(View focused, int focusDirection, RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
           // Log.e(TAG,"onFocusSearchFailed called");
            return super.onFocusSearchFailed(focused, focusDirection, recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, "meet a IOOBE in onFocusSearchFailed");
        }catch (Exception ex){
            Log.e(TAG, "meet a exception in onFocusSearchFailed");
        }
       return  null;
    }
}