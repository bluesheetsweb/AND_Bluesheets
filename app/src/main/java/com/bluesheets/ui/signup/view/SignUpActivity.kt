package com.bluesheets.ui.signup.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bluesheets.databinding.ActivityMainBinding
import com.bluesheets.utils.FragmentConstant
import src.wrapperutil.model.ModelFlow
import src.wrapperutil.utilities.FragmentTransaction

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FragmentTransaction.initParentFrameForFragment(type = FragmentConstant.SIGN_UP_ACTIVITY,
        ModelFlow(
            frameLayout = binding.frameLayout,
            fragmentManager = supportFragmentManager
        )
        )
        navigateToFragment(intent.getIntExtra(FragmentConstant.FRAGMENT_TYPE,0))
    }

    private fun navigateToFragment(fragmentType: Int, bundle: Bundle? = null){
        when (fragmentType){
            FragmentConstant.GET_STARTED_FRAGMENT -> {
                FragmentTransaction.add(type = FragmentConstant.SIGN_UP_ACTIVITY,
                    fragment = FragmentGetStarted())
            }
            FragmentConstant.SIGN_IN_FRAGMENT -> {
                FragmentTransaction.add(type = FragmentConstant.SIGN_UP_ACTIVITY,
                    fragment = FragmentSignIn())
            }
            FragmentConstant.SIGN_UP_FRAGMENT -> {
                FragmentTransaction.add(type = FragmentConstant.SIGN_UP_ACTIVITY,
                    fragment = FragmentSignUp())
            }
            else -> {
                Toast.makeText(this, "Issue with Fragment Type $fragmentType", Toast.LENGTH_SHORT)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.getIntExtra(FragmentConstant.FRAGMENT_TYPE,0)?.let {
            navigateToFragment(it)
        }
    }
}