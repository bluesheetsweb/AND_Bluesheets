package src.wrapperutil.rguicomponent.interfaces

import android.app.Activity
import android.app.Application
import android.os.Bundle

class RGUIActivityCallbacks : Application.ActivityLifecycleCallbacks {

    companion object {
        lateinit var mActivity: Activity
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityDestroyed(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        mActivity = p0
    }

    override fun onActivityResumed(p0: Activity) {
        mActivity = p0
    }
}
