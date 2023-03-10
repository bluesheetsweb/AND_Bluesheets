package com.bluesheets.utils

import android.content.Context
import android.util.Log
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.offline.model.message.attachments.UploadAttachmentsNetworkType
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.ui.ChatUI
import io.getstream.chat.android.ui.SupportedReactions

class ChatSharedClient private constructor() {

    val TAG = ChatSharedClient::class.java.simpleName
    var context: Context? = null

    companion object {
        val INSTANCE: ChatSharedClient by lazy { ChatSharedClient() }
    }

    fun initContext(context: Context?) {
        Log.e(TAG, " initContext called " + context)
        if (context != null) {
            this.context = context
            client = ChatClient.Builder(apiKey, context)
                // Change log level
                .logLevel(ChatLogLevel.ALL)
                .withPlugin(getOfflinePlugin())
                .build()

//            ChatUI.supportedReactions = SupportedReactions(context, mapOf())
        }
    }

    val apiKey = "nuh3h79w2gv2"

    // Step 1 - Set up the OfflinePlugin for offline storage
    fun getOfflinePlugin(): StreamOfflinePluginFactory {
    val offlinePluginFactory = StreamOfflinePluginFactory(
        config = Config(
            backgroundSyncEnabled = true,
            userPresence = true,
            persistenceEnabled = true,
            uploadAttachmentsNetworkType = UploadAttachmentsNetworkType.NOT_ROAMING,
        ),
        appContext = context!!,
    )
    return offlinePluginFactory
}

    // Step 2 - Set up the client, together with offline plugin, for API calls
    var client: ChatClient? = null

    fun connectUser(onResponse: (Boolean) -> Unit) {
        val user = User(
            id = UserInfoUtil.getChatId(),
            name = "",
            image = "",
        )
        client?.connectUser(
            user = user,
            token = UserInfoUtil.chatToken, // or client.devToken(userId); if auth is disabled for your app
        )?.enqueue { result ->
            if (result.isSuccess) {
                onResponse(true)
            } else {
                onResponse(false)
            }
        }
    }
}