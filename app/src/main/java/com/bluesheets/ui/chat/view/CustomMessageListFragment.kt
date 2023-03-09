package com.bluesheets.ui.chat.view

import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.message.MessageListFragment
import io.getstream.chat.android.ui.message.input.MessageInputView
import io.getstream.chat.android.ui.message.list.MessageListView
import io.getstream.chat.android.ui.message.list.header.MessageListHeaderView
import io.getstream.chat.android.ui.message.list.header.viewmodel.MessageListHeaderViewModel
import io.getstream.chat.android.ui.message.list.header.viewmodel.bindView

class CustomMessageListFragment : MessageListFragment() {

    override fun setupMessageListHeader(messageListHeaderView: MessageListHeaderView) {
        val customHeader = CustomChannelMessageHeader(context = requireContext())
        with(customHeader) {
            isVisible = true
//            if (showHeader) {
//                messageListHeaderViewModel.bindView(this, viewLifecycleOwner)
//
////                setBackButtonClickListener {
////                    messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
////                }
//            } else {
//                isVisible = false
//            }
        }


//        super.setupMessageListHeader(CustomChannelMessageHeader())

        // Customize message list header view. For example, set a custom back button click listener:
//        messageListHeaderView.setBackButtonClickListener {
//            // Handle back press
//        }
    }

    override fun setupMessageList(messageListView: MessageListView) {
        super.setupMessageList(messageListView)
        // Customize message list view
    }

    override fun setupMessageInput(messageInputView: MessageInputView) {
        super.setupMessageInput(messageInputView)
        // Customize message input view
    }
}

//private fun MessageListHeaderViewModel.bindView(view: Any, lifecycle: LifecycleOwner) {
//
//}
