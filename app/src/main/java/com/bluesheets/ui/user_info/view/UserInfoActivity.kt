package com.bluesheets.ui.user_info.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bluesheets.R
import com.bluesheets.databinding.ActivityHomeBinding
import com.bluesheets.databinding.ActivityUserInfoBinding
import com.bluesheets.ui.common.BaseActivity
import com.bluesheets.ui.common.CameraGalleryView
import com.bluesheets.ui.common.OnCameraGalleryListener
import com.bluesheets.ui.home.viewmodel.HomeViewModel
import com.bluesheets.ui.user_info.viewmodel.EditProfileViewModel
import com.bluesheets.utils.UserInfoUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UserInfoActivity : BaseActivity() {
    private lateinit var binding: ActivityUserInfoBinding
    private lateinit var viewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.getState().observe(this) {
            binding?.stateLayout?.setViewState(it.state, viewModel)
        }
        binding.textTitle.setOnClickListener {
            finish()
        }

        binding?.let {
            Glide.with(this)
                .load(UserInfoUtil.userProfileImage)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_pic)
                .into(it.imgBgView)
        };

        binding.deleteAccount.setOnClickListener {
            viewModel.askForAccountDelete()
        }

        binding.imgBgView.setOnClickListener {
            CameraGalleryView.openDialog(
                baseActivity = this as BaseActivity,
                listener = object : OnCameraGalleryListener {
                    override fun onReceivedFromCamera(imagePath: String?) {

                    }

                    override fun onReceivedFromGallery(imagePath: String?) {

                    }

                    override fun onReceivedFromGallery(multipleImagesList: MutableList<String>) {

                    }
                }
            )
        }

    }

    override fun onResume() {
        super.onResume()
    }
}