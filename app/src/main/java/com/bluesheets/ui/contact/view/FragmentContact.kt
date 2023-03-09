package com.bluesheets.ui.contact.view

import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bluesheets.databinding.FragmentContactBinding
import com.bluesheets.databinding.FragmentContactsBinding
import com.bluesheets.ui.chat.view.FragmentChatList
import com.bluesheets.ui.contact.viewmodel.UserInfoViewModel
import com.bluesheets.ui.organisation.view.FragmentCreateOrg
import com.bluesheets.ui.signup.viewmodel.SignInViewModel
import com.bluesheets.ui.workspace.view.FragmentCreateWorkSpace
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.UserInfoUtil
import com.bumptech.glide.Glide
import src.wrapperutil.utilities.FragmentTransaction
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

class FragmentContact: Fragment() {

    private var binding: FragmentContactsBinding? = null
    private lateinit var viewModel: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)
        binding?.viewModel = viewModel
        viewModel.getUserInfo()
        viewModel.navigateToEditProfile.observe(viewLifecycleOwner){
            navigateToUserFragment(it)
        }
        return binding?.root
    }

    private fun navigateToUserFragment(value: Boolean){

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getState().observe(viewLifecycleOwner) {
            binding?.stateLayout?.setViewState(it.state, viewModel)
        }
        binding?.let {
            Glide.with(this)
                .load(UserInfoUtil.userProfileImage)
                .into(it.imgBgView)
        };

        binding?.buttonEditProfile?.updateMode(WrapperEnumAnnotation(WrapperConstant.BUTTON_MODE_PRIMARY))

    }

}