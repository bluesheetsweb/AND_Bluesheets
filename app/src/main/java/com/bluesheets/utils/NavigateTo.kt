package com.bluesheets.utils

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.bluesheets.BluesheetApplication
import com.bluesheets.SplashActivity
import com.bluesheets.ui.chat.view.ChatOtherActivity
import com.bluesheets.ui.chat.view.CustomMessageListActivity
import com.bluesheets.ui.home.view.HomeActivity
import com.bluesheets.ui.signup.view.SignUpActivity
import com.bluesheets.ui.switch_org_n_workspace.ui.SwitchOrgAndWorkActivity
import com.bluesheets.ui.user_info.view.UserInfoActivity

object NavigateTo {

    fun screen(activityType: Int, fragmentType: Int = 0, bundle: Bundle? = null) {
        when (activityType) {
            FragmentConstant.SIGN_UP_ACTIVITY -> {
                toSignUp(fragmentType, bundle)
            }
            FragmentConstant.HOME_ACTIVITY -> {
                toHome(bundle)
            }
            FragmentConstant.CHAT_ACTIVITY -> {
                toChat(bundle)
            }
            FragmentConstant.USER_INFO_ACTIVITY -> {
                toUserInfoScreen(bundle)
            }
            FragmentConstant.CHAT_OTHER_ACTIVITY -> {
                toOtherChat(fragmentType, bundle)
            }
            FragmentConstant.SWITCH_ORG_N_WORK_ACTIVITY -> {
                toSwitchOrgAndWorkSpace(bundle)
            }
        }
    }

    private fun toSignUp(fragmentType: Int, bundle: Bundle?) {
        val myIntent = Intent(
            BluesheetApplication.instance.activityLifeCycle.currentActivity,
            SignUpActivity::class.java
        )
        myIntent.putExtra(FragmentConstant.FRAGMENT_TYPE, fragmentType)
        myIntent.putExtra(FragmentConstant.ACTIVITY_BUNDLE, bundle)
        BluesheetApplication.instance.activityLifeCycle.currentActivity?.startActivity(myIntent)
    }

    private fun toHome(bundle: Bundle?) {
        val myIntent = Intent(
            BluesheetApplication.instance.activityLifeCycle.currentActivity,
            HomeActivity::class.java
        )
        bundle?.let {
            myIntent.putExtras(it)
        }
        myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        BluesheetApplication.instance.activityLifeCycle.currentActivity?.startActivity(myIntent)
        BluesheetApplication.instance.activityLifeCycle.currentActivity ?. let {
            ActivityCompat.finishAffinity(it)
        }
    }

    private fun toCreateWOrkSpace(fragmentType: Int, bundle: Bundle?) {
        val myIntent = Intent(
            BluesheetApplication.instance.activityLifeCycle.currentActivity,
            SignUpActivity::class.java
        )
        myIntent.putExtra(FragmentConstant.FRAGMENT_TYPE, fragmentType)
        bundle?.let {
            myIntent.putExtras(it)
        }
        BluesheetApplication.instance.activityLifeCycle.currentActivity?.startActivity(myIntent)
    }

    private fun toChat(bundle: Bundle?) {
        bundle?.let {
            bundle?.let {
                it.getString("cId")?.let { cId ->
                    BluesheetApplication.instance.activityLifeCycle.currentActivity?.startActivity(
                        CustomMessageListActivity.createIntent(
                            BluesheetApplication.instance.applicationContext,
                            cid = cId
                        )
                    )
                }
            }
        }
    }

    private fun toUserInfoScreen(bundle: Bundle?) {
        val myIntent = Intent(
            BluesheetApplication.instance.activityLifeCycle.currentActivity,
            UserInfoActivity::class.java
        )
        bundle?.let {
            myIntent.putExtras(it)
        }
        BluesheetApplication.instance.activityLifeCycle.currentActivity?.startActivity(myIntent)
    }

    private fun toOtherChat(fragmentType: Int, bundle: Bundle?) {
        val myIntent = Intent(
            BluesheetApplication.instance.activityLifeCycle.currentActivity,
            ChatOtherActivity::class.java
        )
        myIntent.putExtra(FragmentConstant.FRAGMENT_TYPE, fragmentType)
        myIntent.putExtra(FragmentConstant.ACTIVITY_BUNDLE, bundle)
        BluesheetApplication.instance.activityLifeCycle.currentActivity?.startActivity(myIntent)
    }

    private fun toSwitchOrgAndWorkSpace(bundle: Bundle?) {
        val myIntent = Intent(
            BluesheetApplication.instance.activityLifeCycle.currentActivity,
            SwitchOrgAndWorkActivity::class.java
        )
        bundle?.let {
            myIntent.putExtras(it)
        }
        BluesheetApplication.instance.activityLifeCycle.currentActivity?.startActivity(myIntent)
    }

    fun reStartActivity() {
        val myIntent = Intent(
            BluesheetApplication.instance.activityLifeCycle.currentActivity,
            SplashActivity::class.java
        )
        myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        BluesheetApplication.instance.activityLifeCycle.currentActivity?.startActivity(myIntent)
        BluesheetApplication.instance.activityLifeCycle.currentActivity ?. let {
            ActivityCompat.finishAffinity(it)
        }
    }
}