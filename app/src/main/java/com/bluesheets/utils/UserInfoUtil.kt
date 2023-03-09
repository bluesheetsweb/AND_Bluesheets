package com.bluesheets.utils

import com.bluesheets.BuildConfig

object UserInfoUtil {
    var isLogin: Boolean?
    get() = SharedPrefUtils.INSTANCE.getFromPreferencesBoolval("IS_LOG_IN")
    set(value) {
        SharedPrefUtils.INSTANCE.saveBoolToPreferences("IS_LOG_IN", value)
    }

    var workSpaceId: String?
        get() = SharedPrefUtils.INSTANCE.getFromPreferences("WORKSPACE_ID")
        set(value) {
            SharedPrefUtils.INSTANCE.savePreferences("WORKSPACE_ID", value)
        }

    var workSpaceToken: String?
        get() = SharedPrefUtils.INSTANCE.getFromPreferences("WORKSPACE_TOKEN")
        set(value) {
            SharedPrefUtils.INSTANCE.savePreferences("WORKSPACE_TOKEN", value)
        }

    var workSpaceName: String?
        get() = SharedPrefUtils.INSTANCE.getFromPreferences("WORKSPACE_NAME")
        set(value) {
            SharedPrefUtils.INSTANCE.savePreferences("WORKSPACE_NAME", value)
        }

    var organizationName: String?
        get() = SharedPrefUtils.INSTANCE.getFromPreferences("ORGANIZATION_NAME")
        set(value) {
            SharedPrefUtils.INSTANCE.savePreferences("ORGANIZATION_NAME", value)
        }

    var organizationId: String?
        get() = SharedPrefUtils.INSTANCE.getFromPreferences("ORGANIZATION_ID")
        set(value) {
            SharedPrefUtils.INSTANCE.savePreferences("ORGANIZATION_ID", value)
        }

    var organizationToken: String?
        get() = SharedPrefUtils.INSTANCE.getFromPreferences("ORGANIZATION_TOKEN")
        set(value) {
            SharedPrefUtils.INSTANCE.savePreferences("ORGANIZATION_TOKEN", value)
        }

    var authToken: String?
        get() = SharedPrefUtils.INSTANCE.getFromPreferences("AUTH_TOKEN")
        set(value) {
            SharedPrefUtils.INSTANCE.savePreferences("AUTH_TOKEN", value)
        }

    var userId: String?
        get() = SharedPrefUtils.INSTANCE.getFromPreferences("USER_ID")
        set(value) {
            SharedPrefUtils.INSTANCE.savePreferences("USER_ID", value)
        }

    var userName: String?
        get() = SharedPrefUtils.INSTANCE.getFromPreferences("USER_NAME")
        set(value) {
            SharedPrefUtils.INSTANCE.savePreferences("USER_NAME", value)
        }

    var chatToken: String
        get() = SharedPrefUtils.INSTANCE.getFromPreferences("CHAT_TOKEN")
        set(value) {
            SharedPrefUtils.INSTANCE.savePreferences("CHAT_TOKEN", value)
        }

    var workSpaceLOGO: String?
        get() = SharedPrefUtils.INSTANCE.getFromPreferences("WORKSPACE_LOGO")
        set(value) {
            SharedPrefUtils.INSTANCE.savePreferences("WORKSPACE_LOGO", value)
        }

    var userProfileImage: String?
        get() = SharedPrefUtils.INSTANCE.getFromPreferences("USER_LOGO")
        set(value) {
            SharedPrefUtils.INSTANCE.savePreferences("USER_LOGO", value)
        }

    fun getChatId() : String {
        return "${BuildConfig.FLAVOR}-$userId"
    }
}