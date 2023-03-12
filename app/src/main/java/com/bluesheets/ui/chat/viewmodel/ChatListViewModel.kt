package com.bluesheets.ui.chat.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bluesheets.utils.ChatSharedClient
import com.bluesheets.utils.SharedUtils
import com.bluesheets.utils.UserInfoUtil
import io.getstream.chat.android.client.api.models.QueryChannelsRequest
import io.getstream.chat.android.client.api.models.querysort.QuerySortByField
import io.getstream.chat.android.client.events.*
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.socket.SocketListener
import src.wrapperutil.utilities.LogUtil
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class ChatListViewModel: ParentVM() {

    var allChannels: MutableLiveData<MutableList<Channel>> = MutableLiveData()
    var isSearching: Boolean = false
    private var lastSize = 0
    private var tempChannels: MutableLiveData<MutableList<Channel>> = MutableLiveData()

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun initChat(){
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        TAG = ChatListViewModel::class.java.simpleName
        ChatSharedClient.INSTANCE.connectUser { status ->
            if (status) {
                getChannels()
            }
        }
        ChatSharedClient.INSTANCE.client?.addSocketListener(object : SocketListener() {
            override fun onEvent(event: ChatEvent) {
                when (event) {
                    is MemberAddedEvent -> {
                        updateListItem()
                    }
                    is ChannelDeletedEvent -> {
                        updateListItem()
                    }
                    is ChannelUpdatedEvent -> {
                        updateListItem()
                    }
                    is NotificationAddedToChannelEvent -> {
                        updateListItem()
                    }
                    is NotificationRemovedFromChannelEvent -> {
                        updateListItem()
                    }
                    else -> {
                    }
                }
            }
        })
    }

    fun updateListItem(){
        getChannels()
    }

    fun getChannels() {
        var members: MutableList<Any> = mutableListOf()
        members.add(UserInfoUtil.getChatId())
        val request = QueryChannelsRequest(
            filter = Filters.and(
                Filters.`in`("members", members),
            ),
            offset = 0,
            limit = 50,
            querySort = QuerySortByField.descByName("lastMessageAt")
        ).apply {
            watch = true
            state = true
        }

        ChatSharedClient.INSTANCE.client?.queryChannels(request)?.enqueue { result ->
            if (result.isSuccess) {
                filterChannel(false, result.data())
                lastSize = 50
                loadMoreChannels()
            } else {
                errorToastState.msg = "Failed to Fetch Channels"
                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
            }
        }
    }

    fun loadMoreChannels() {
        val offset: Int? = allChannels.value?.count()
        offset ?.let {
            if (offset == lastSize) {
                var members: MutableList<Any> = mutableListOf()
                members.add(UserInfoUtil.getChatId())
                val request = QueryChannelsRequest(
                    filter = Filters.and(
                        Filters.`in`("members", members),
                    ),
                    offset = offset,
                    limit = 50,
                    querySort = QuerySortByField.descByName("lastMessageAt")
                ).apply {
                    watch = true
                    state = true
                }

                ChatSharedClient.INSTANCE.client?.queryChannels(request)?.enqueue { result ->
                    mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)
                    if (result.isSuccess) {
                        lastSize += 50
                        filterChannel(true, result.data())
                    } else {

                    }
                }
            } else {
                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)
                LogUtil.e(TAG, "no Pagination Required offset $offset and lastSize is $lastSize")
            }
        }
    }


    private fun filterChannel(isToAdd: Boolean, channels: List<Channel>){
        val filter = channels.filter {
            SharedUtils.isChannelMember(it)
        }
        if (isToAdd) {
            allChannels.value?.addAll(filter)
            tempChannels.value?.addAll(filter)
        } else {
            allChannels.value = filter.toMutableList()
            tempChannels.value = filter.toMutableList()
        }
    }

    fun getChannelWithName(s: CharSequence) {
        val text  = s.toString()
        print("get name starts with $text")
        if (text.isEmpty()) {
            allChannels.value = tempChannels.value
            isSearching = false
        }
        else {
            isSearching = true
            val filter = tempChannels.value?.filter {
                it.name.lowercase().contains(text.lowercase())
            }
            if (filter != null) {
                allChannels.value = filter.toMutableList()
            }
        }
    }
}