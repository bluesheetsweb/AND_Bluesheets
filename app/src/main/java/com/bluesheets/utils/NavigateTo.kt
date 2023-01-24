package com.bluesheets.utils

import android.content.Intent
import android.os.Bundle
import com.bluesheets.BluesheetApplication
import com.bluesheets.ui.signup.view.SignUpActivity

object NavigateTo {

    fun screen(activityType: Int, fragmentType: Int, bundle: Bundle? = null){
        when (activityType) {
            FragmentConstant.SIGN_UP_ACTIVITY ->
            {
                toSignUp(fragmentType, bundle)
            }
        }
    }

    private fun toSignUp(fragmentType: Int, bundle: Bundle?) {
        val myIntent = Intent(BluesheetApplication.instance.activityLifeCycle.currentActivity, SignUpActivity::class.java)
        myIntent.putExtra(FragmentConstant.FRAGMENT_TYPE, fragmentType)
        myIntent.putExtra(FragmentConstant.ACTIVITY_BUNDLE, bundle)
        BluesheetApplication.instance.activityLifeCycle.currentActivity?.startActivity(myIntent)
    }
}