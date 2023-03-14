package com.bluesheets.ui.home.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bluesheets.R
import com.bluesheets.databinding.ActivityHomeBinding
import com.bluesheets.ui.chat.view.FragmentChatList
import com.bluesheets.ui.contact.view.FragmentContact
import com.bluesheets.ui.documents.view.FragmentDocument
import com.bluesheets.ui.home.viewmodel.HomeViewModel
import com.bluesheets.utils.FragmentConstant
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.bluesheets.utils.NavigateTo
import com.bluesheets.utils.UserInfoUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import src.wrapperutil.model.ModelFlow
import src.wrapperutil.uicomponent.LinearLayout
import src.wrapperutil.utilities.FragmentTransaction
import src.wrapperutil.utilities.Toaster


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        FragmentTransaction.initParentFrameForFragment(type = FragmentConstant.HOME_ACTIVITY,
            ModelFlow(
                frameLayout = binding.frameLayout,
                fragmentManager = supportFragmentManager
            )
        )
        viewModel.currentSelectedPos.observe(this){
            navigateToFragment(it)
        }
        viewModel.currentSelectedPos.value = 0

        binding?.imageWithButton?.updateImageSrc(R.drawable.ic_white_plus)
        binding?.imageWithButton?.textValue

        binding.imageWithButton.setOnClickListener {
            if (viewModel.currentSelectedPos.value == 0) {
                showChatBottomSheet()
            } else {
                showUploadBottomSheet()
            }
        }
        
        binding?.let {
            Glide.with(this)
                .load(UserInfoUtil.workSpaceLOGO)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.ic_pic)
                .into(it.profilePic)
        }

        binding.layoutWorkNOrg.setOnClickListener {
            NavigateTo.screen(
                activityType = FragmentConstant.SWITCH_ORG_N_WORK_ACTIVITY
            )
        }
    }

    fun showChatBottomSheet(){
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.chat_bottom_sheet)

        val newMessage: LinearLayout? = bottomSheetDialog.findViewById(R.id.layoutNewMessage)
        newMessage?.setOnClickListener {
            NavigateTo.screen(
                activityType = FragmentConstant.CHAT_OTHER_ACTIVITY,
                fragmentType = FragmentConstant.CREATE_CHANNEL_FRAGMENT
            )
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    fun showUploadBottomSheet(){
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.upload_bottom_sheet)

        val uploadCamera: LinearLayout? = bottomSheetDialog.findViewById(R.id.layoutCamera)
        uploadCamera?.setOnClickListener {

            bottomSheetDialog.dismiss()
        }
        val uploadGallery: LinearLayout? = bottomSheetDialog.findViewById(R.id.layoutGallery)
        uploadGallery?.setOnClickListener {

            bottomSheetDialog.dismiss()
        }
        val uploadDocument: LinearLayout? = bottomSheetDialog.findViewById(R.id.layoutDocument)
        uploadDocument?.setOnClickListener {

            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    private fun navigateToFragment(selectedPos: Int){
        when (selectedPos){
            0 -> {
                binding.titleHeader.text = "Messages"
                binding.textChat.updateTextColor(Color.parseColor("#0060FD"))
                binding.textExpense.updateTextColor(Color.parseColor("#9D9D9D"))
                binding.textDocument.updateTextColor(Color.parseColor("#9D9D9D"))
                binding.textContact.updateTextColor(Color.parseColor("#9D9D9D"))
                binding.titleHeader.visibility = View.VISIBLE
                binding.imageWithButton.visibility = View.VISIBLE
                binding.imageChat.setImageResource(R.drawable.ic_chat_selected)
                binding.imageExpense.setImageResource(R.drawable.ic_expenses_unselected)
                binding.imageDocument.setImageResource(R.drawable.ic_document_unselected)
                binding.imageContact.setImageResource(R.drawable.ic_contact_unselected)
                FragmentTransaction.add(type = FragmentConstant.HOME_ACTIVITY,
                    fragment = FragmentChatList()
                )
            }
            1 -> {
                binding.titleHeader.text = "Expenses"
                binding.textChat.updateTextColor(Color.parseColor("#9D9D9D"))
                binding.textExpense.updateTextColor(Color.parseColor("#0060FD"))
                binding.textDocument.updateTextColor(Color.parseColor("#9D9D9D"))
                binding.textContact.updateTextColor(Color.parseColor("#9D9D9D"))
                binding.titleHeader.visibility = View.VISIBLE
                binding.imageWithButton.visibility = View.VISIBLE
                binding.imageChat.setImageResource(R.drawable.ic_chat_unselected)
                binding.imageExpense.setImageResource(R.drawable.ic_expenses_selected)
                binding.imageDocument.setImageResource(R.drawable.ic_document_unselected)
                binding.imageContact.setImageResource(R.drawable.ic_contact_unselected)
                FragmentTransaction.add(type = FragmentConstant.HOME_ACTIVITY,
                    fragment = FragmentContact()
                )
            }
            2 -> {
                binding.titleHeader.text = "Documents"
                binding.textChat.updateTextColor(Color.parseColor("#9D9D9D"))
                binding.textExpense.updateTextColor(Color.parseColor("#9D9D9D"))
                binding.textDocument.updateTextColor(Color.parseColor("#0060FD"))
                binding.textContact.updateTextColor(Color.parseColor("#9D9D9D"))
                binding.titleHeader.visibility = View.VISIBLE
                binding.imageWithButton.visibility = View.VISIBLE
                binding.imageChat.setImageResource(R.drawable.ic_chat_unselected)
                binding.imageExpense.setImageResource(R.drawable.ic_expenses_unselected)
                binding.imageDocument.setImageResource(R.drawable.ic_document_selected)
                binding.imageContact.setImageResource(R.drawable.ic_contact_unselected)
                FragmentTransaction.add(type = FragmentConstant.HOME_ACTIVITY,
                    fragment = FragmentDocument()
                )
            }
            else -> {
                binding.textChat.updateTextColor(Color.parseColor("#9D9D9D"))
                binding.textExpense.updateTextColor(Color.parseColor("#9D9D9D"))
                binding.textDocument.updateTextColor(Color.parseColor("#9D9D9D"))
                binding.textContact.updateTextColor(Color.parseColor("#0060FD"))
                binding.titleHeader.visibility = View.GONE
                binding.imageWithButton.visibility = View.GONE
                binding.imageChat.setImageResource(R.drawable.ic_chat_unselected)
                binding.imageExpense.setImageResource(R.drawable.ic_expenses_unselected)
                binding.imageDocument.setImageResource(R.drawable.ic_document_unselected)
                binding.imageContact.setImageResource(R.drawable.ic_contact_selected)
                FragmentTransaction.add(type = FragmentConstant.HOME_ACTIVITY,
                    fragment = FragmentContact()
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}