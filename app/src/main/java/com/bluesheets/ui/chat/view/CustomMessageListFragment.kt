package com.bluesheets.ui.chat.view

import android.os.Bundle
import android.view.ViewGroup
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo
import com.bluesheets.utils.SharedUtils
import com.getstream.sdk.chat.adapter.MessageListItem
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.ui.message.MessageListFragment
import io.getstream.chat.android.ui.message.input.MessageInputView
import io.getstream.chat.android.ui.message.list.MessageListView
import io.getstream.chat.android.ui.message.list.adapter.BaseMessageItemViewHolder
import io.getstream.chat.android.ui.message.list.adapter.MessageListItemViewHolderFactory
import io.getstream.chat.android.ui.message.list.header.MessageListHeaderView
import java.util.*
import java.util.concurrent.TimeUnit

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
                val bundle = Bundle()
                bundle.putString("cId", it.cid)
                NavigateTo.screen(FragmentConstant.CHAT_OTHER_ACTIVITY, FragmentConstant.CHAT_INFO_FRAGMENT, bundle)
            }
        }

        messageListHeaderView.setSubtitleClickListener{
            channel ?.let {
                val bundle = Bundle()
                bundle.putString("cId", it.cid)
                NavigateTo.screen(FragmentConstant.CHAT_OTHER_ACTIVITY, FragmentConstant.CHAT_INFO_FRAGMENT, bundle)
            }
        }
    }

    override fun setupMessageList(messageListView: MessageListView) {
        super.setupMessageList(messageListView)
        // Customize message list view
        messageListView.setReactionsEnabled(false)
        messageListView.setMessageFlagEnabled(false)
        messageListView.setShowAvatarPredicate(MessageListView.ShowAvatarPredicate { _ ->
            false
        })
//        messageListView.set
    }

    override fun setupMessageInput(messageInputView: MessageInputView) {
        super.setupMessageInput(messageInputView)
        // Customize message input view
    }
}
