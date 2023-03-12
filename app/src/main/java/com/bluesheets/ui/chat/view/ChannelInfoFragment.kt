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
import com.bluesheets.databinding.FragmentChannelInfoBinding
import com.bluesheets.ui.chat.viewmodel.ChannelInfoViewModel
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.getstream.chat.android.client.models.Channel
import jp.wasabeef.glide.transformations.BlurTransformation
import src.wrapperutil.empty_state.StateManagerConstraintLayout
import src.wrapperutil.uicomponent.BaseDialogFragment
import src.wrapperutil.utilities.ColorPicker
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

private var binding: FragmentChannelInfoBinding? = null
private lateinit var viewModel: ChannelInfoViewModel
private lateinit var adapter: ChannelUserAdapter

class ChannelInfoFragment(private val cid: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChannelInfoBinding.inflate(inflater,container, false)
        viewModel = ViewModelProvider(this).get(ChannelInfoViewModel::class.java)
        binding?.imageClose?.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        binding?.deleteButton?.setOnClickListener {
            viewModel.deleteOrLeaveChannel()
        }
        binding?.layoutAddUser?.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("cId", cid)
            NavigateTo.screen(FragmentConstant.CHAT_OTHER_ACTIVITY, FragmentConstant.CHAT_ADD_MORE_FRAGMENT, bundle)
        }

        viewModel.getState().observe(viewLifecycleOwner) {
            (binding?.root as StateManagerConstraintLayout)?.setViewState(it.state, viewModel)
            if (it.state == WrapperConstant.STATE_SCREEN_SUCCESS) {
                updateUI()
            }
        }
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getChannel(cid)
    }

    fun updateUI(){
        binding?.viewModel = viewModel
        if (viewModel.isChannelAdmin) {
            binding?.deleteButton?.text = "Delete"
        } else {
            binding?.deleteButton?.text = "Leave"
        }
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.setLayoutManager(layoutManager)
        adapter = ChannelUserAdapter(viewModel.adminId, !viewModel.isOneToOne) {
            viewModel.removeUserFromChannel(it)
        }
        binding?.recyclerView?.adapter = adapter
        Glide.with(this).load(viewModel.channelImage)
            .placeholder(viewModel.channelThumb)
            .into((binding?.imageProfile!!))
        Glide.with(this).load(viewModel.channelImage)
            .placeholder(viewModel.channelThumb)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(75)))
            .into((binding?.imageBlur!!))

        viewModel.listUsers.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }

        if (!viewModel.isOneToOne && viewModel.isChannelAdmin){
            binding?.layoutAddUser?.visibility = View.VISIBLE
            binding?.editButton?.visibility = View.VISIBLE
            binding?.changeImage?.visibility = View.VISIBLE
        } else {
            binding?.layoutAddUser?.visibility = View.GONE
            binding?.editButton?.visibility = View.GONE
            binding?.changeImage?.visibility = View.GONE
        }
        if (viewModel.isChannelAdmin || !viewModel.isOneToOne){
            binding?.deleteButton?.visibility = View.VISIBLE
        } else {
            binding?.deleteButton?.visibility = View.GONE
        }

        if (!viewModel.isOneToOne && viewModel.isChannelAdmin){
            binding?.layoutAddUser?.visibility = View.VISIBLE
        } else {
            binding?.layoutAddUser?.visibility = View.GONE
        }
        viewModel.isEditing.observe(viewLifecycleOwner) {
            if (it){
                binding?.layoutEdit?.visibility = View.VISIBLE
                binding?.textName?.visibility = View.GONE
                binding?.textSubDesc?.visibility = View.GONE
                binding?.layoutMainName?.setBackgroundColor(Color.parseColor(ColorPicker.GRAY_LIGHT))
            } else {
                binding?.layoutEdit?.visibility = View.GONE
                binding?.textName?.visibility = View.VISIBLE
                binding?.textSubDesc?.visibility = View.VISIBLE
                binding?.layoutMainName?.setBackgroundColor(Color.WHITE)
                hideSoftKeyboard()
            }
            binding?.editButton?.text = viewModel.editButtonText
        }

        viewModel.isSearch.observe(viewLifecycleOwner){
            if (it) {
                binding?.layoutSearch?.visibility = View.VISIBLE
                binding?.textParticipants?.visibility = View.GONE
                binding?.imageSearch?.visibility = View.GONE
                binding?.deleteButton?.visibility = View.GONE
            } else {
                binding?.layoutSearch?.visibility = View.GONE
                binding?.textParticipants?.visibility = View.VISIBLE
                binding?.imageSearch?.visibility = View.VISIBLE
                binding?.deleteButton?.visibility = View.VISIBLE
                hideSoftKeyboard()
            }
        }
        viewModel.refreshChannel.observe(viewLifecycleOwner){
            binding?.textName?.text = viewModel.channelName
        }
        viewModel.isDisabled.observe(viewLifecycleOwner){
            if (it) {
                binding?.editButton?.updateMode(WrapperEnumAnnotation(WrapperConstant.BUTTON_MODE_DISABLED))
            } else {
                binding?.editButton?.updateMode(WrapperEnumAnnotation(WrapperConstant.BUTTON_MODE_PRIMARY))
            }
        }
    }

    fun hideSoftKeyboard() {

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