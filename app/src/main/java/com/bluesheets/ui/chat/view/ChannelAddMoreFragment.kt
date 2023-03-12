package com.bluesheets.ui.chat.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluesheets.databinding.FragmentChannelAddMoreBinding
import com.bluesheets.databinding.FragmentChannelAddMoreBindingImpl
import com.bluesheets.databinding.FragmentChannelInfoBinding
import com.bluesheets.ui.chat.viewmodel.ChannelAddMoreViewModel
import com.bluesheets.ui.chat.viewmodel.ChannelInfoViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.getstream.chat.android.client.models.Channel
import jp.wasabeef.glide.transformations.BlurTransformation
import src.wrapperutil.empty_state.StateManagerConstraintLayout
import src.wrapperutil.uicomponent.BaseDialogFragment
import src.wrapperutil.utilities.ColorPicker
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

private var binding: FragmentChannelAddMoreBinding? = null
private lateinit var viewModel: ChannelAddMoreViewModel
private lateinit var adapter: ChannelAddUserAdapter

class ChannelAddMoreFragment(private val cId: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChannelAddMoreBinding.inflate(inflater,container, false)
        viewModel = ViewModelProvider(this).get(ChannelAddMoreViewModel::class.java)
        viewModel.getChannel(cId)
        binding?.viewModel = viewModel
        binding?.backButton?.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.setLayoutManager(layoutManager)
        adapter = ChannelAddUserAdapter() {
            viewModel.selectParticipant(it)
        }
        binding?.recyclerView?.adapter = adapter

        viewModel.getState().observe(viewLifecycleOwner) {
            (binding?.root as StateManagerConstraintLayout)?.setViewState(it.state, viewModel)
            if (it.state == WrapperConstant.STATE_SCREEN_SUCCESS) {
                binding?.viewModel = viewModel
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listNewUsers.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
        viewModel.refreshChannel.observe(viewLifecycleOwner){
            if (it) {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
        viewModel.isDisabled.observe(viewLifecycleOwner){
            if (it) {
               binding?.addButton?.updateMode(WrapperEnumAnnotation(WrapperConstant.BUTTON_MODE_DISABLED))
            } else {
                binding?.addButton?.updateMode(WrapperEnumAnnotation(WrapperConstant.BUTTON_MODE_PRIMARY))
            }
        }
    }

//    override fun onStart() {
//        super.onStart()
//        val dialog: Dialog? = dialog
//        if (dialog != null) {
//            val width = ViewGroup.LayoutParams.MATCH_PARENT
//            val height = ViewGroup.LayoutParams.MATCH_PARENT
//            dialog.getWindow()?.setLayout(width, height)
//        }
//    }
}