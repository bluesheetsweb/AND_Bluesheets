package com.bluesheets.ui.organisation.view

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bluesheets.databinding.FragmentCreateOrgBinding
import com.bluesheets.databinding.FragmentSignInBinding
import com.bluesheets.ui.organisation.viewmodel.CreateOrgViewModel
import com.bluesheets.ui.signup.repository.SignInRepo
import com.bluesheets.ui.signup.viewmodel.SignInViewModel
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

class FragmentCreateOrg: Fragment() {

    private var binding: FragmentCreateOrgBinding? = null
    private lateinit var viewModel: CreateOrgViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateOrgBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CreateOrgViewModel::class.java)
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getState().observe(viewLifecycleOwner) {
            binding?.stateLayout?.setViewState(it.state, viewModel)
        }
        viewModel.buttonEnabled.observe(viewLifecycleOwner) {
            if (it) {
                binding?.buttonCreateOrg?.updateMode(WrapperEnumAnnotation(WrapperConstant.BUTTON_MODE_PRIMARY))
            } else {
                binding?.buttonCreateOrg?.updateMode(WrapperEnumAnnotation(WrapperConstant.BUTTON_MODE_DISABLED))
            }
        }
    }
}
