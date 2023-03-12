package com.bluesheets.ui.switch_org_n_workspace.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bluesheets.databinding.FragmentOrganizationsListBinding
import com.bluesheets.ui.switch_org_n_workspace.viewmodel.OrgAndWorkSpaceViewModel

class FragmentOrganiszationsList : Fragment() {

    private var binding: FragmentOrganizationsListBinding? = null
    private lateinit var viewModel: OrgAndWorkSpaceViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrganizationsListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(OrgAndWorkSpaceViewModel::class.java)
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getState().observe(viewLifecycleOwner) {
            binding?.stateLayout?.setViewState(it.state, viewModel)
        }
    }
}