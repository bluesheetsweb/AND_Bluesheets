package com.bluesheets.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPrefUtils private constructor() {

        var context: Context? = null
        val TAG = SharedPrefUtils::class.java.simpleName

        companion object {
            val INSTANCE: SharedPrefUtils by lazy { SharedPrefUtils() }
        }

        fun initContext(context: Context?) {
            Log.e(TAG, " initContext called " + context)
            if (context != null) {
                this.context = context
            }
        }

        val preferences: SharedPreferences
            get() =
                context!!.getSharedPreferences("Bluesheet", Context.MODE_PRIVATE)

        fun savePreferences(key: String, value: String?) {
            if (preferences == null)
                return
            val prefs = preferences
            val editor = prefs.edit()
            editor.putString(key, value)
            editor.commit()
        }

    fun saveBoolToPreferences(key: String, value: Boolean?) {
        if (preferences == null)
            return
        value?.let {
            val prefs = preferences
            val editor = prefs.edit()
            editor.putBoolean(key, it)
            editor.commit()
        }
    }

        fun getFromPreferences(key: String): String {
            if (preferences == null)
                return ""
            val prefs = preferences
            return try {
                val value = prefs.getString(key, "")
                value.toString()
            } catch (e: Throwable) {
                e.printStackTrace()
                ""
            }
        }

        fun getFromPreferencesLongval(key: String): Long {
            if (preferences == null)
                return 0
            val prefs = preferences
            val value = prefs.getLong(key, 0)
            return if (value == 0L) {
                0L
            } else value
        }

    fun getFromPreferencesBoolval(key: String): Boolean {
        if (preferences == null)
            return false
        val prefs = preferences
        val value = prefs.getBoolean(key, false)
        return value
    }

    }