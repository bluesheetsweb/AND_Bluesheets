package com.bluesheets.ui.user_info.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bluesheets.databinding.ActivityHomeBinding
import com.bluesheets.databinding.ActivityUserInfoBinding
import com.bluesheets.ui.home.viewmodel.HomeViewModel
import com.bluesheets.ui.user_info.viewmodel.EditProfileViewModel

class UserInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserInfoBinding
    private lateinit var viewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        binding.viewModel = viewModel
        binding.textTitle.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
    }
}