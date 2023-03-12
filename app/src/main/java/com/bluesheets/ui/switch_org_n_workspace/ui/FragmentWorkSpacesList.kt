package com.bluesheets.ui.switch_org_n_workspace.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bluesheets.databinding.FragmentWorkspacesListBinding

class FragmentWorkSpacesList: Fragment() {

    private var binding: FragmentWorkspacesListBinding? = null
//    private lateinit var viewModel: DocumentListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkspacesListBinding.inflate(inflater, container, false)
//        viewModel = ViewModelProvider(this).get(DocumentListViewModel::class.java)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}