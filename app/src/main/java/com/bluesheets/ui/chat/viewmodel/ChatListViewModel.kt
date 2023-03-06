package com.bluesheets.ui.chat.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bluesheets.utils.ChatSharedClient
import com.bluesheets.utils.UserInfoUtil
import io.getstream.chat.android.client.api.models.QueryChannelsRequest
import io.getstream.chat.android.client.api.models.querysort.QuerySortByField
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Filters
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class ChatListViewModel: ParentVM() {

    var currentSelectedPos = MutableLiveData<Int>()
    var organizationName: String? = UserInfoUtil.organizationName
    var workSpaceName: String? = UserInfoUtil.workSpaceName

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun initChat(){
        ChatSharedClient.INSTANCE.connectUser()
    }

    fun getChannels() {
        val request = QueryChannelsRequest(
            filter = Filters.and(
                Filters.eq("type", "messaging"),
                Filters.`in`("members", listOf("thierry")),
            ),
            offset = 0,
            limit = 10,
            querySort = QuerySortByField.descByName("lastMessageAt")
        ).apply {
            watch = true
            state = true
        }

        ChatSharedClient.INSTANCE.client?.queryChannels(request)?.enqueue { result ->
            if (result.isSuccess) {
                val channels: List<Channel> = result.data()
            } else {
                // Handle result.error()
            }
        }
    }
}