package com.bluesheets.ui.chat.view

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.bluesheets.utils.SharedUtils
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.ui.message.MessageListFragment
import io.getstream.chat.android.ui.message.input.MessageInputView
import io.getstream.chat.android.ui.message.list.MessageListView
import io.getstream.chat.android.ui.message.list.header.MessageListHeaderView
import io.getstream.chat.android.ui.message.list.header.viewmodel.MessageListHeaderViewModel
import io.getstream.chat.android.ui.message.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory

class CustomMessageListFragment : MessageListFragment() {
    private var channel: Channel? = null
    override fun setupMessageListHeader(messageListHeaderView: MessageListHeaderView) {
        super.setupMessageListHeader(messageListHeaderView)
//         Customize message list header view. For example, set a custom back button click listener:
        messageListHeaderView.setBackButtonClickListener {
            // Handle back press
            activity?.finish()
        }
        messageListHeaderViewModel.channel.observe(viewLifecycleOwner) {
            channel = it
            messageListHeaderView.setTitle(SharedUtils.getChannelName(it))
            messageListHeaderView.setOnlineStateSubtitle(SharedUtils.getSubMessage(it))
        }

        messageListHeaderView.setTitleClickListener{
            channel ?.let {
                var bottomSheet = ChannelInfoFragment(it)
                bottomSheet.show(childFragmentManager,ChannelInfoFragment::class.java.simpleName)
            }
        }

        messageListHeaderView.setSubtitleClickListener{
            channel ?.let {
                var bottomSheet = ChannelInfoFragment(it)
                bottomSheet.show(childFragmentManager,ChannelInfoFragment::class.java.simpleName)
            }
        }
    }

    override fun setupMessageList(messageListView: MessageListView) {
        super.setupMessageList(messageListView)
        // Customize message list view
        messageListView.setReactionsEnabled(false)
        messageListView.setMessageFlagEnabled(false)
    }

    override fun setupMessageInput(messageInputView: MessageInputView) {
        super.setupMessageInput(messageInputView)
        // Customize message input view
    }
}

//private fun MessageListHeaderViewModel.bindView(view: Any, lifecycle: LifecycleOwner) {
//
//}
