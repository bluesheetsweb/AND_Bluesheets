package com.bluesheets.ui.chat.view

import android.content.Context
import android.content.Intent
import com.bluesheets.R
import io.getstream.chat.android.ui.message.MessageListActivity
import io.getstream.chat.android.ui.message.MessageListFragment

class CustomMessageListActivity : MessageListActivity() {

    override fun createMessageListFragment(cid: String, messageId: String?): MessageListFragment {
        return MessageListFragment.newInstance(cid) {
            setFragment(CustomMessageListFragment())
            showHeader(true)
            messageId(messageId)
        }
    }

    public companion object {
        private const val EXTRA_CID: String = "extra_cid"
        private const val EXTRA_MESSAGE_ID: String = "extra_message_id"

        @JvmStatic
        @JvmOverloads
        public fun createIntent(context: Context, cid: String, messageId: String? = null): Intent {
            return Intent(context, MessageListActivity::class.java).apply {
                putExtra(EXTRA_CID, cid)
                putExtra(EXTRA_MESSAGE_ID, messageId)
            }
        }
    }
}