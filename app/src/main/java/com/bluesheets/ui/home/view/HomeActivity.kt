package com.bluesheets.ui.home.view

import android.content.Intent
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
    }

    fun showChatBottomSheet(){
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.chat_bottom_sheet)

        val newMessage: LinearLayout? = bottomSheetDialog.findViewById(R.id.layoutNewMessage)
        newMessage?.setOnClickListener {

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
                binding.titleHeader.visibility = View.VISIBLE
                binding.imageWithButton.visibility = View.VISIBLE
                binding.imageChat.setImageResource(R.drawable.ic_blue_chat)
                binding.imageExpense.setImageResource(R.drawable.ic_black_expense)
                binding.imageDocument.setImageResource(R.drawable.ic_black_document)
                binding.imageContact.setImageResource(R.drawable.ic_black_contact)
                FragmentTransaction.add(type = FragmentConstant.HOME_ACTIVITY,
                    fragment = FragmentChatList()
                )
            }
            1 -> {
                binding.titleHeader.text = "Expenses"
                binding.titleHeader.visibility = View.VISIBLE
                binding.imageWithButton.visibility = View.VISIBLE
                binding.imageChat.setImageResource(R.drawable.ic_black_chat)
                binding.imageExpense.setImageResource(R.drawable.ic_blue_expense)
                binding.imageDocument.setImageResource(R.drawable.ic_black_document)
                binding.imageContact.setImageResource(R.drawable.ic_black_contact)
                FragmentTransaction.add(type = FragmentConstant.HOME_ACTIVITY,
                    fragment = FragmentContact()
                )
            }
            2 -> {
                binding.titleHeader.text = "Documents"
                binding.titleHeader.visibility = View.VISIBLE
                binding.imageWithButton.visibility = View.VISIBLE
                binding.imageChat.setImageResource(R.drawable.ic_black_chat)
                binding.imageExpense.setImageResource(R.drawable.ic_black_expense)
                binding.imageDocument.setImageResource(R.drawable.ic_blue_document)
                binding.imageContact.setImageResource(R.drawable.ic_black_contact)
                FragmentTransaction.add(type = FragmentConstant.HOME_ACTIVITY,
                    fragment = FragmentDocument()
                )
            }
            else -> {
                binding.titleHeader.visibility = View.GONE
                binding.imageWithButton.visibility = View.GONE
                binding.imageChat.setImageResource(R.drawable.ic_black_chat)
                binding.imageExpense.setImageResource(R.drawable.ic_black_expense)
                binding.imageDocument.setImageResource(R.drawable.ic_black_document)
                binding.imageContact.setImageResource(R.drawable.ic_blue_contact)
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