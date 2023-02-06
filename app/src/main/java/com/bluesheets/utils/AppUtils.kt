package com.bluesheets.utils

import android.text.TextUtils

object AppUtils {
    fun isValidNameLength(password: String) : Boolean {
        return if (TextUtils.isEmpty(password)) {
            return false
        } else {
            return password.trim().length >= 3
        }
    }
    fun isValidEmail(email: String) : Boolean {
        return if (TextUtils.isEmpty(email)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    fun isValidPasswordLength(password: String) : Boolean {
        return if (TextUtils.isEmpty(password)) {
            return false
        } else {
            return password.trim().length >= 6
        }
    }

    fun isValidMobileLength(mobile: String) : Boolean {
        return if (TextUtils.isEmpty(mobile)) {
            return false
        } else {
            return mobile.trim().length >= 10
        }
    }

    fun isValidString(value: String?): Boolean {
        if (value.isNullOrEmpty() || value.isNullOrBlank())
            return false
        return true
    }
}