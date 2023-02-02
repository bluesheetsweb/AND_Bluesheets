package com.bluesheets

import android.app.Application
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
        InitNetworkUtils.server_path = "https://dev.bluesheets.io/backend/api/"
        SharedPrefUtils.INSTANCE.initContext(this)
    }

    companion object {
        lateinit var instance: BluesheetApplication
            private set
    }
}