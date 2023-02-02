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
}