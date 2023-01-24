package com.bluesheets.utils

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.Log

class MyLifecycleHandler : ActivityLifecycleCallbacks {
    var currentActivity: Activity? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityDestroyed(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
        ++resumed
    }

    override fun onActivityPaused(activity: Activity) {
        ++paused
        Log.w("test", "application is in foreground: " + (resumed > paused))
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
        ++started
    }

    override fun onActivityStopped(activity: Activity) {
        ++stopped
        Log.w("test", "application is visible: " + (started > stopped))
    }

    companion object {
        private var resumed = 0
        private var paused = 0
        private var started = 0
        private var stopped = 0
        val isApplicationVisible: Boolean
            get() = started > stopped
        val isApplicationInForeground: Boolean
            get() = resumed > paused
    }
}