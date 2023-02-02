package com.bluesheets.ui.workspace.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bluesheets.databinding.FragmentCreateOrgBinding
import com.bluesheets.databinding.FragmentCreateWorkBinding
import com.bluesheets.ui.organisation.viewmodel.CreateOrgViewModel
import com.bluesheets.ui.workspace.viewmodel.CreateWorkSpaceViewModel
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

class FragmentCreateWorkSpace: Fragment() {

    private var binding: FragmentCreateWorkBinding? = null
    private lateinit var viewModel: CreateWorkSpaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateWorkBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CreateWorkSpaceViewModel::class.java)
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getState().observe(viewLifecycleOwner) {
            binding?.stateLayout?.setViewState(it.state, viewModel)
        }
        viewModel.buttonEnabled.observe(viewLifecycleOwner) {
            if (it) {
                binding?.buttonCreateWork?.updateMode(WrapperEnumAnnotation(WrapperConstant.BUTTON_MODE_PRIMARY))
            } else {
                binding?.buttonCreateWork?.updateMode(WrapperEnumAnnotation(WrapperConstant.BUTTON_MODE_DISABLED))
            }
        }
    }
}