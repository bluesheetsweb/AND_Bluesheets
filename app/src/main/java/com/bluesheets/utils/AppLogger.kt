package com.bluesheets.utils

import android.util.Log
import com.bluesheets.BuildConfig
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.Exception

/**
 * One of the way to make your application secure is to disable the log in the release apk.
 * Use this class instead of simple Log class.
 *
 *
 * I'm using ON_DEBUG_MODE, and I have defined this boolean in gradle file.
 * In debug build it is true, so Log will be printed,
 * but in the release build I made that to be false.
 */
@Module
@InstallIn(SingletonComponent::class)

object AppLogger {
    fun e(tag: String?, msg: String?) {
        if (!BuildConfig.ON_DEBUG_MODE) return
        if (msg == null) return
        if (msg.length > 4000) {
            Log.e(tag, msg.substring(0, 4000))
            e("", "" + checkInstanceOfException(msg.substring(4000)))
        } else Log.e(tag, "" + checkInstanceOfException(msg))
    }

    fun i(tag: String?, msg: String?) {
        if (!BuildConfig.ON_DEBUG_MODE) return
        if (msg == null) return
        if (msg.length > 4000) {
            Log.i(tag, msg.substring(0, 4000))
            e("", "" + checkInstanceOfException(msg.substring(4000)))
        } else Log.i(tag, "" + checkInstanceOfException(msg))
    }

    fun d(tag: String?, msg: String?) {
        if (!BuildConfig.ON_DEBUG_MODE) return
        if (msg == null) return
        if (msg.length > 4000) {
            Log.d(tag, msg.substring(0, 4000))
            e("", "" + checkInstanceOfException(msg.substring(4000)))
        } else Log.d(tag, "" + checkInstanceOfException(msg))
    }

    fun w(tag: String?, msg: String?) {
        if (!BuildConfig.ON_DEBUG_MODE) return
        if (msg == null) return
        if (msg.length > 4000) {
            Log.w(tag, msg.substring(0, 4000))
            e("", "" + checkInstanceOfException(msg.substring(4000)))
        } else Log.w(tag, "" + checkInstanceOfException(msg))
    }

    private fun checkInstanceOfException(msg: Any): Any {
        var msg = msg
        try {
            if (msg is Exception) {
                val errors = StringWriter()
                msg.printStackTrace(PrintWriter(errors))
                msg = errors.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return msg
    }
}
