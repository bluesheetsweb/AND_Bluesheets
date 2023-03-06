package com.bluesheets

import android.app.Application
import android.content.ComponentCallbacks2
import com.bluesheets.utils.ChatSharedClient
import com.bluesheets.utils.MyLifecycleHandler
import com.bluesheets.utils.SharedPrefUtils
import src.networkutil.network.InitNetworkUtils

class BluesheetApplication: Application() {
    lateinit var activityLifeCycle: MyLifecycleHandler

    override fun onCreate() {
        super.onCreate()
        instance = this
        activityLifeCycle = MyLifecycleHandler()
        registerActivityLifecycleCallbacks(activityLifeCycle)
        initServer()
    }

    private fun initServer(){
        InitNetworkUtils.server_path = BuildConfig.BASE_URL
        SharedPrefUtils.INSTANCE.initContext(this)
        ChatSharedClient.INSTANCE.initContext(this)
    }

    companion object {
        lateinit var instance: BluesheetApplication
            private set
    }


    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        // Determine which lifecycle or system event was raised.
        when (level) {

            ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN -> {
                /*
                   Release any UI objects that currently hold memory.
                   The user interface has moved to the background.
                */
            }

            ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE,
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW,
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL -> {
                /*
                   Release any memory that your app doesn't need to run.

                   The device is running low on memory while the app is running.
                   The event raised indicates the severity of the memory-related event.
                   If the event is TRIM_MEMORY_RUNNING_CRITICAL, then the system will
                   begin killing background processes.
                */

                // Below method invokes the JVM to perform Garbage Collection,
                // if your app is eligible then it will perform GC.

                Runtime.getRuntime().gc()
            }

            ComponentCallbacks2.TRIM_MEMORY_BACKGROUND,
            ComponentCallbacks2.TRIM_MEMORY_MODERATE,
            ComponentCallbacks2.TRIM_MEMORY_COMPLETE -> {
                /*
                   Release as much memory as the process can.

                   The app is on the LRU list and the system is running low on memory.
                   The event raised indicates where the app sits within the LRU list.
                   If the event is TRIM_MEMORY_COMPLETE, the process will be one of
                   the first to be terminated.
                */
                /*
                    Since this is a critical situation, and your app will be in background.
                    Here we will be killing the process.
                    After relaunch of an app, the app will start from fresh, keeping intact
                    all the values of a SharedPrefrences.
                */
            }

            else -> {
                /*
                  Release any non-critical data structures.

                  The app received an unrecognized memory level value
                  from the system. Treat this as a generic low-memory message.
                */
            }
        }
    }
}