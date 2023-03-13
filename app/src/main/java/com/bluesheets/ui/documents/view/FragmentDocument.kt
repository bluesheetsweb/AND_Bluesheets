package com.bluesheets.ui.documents.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluesheets.databinding.FragmentCreateWorkBinding
import com.bluesheets.databinding.FragmentDocumentListBinding
import com.bluesheets.ui.chat.view.ChannelAddUserAdapter
import com.bluesheets.ui.documents.viewmodel.DocumentListViewModel
import com.bluesheets.ui.workspace.viewmodel.CreateWorkSpaceViewModel
import src.wrapperutil.empty_state.StateManagerConstraintLayout
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

class FragmentDocument: Fragment() {

    private var binding: FragmentDocumentListBinding? = null
    private lateinit var viewModel: DocumentListViewModel
    private lateinit var adapter: DocumentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDocumentListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DocumentListViewModel::class.java)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.setLayoutManager(layoutManager)
        adapter = DocumentListAdapter(true) {
//            viewModel.selectParticipant(it)
        }
        binding?.recyclerView?.adapter = adapter

        viewModel.getState().observe(viewLifecycleOwner) {
            (binding?.root as StateManagerConstraintLayout).setViewState(it.state, viewModel)
            if (it.state == WrapperConstant.STATE_SCREEN_SUCCESS) {
                binding?.viewModel = viewModel
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getState().observe(viewLifecycleOwner) {
            binding?.stateLayout?.setViewState(it.state, viewModel)
        }
        viewModel.initData()
        viewModel.listNewUsers.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }
}