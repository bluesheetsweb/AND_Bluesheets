package com.bluesheets.ui.chat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bluesheets.databinding.FragmentChatListBinding
import com.bluesheets.databinding.FragmentGetStartedBinding
import com.bluesheets.ui.chat.viewmodel.ChatListViewModel
import com.bluesheets.ui.home.viewmodel.HomeViewModel
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo

class FragmentChatList: Fragment() {

    private var binding: FragmentChatListBinding? = null
    private lateinit var viewModel: ChatListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatListBinding.inflate(inflater,container, false)
        viewModel = ViewModelProvider(this).get(ChatListViewModel::class.java)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.initChat()
//        binding?.buttonGetStarted?.setOnClickListener {
//            NavigateTo.screen(activityType = FragmentConstant.SIGN_UP_ACTIVITY,
//                fragmentType = FragmentConstant.SIGN_UP_FRAGMENT)
//        }
//
//        binding?.textAlreadyHaveAccount?.setOnClickListener {
//            NavigateTo.screen(activityType = FragmentConstant.SIGN_UP_ACTIVITY,
//                fragmentType = FragmentConstant.SIGN_IN_FRAGMENT)
//        }
    }
}