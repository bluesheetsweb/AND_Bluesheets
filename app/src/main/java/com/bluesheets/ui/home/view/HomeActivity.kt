package com.bluesheets.ui.home.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bluesheets.R
import com.bluesheets.databinding.ActivityHomeBinding
import com.bluesheets.ui.contact.view.FragmentContact
import com.bluesheets.ui.home.viewmodel.HomeViewModel
import com.bluesheets.utils.FragmentConstant
import src.wrapperutil.model.ModelFlow
import src.wrapperutil.utilities.FragmentTransaction

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        FragmentTransaction.initParentFrameForFragment(type = FragmentConstant.HOME_ACTIVITY,
            ModelFlow(
                frameLayout = binding.frameLayout,
                fragmentManager = supportFragmentManager
            )
        )
        viewModel.currentSelectedPos.observe(this){
            navigateToFragment(it)
        }
        viewModel.currentSelectedPos.value = 0
    }

    private fun navigateToFragment(selectedPos: Int){
        when (selectedPos){
            0 -> {
                binding.imageChat.setImageResource(R.drawable.ic_blue_chat)
                binding.imageExpense.setImageResource(R.drawable.ic_black_expense)
                binding.imageDocument.setImageResource(R.drawable.ic_black_document)
                binding.imageContact.setImageResource(R.drawable.ic_black_contact)
                FragmentTransaction.add(type = FragmentConstant.HOME_ACTIVITY,
                    fragment = FragmentContact()
                )
            }
            1 -> {
                binding.imageChat.setImageResource(R.drawable.ic_black_chat)
                binding.imageExpense.setImageResource(R.drawable.ic_blue_expense)
                binding.imageDocument.setImageResource(R.drawable.ic_black_document)
                binding.imageContact.setImageResource(R.drawable.ic_black_contact)
                FragmentTransaction.add(type = FragmentConstant.HOME_ACTIVITY,
                    fragment = FragmentContact()
                )
            }
            2 -> {
                binding.imageChat.setImageResource(R.drawable.ic_black_chat)
                binding.imageExpense.setImageResource(R.drawable.ic_black_expense)
                binding.imageDocument.setImageResource(R.drawable.ic_blue_document)
                binding.imageContact.setImageResource(R.drawable.ic_black_contact)
                FragmentTransaction.add(type = FragmentConstant.HOME_ACTIVITY,
                    fragment = FragmentContact()
                )
            }
            else -> {
                binding.imageChat.setImageResource(R.drawable.ic_black_chat)
                binding.imageExpense.setImageResource(R.drawable.ic_black_expense)
                binding.imageDocument.setImageResource(R.drawable.ic_black_document)
                binding.imageContact.setImageResource(R.drawable.ic_blue_contact)
                FragmentTransaction.add(type = FragmentConstant.HOME_ACTIVITY,
                    fragment = FragmentContact()
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}