package com.bluesheets.ui.documents.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bluesheets.databinding.FragmentCreateWorkBinding
import com.bluesheets.databinding.FragmentDocumentListBinding
import com.bluesheets.ui.documents.viewmodel.DocumentListViewModel
import com.bluesheets.ui.workspace.viewmodel.CreateWorkSpaceViewModel
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

class FragmentDocument: Fragment() {

    private var binding: FragmentDocumentListBinding? = null
    private lateinit var viewModel: DocumentListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDocumentListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DocumentListViewModel::class.java)
        binding?.viewModel = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getDocumentList()

    }
}