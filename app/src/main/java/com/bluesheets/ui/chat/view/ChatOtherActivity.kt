package com.bluesheets.ui.chat.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bluesheets.BluesheetApplication
import com.bluesheets.databinding.ActivityMainBinding
import com.bluesheets.utils.FragmentConstant
import src.wrapperutil.model.ModelFlow
import src.wrapperutil.utilities.FragmentTransaction

class ChatOtherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FragmentTransaction.initParentFrameForFragment(
            type = FragmentConstant.CHAT_OTHER_ACTIVITY,
            ModelFlow(
                frameLayout = binding.frameLayout,
                fragmentManager = supportFragmentManager
            )
        )

        navigateToFragment(intent.getIntExtra(FragmentConstant.FRAGMENT_TYPE, 0))
    }

    private fun navigateToFragment(fragmentType: Int, bundle: Bundle? = null) {
        when (fragmentType) {
            FragmentConstant.CHAT_INFO_FRAGMENT -> {
                intent?.let {
                    it.getBundleExtra(FragmentConstant.ACTIVITY_BUNDLE) ?.let {
                        it.getString("cId")?.let { cId ->
                            FragmentTransaction.add(
                                type = FragmentConstant.CHAT_OTHER_ACTIVITY,
                                fragment = ChannelInfoFragment(cId)
                            )
                        }
                    }
                }
            }
            FragmentConstant.CHAT_ADD_MORE_FRAGMENT -> {
                intent?.let {
                    it.getBundleExtra(FragmentConstant.ACTIVITY_BUNDLE) ?.let {
                        it.getString("cId")?.let { cId ->
                            FragmentTransaction.add(
                                type = FragmentConstant.CHAT_OTHER_ACTIVITY,
                                fragment = ChannelAddMoreFragment(cId),
                                isToAddBack = true
                            )
                        }
                    }
                }
            }
            FragmentConstant.CREATE_CHANNEL_FRAGMENT -> {
                FragmentTransaction.add(
                    type = FragmentConstant.CHAT_OTHER_ACTIVITY,
                    fragment = ChannelOneToOneFragment()
                )
            }
            else -> {
                Toast.makeText(this, "Issue with Fragment Type $fragmentType", Toast.LENGTH_SHORT)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.getIntExtra(FragmentConstant.FRAGMENT_TYPE, 0)?.let {
            navigateToFragment(it)
        }
    }
}