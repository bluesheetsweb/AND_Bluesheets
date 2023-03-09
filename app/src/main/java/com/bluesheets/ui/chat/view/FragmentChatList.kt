package com.bluesheets.ui.chat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluesheets.databinding.FragmentChatListBinding
import com.bluesheets.ui.chat.viewmodel.ChatListViewModel
import src.wrapperutil.empty_state.StateManagerConstraintLayout

class FragmentChatList: Fragment() {

    private var binding: FragmentChatListBinding? = null
    private lateinit var viewModel: ChatListViewModel
    private lateinit var adapter: ChannelItemAdapter
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

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding?.recycleView?.setLayoutManager(layoutManager)
        adapter = ChannelItemAdapter()
        binding?.recycleView?.adapter = adapter

        viewModel.getState().observe(viewLifecycleOwner) {
            (binding?.root as StateManagerConstraintLayout)?.setViewState(it.state, viewModel)
        }

        viewModel.allChannels.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }

        binding?.viewModel = viewModel
    }
}