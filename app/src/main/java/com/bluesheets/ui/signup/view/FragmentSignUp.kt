package com.bluesheets.ui.signup.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bluesheets.databinding.FragmentSignUpBinding
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo

class FragmentSignUp: Fragment() {

    private var binding: FragmentSignUpBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.buttonSignIn?.setOnClickListener {
            NavigateTo.screen(activityType = FragmentConstant.SIGN_UP_ACTIVITY,
                fragmentType = FragmentConstant.SIGN_IN_FRAGMENT)
        }
    }
}