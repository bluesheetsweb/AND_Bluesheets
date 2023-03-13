package com.bluesheets.utils

object FragmentConstant {
    //Activity
    const val SIGN_UP_ACTIVITY = 1
    const val HOME_ACTIVITY = 2
    const val CHAT_ACTIVITY = 3
    const val USER_INFO_ACTIVITY = 4
    const val CHAT_OTHER_ACTIVITY = 5
    const val SWITCH_ORG_N_WORK_ACTIVITY = 6

    //Fragments
    const val GET_STARTED_FRAGMENT = 1
    const val SIGN_IN_FRAGMENT = 2
    const val SIGN_UP_FRAGMENT = 3
    const val CREATE_WORKSPACE_FRAGMENT = 3
    const val CREATE_ORGANISATION_FRAGMENT = 4
    const val CHAT_MESSAGE_FRAGMENT = 10
    const val CHAT_INFO_FRAGMENT = 11
    const val CHAT_ADD_MORE_FRAGMENT = 12
    const val CREATE_CHANNEL_FRAGMENT = 13

    const val FRAGMENT_TYPE = "fragmentType"
    const val ACTIVITY_BUNDLE = "bundle"
}

object AppGlobalConstant {
    const val RUPEE_SIGN = "₹"
    const val CHANNEL_TYPE_GROUP = "group"
    const val CHANNEL_TYPE_ONE_O_ONE = "oneToOne"
}

object ApiMethods {
    // OnBoarding Related APIs
    const val LOGIN = "customUsers/login"
    const val REGISTER = "customUsers/register"
    const val VERIFY_OTP = "customUsers/verifyUser"
    const val INVITE_CODE = "invites/acceptWithCode"

    // Users Info Related APIs
    const val USER_INFO = "customUsers/"
    const val USER_FORGOT_PASSWORD = "customUsers/sendResetPasswordLetter"
    const val USER_DELETE_ACCOUNT = "customUsers/delete-account-request"
    const val USER_PROFILE_IMAGE = "customUsers/profile-image"
    const val USER_LOGOUT = "customUsers/logout"

    // WorkSpace Related API
    const val CREATE_WORKSPACE = "workspaces"
    const val GET_WORKSPACE = "workspaces/getWorkspacesForUser"
    const val LOGIN_TO_WORKSPACE = "workspaces/loginToWorkspace"
    const val WORKSPACE_LOGO = "workspaces/uploadWorkspaceLogo"

    // Organizations Related API
    const val CREATE_ORGANIZATION = "organizations"
    const val GET_ORGANISATIONS_FOR_WORKSPACE = "organizations"
    const val LOGIN_TO_ORGANISATIONS = "organizations/login"

    // Documents Related API
    const val GET_DOCUMENTS = "documents"

    //Get Users
    const val GET_USERS = "workspaces/contacts"

    //Get Org and Tags
    const val GET_ORG_TAGS = "workspaces/available-tags/"

    // Upload Related APIs
    const val DOCUMENT_UPLOAD_FILES = "documents/uploadFiles"
    const val GET = "mobileChannels/"

}