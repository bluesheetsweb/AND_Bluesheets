package com.bluesheets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo
import java.util.Timer
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timer().schedule(1000){
            NavigateTo.screen(activityType = FragmentConstant.SIGN_UP_ACTIVITY, fragmentType = FragmentConstant.GET_STARTED_FRAGMENT)
            finish()
        }
    }
}