package com.bluesheets.ui.chat.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluesheets.R
import com.bluesheets.databinding.FragmentChannelAddMoreBinding
import com.bluesheets.databinding.FragmentChannelAddMoreBindingImpl
import com.bluesheets.databinding.FragmentChannelFollowupBinding
import com.bluesheets.databinding.FragmentChannelInfoBinding
import com.bluesheets.ui.chat.model.ConnectionUserModel
import com.bluesheets.ui.chat.viewmodel.ChannelAddMoreViewModel
import com.bluesheets.ui.chat.viewmodel.ChannelCreateViewModel
import com.bluesheets.ui.chat.viewmodel.ChannelInfoViewModel
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.getstream.chat.android.client.models.Channel
import jp.wasabeef.glide.transformations.BlurTransformation
import src.wrapperutil.empty_state.StateManagerConstraintLayout
import src.wrapperutil.uicomponent.BaseDialogFragment
import src.wrapperutil.uicomponent.LinearLayout
import src.wrapperutil.utilities.ColorPicker
import src.wrapperutil.utilities.FragmentTransaction
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation

private var binding: FragmentChannelFollowupBinding? = null
private lateinit var viewModel: ChannelCreateViewModel

class ChannelFollowUpFragment(private val isGroup: Boolean, private var selectedUser: ConnectionUserModel? = null) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChannelFollowupBinding.inflate(inflater,container, false)
        viewModel = ViewModelProvider(this).get(ChannelCreateViewModel::class.java)
        viewModel.initOrg(isGroup, selectedUser)
        binding?.viewModel = viewModel
        binding?.backButton?.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        binding?.layoutOrganization?.setOnClickListener {
            showOrgBottomSheet()
        }
        binding?.layoutTag?.setOnClickListener {
            showTagBottomSheet()
        }

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
        binding?.textSelectTag?.visibility = View.GONE
        binding?.layoutTag?.visibility = View.GONE

        if (isGroup) {
            binding?.titleText?.text = "Create Group"
            binding?.editName?.visibility = View.VISIBLE
            binding?.titleName?.visibility = View.VISIBLE
            binding?.addButton?.text = "Next"
        } else {
            binding?.editName?.visibility = View.GONE
            binding?.titleName?.visibility = View.GONE
            binding?.titleText?.text = "Complete Process"
            binding?.addButton?.text = "Create Chat"
        }

        binding?.addButton?.updateMode(WrapperEnumAnnotation(WrapperConstant.BUTTON_MODE_DISABLED))

        viewModel.refreshChannel.observe(viewLifecycleOwner){
            if (it) {
                activity?.finish()
            }
        }
        viewModel.selectedOrg.observe(viewLifecycleOwner){
            if (it == null) {
                binding?.textOrganization?.text = "Select Organization"
                binding?.textOrganization?.updateMode(WrapperEnumAnnotation(WrapperConstant.TEXT_MODE_PARAGRAPH_MEDIUM_ERROR))
            }
            it ?. let {
                binding?.textOrganization?.text = it.name
                binding?.textOrganization?.updateMode(WrapperEnumAnnotation(WrapperConstant.TEXT_MODE_PARAGRAPH_REGULAR))
                binding?.textSelectTag?.visibility = View.VISIBLE
                binding?.layoutTag?.visibility = View.VISIBLE
                binding?.textTag?.text = "Select Tag"
                binding?.textTag?.updateMode(WrapperEnumAnnotation(WrapperConstant.TEXT_MODE_PARAGRAPH_MEDIUM_ERROR))
            }
        }

        viewModel.selectedTag.observe(viewLifecycleOwner){
            it ?. let {
                binding?.textTag?.text = it.name
                binding?.textTag?.updateMode(WrapperEnumAnnotation(WrapperConstant.TEXT_MODE_PARAGRAPH_REGULAR))
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

    fun showOrgBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireActivity())
        bottomSheetDialog.setContentView(R.layout.bottomsheet_org_tag)

        val recyclerView: RecyclerView? = bottomSheetDialog.findViewById(R.id.recyclerView)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView?.setLayoutManager(layoutManager)
        var adapter = OrgTagAdapter(true) {
            viewModel.selectOrg(it)
            bottomSheetDialog.dismiss()
        }
        adapter.updateList(viewModel.listOrgTags)
        recyclerView?.adapter = adapter
        bottomSheetDialog.show()
    }

    fun showTagBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireActivity())
        bottomSheetDialog.setContentView(R.layout.bottomsheet_org_tag)

        val recyclerView: RecyclerView? = bottomSheetDialog.findViewById(R.id.recyclerView)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView?.setLayoutManager(layoutManager)
        var adapter = OrgTagAdapter(false) {
            viewModel.selectTag(it)
            bottomSheetDialog.dismiss()
        }
        adapter.updateList(viewModel.listTag)
        recyclerView?.adapter = adapter
        bottomSheetDialog.show()
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