package com.bluesheets.utils

object FragmentConstant {
    //Activity
    const val SIGN_UP_ACTIVITY = 1
    const val HOME_ACTIVITY = 2



    //Fragments
    const val GET_STARTED_FRAGMENT = 1
    const val SIGN_IN_FRAGMENT = 2
    const val SIGN_UP_FRAGMENT = 3
    const val CREATE_WORKSPACE_FRAGMENT = 3
    const val CREATE_ORGANISATION_FRAGMENT = 4


    const val FRAGMENT_TYPE = "fragmentType"
    const val ACTIVITY_BUNDLE = "bundle"
}

object ApiMethods {
    const val LOGIN = "customUsers/login"
    const val REGISTER = "customUsers/register"
    const val CREATE_ORGANIZATION = "organizations"
    const val CREATE_WORKSPACE = "workspaces"
    const val VERIFY_OTP = "customUsers/verifyUser"
    const val LOGOUT = "customUsers/logout"
    const val DELETE_ACCOUNT = "customUsers/delete-account-request"
    const val PROFILE_IMAGE = "customUsers/profile-image"
    const val GET_WORKSPACE = "workspaces/getWorkspacesForUser"
    const val GET_ORGANISATIONS_FOR_WORKSPACE = "organizations"
    const val LOGIN_TO_WORKSPACE = "workspaces/loginToWorkspace"
    const val LOGIN_TO_ORGANISATIONS = "organizations/login"
    const val GET_DOCUMENTS = "documents"
    const val USER_INFO = "customUsers/"
    const val WORKSPACE_LOGO = "workspaces/uploadWorkspaceLogo"


}