package com.bluesheets.ui.signup.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bluesheets.databinding.FragmentSignUpBinding
import com.bluesheets.ui.signup.viewmodel.SignInViewModel
import com.bluesheets.ui.signup.viewmodel.SignUpViewModel
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

class FragmentSignUp: Fragment() {

    private var binding: FragmentSignUpBinding? = null
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getState().observe(viewLifecycleOwner) {
            binding?.stateLayout?.setViewState(it.state, viewModel)
        }

        viewModel.buttonEnabled.observe(viewLifecycleOwner) {
            if (it) {
                binding?.buttonSignUp?.updateMode(WrapperEnumAnnotation(WrapperConstant.BUTTON_MODE_PRIMARY))
            } else {
                binding?.buttonSignUp?.updateMode(WrapperEnumAnnotation(WrapperConstant.BUTTON_MODE_DISABLED))
            }
        }
        viewModel.passwordDontMatch.observe(viewLifecycleOwner) {
            if (it) {
                binding?.editTextRePassword?.setError("Password doesn't matched")
                binding?.editTextPassword?.setError("Password doesn't matched")
            } else {
                binding?.editTextRePassword?.setError(null)
                binding?.editTextPassword?.setError(null)
            }
        }
    }
}