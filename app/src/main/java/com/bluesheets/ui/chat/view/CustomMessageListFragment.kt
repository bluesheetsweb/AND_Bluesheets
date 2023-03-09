package com.bluesheets.ui.chat.view

import io.getstream.chat.android.ui.message.MessageListFragment
import io.getstream.chat.android.ui.message.input.MessageInputView
import io.getstream.chat.android.ui.message.list.MessageListView
import io.getstream.chat.android.ui.message.list.header.MessageListHeaderView

class CustomMessageListFragment : MessageListFragment() {

    override fun setupMessageListHeader(messageListHeaderView: MessageListHeaderView) {
        super.setupMessageListHeader(messageListHeaderView)
        // Customize message list header view. For example, set a custom back button click listener:
        messageListHeaderView.setBackButtonClickListener {
            // Handle back press
        }
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
