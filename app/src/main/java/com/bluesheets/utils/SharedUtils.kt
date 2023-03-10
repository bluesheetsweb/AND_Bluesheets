package com.bluesheets.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.bluesheets.BluesheetApplication
import com.bluesheets.R
import com.bluesheets.ui.chat.model.ChannelExtraData
import io.getstream.chat.android.client.api.models.QueryChannelsRequest
import io.getstream.chat.android.client.api.models.querysort.QuerySortByField
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.Member
import io.getstream.chat.android.client.models.User
import src.wrapperutil.listener.OnYesNoClickListener

object SharedUtils {

    fun getSubMessage(channel: Channel?): String {
        var subMessage: String = ""
        channel?.let {
            if (channel.extraData.isNotEmpty()) {
                val orgName = channel.extraData["OrgName"].toString()
                val tagName = channel.extraData["TagName"].toString()
                if ((orgName.isEmpty() && tagName.isEmpty()) || (orgName.contains("null") && tagName.contains("null"))) {
                    subMessage = ""
                } else {
                    subMessage = "$orgName | $tagName"
                }
            }
        }
        return subMessage
    }

    fun getChannelImageUrl(channel: Channel?): String {
        var imageUrl: String = ""
        channel?.let {
            if (isTypeGroup(channel)) {
                imageUrl = channel.image
            } else {
                if (channel.extraData.isNotEmpty()) {
                    val userId = channel.extraData["CreatorId"].toString()
                    if (userId == UserInfoUtil.userId) {
                        imageUrl = channel.image
                    } else {
                        imageUrl = channel.extraData["CreatorImage"].toString()
                    }
                }
            }
            if (imageUrl.isEmpty() || imageUrl.contains("null")) {
                imageUrl = channel.image
            }
        }
        return imageUrl
    }

    fun getChannelThumb(channel: Channel?): Int {
        var imageThumb = R.drawable.ic_profile_thumb
        channel?.let {
            if (isTypeGroup(channel)) {
                imageThumb = R.drawable.ic_group_thumbnail
            } else {
                imageThumb = R.drawable.ic_profile_thumb
            }
        }
        return imageThumb
    }

    fun getUserName(user: User): String {
        if (user.id == UserInfoUtil.getChatId()) {
            return "You"
        } else {
            return if (user.name.isEmpty()) {
                return user.extraData["username"].toString()
            } else {
                user.name
            }
        }
    }

    fun getChannelName(channel: Channel?): String {
        var channelName: String = ""
        channel?.let {
            if (isTypeGroup(channel)) {
                channelName = channel.name
            } else {
                if (channel.extraData.isNotEmpty()) {
                    val userId = channel.extraData["CreatorId"].toString()
                    if (userId == UserInfoUtil.userId) {
                        channelName = channel.name
                    } else {
                        channelName = channel.extraData["CreatorName"].toString()
                    }
                }
            }
            if (channelName.isEmpty() || channelName.contains("null")) {
                channelName = channel.name
            }
        }
        return channelName
    }

    fun isTypeGroup(channel: Channel?): Boolean {
        var isGroup = false
        channel?.let {
            if (channel.extraData.isNotEmpty()) {
                val type = channel.extraData["channelType"].toString()
                if (type.isNotEmpty()) {
                    if (type == AppGlobalConstant.CHANNEL_TYPE_ONE_O_ONE) {
                        isGroup = false
                    } else if (type == AppGlobalConstant.CHANNEL_TYPE_GROUP) {
                        isGroup = true
                    }
                }
            }
        }
        return isGroup
    }

    fun getOtherData(channel: Channel?): Triple<String, String, String> {
        var userId: String = ""
        var userName: String = ""
        var imageUrl: String = ""
        channel?.let {
            if (channel.extraData.isNotEmpty()) {
                userId = channel.extraData["CreatorId"].toString()
                userName = channel.extraData["CreatorName"].toString()
                imageUrl = channel.extraData["CreatorImage"].toString()
            }
        }
        return Triple(userId, userName, imageUrl)
    }

    fun getUserNameFromChannel(channel: Channel, currentUserId: String?): String {
//        for (user in channel.members){
//            if (user.getUserId() != (currentUserId ?? "")) {
//                return getUserName(user)
//            }
//        }
        return ""
    }

    fun getChannelExtraData(channel: Channel): ChannelExtraData {
        var extraData = ChannelExtraData()
        if (channel.extraData.isNotEmpty()) {
            extraData.orgName = channel.extraData["OrgName"].toString()
            extraData.orgId = channel.extraData["OrgId"].toString()
            extraData.tagName = channel.extraData["TagName"].toString()
            extraData.tagId = channel.extraData["TagId"].toString()
        }
        return extraData
    }

    fun updateChannelProfilePic(
        channel: Channel,
        profilePic: String,
        onChanged: ((String) -> Unit)
    ) {
        val controller = ChatSharedClient.INSTANCE.client?.channel(channel.cid)
        var extraData = channel.extraData
        extraData.put(
            "image", profilePic
        )
        extraData.put(
            "name", channel.name
        )
        controller?.update(
            extraData = extraData,
        )?.enqueue() { result ->
            if (result.isSuccess) {
                onChanged(profilePic)
            } else {
                onChanged(profilePic)
//                    showErrorPrompt(message: "Error while Removing Participant, Please try again after some time.")
            }
        }
    }

    fun updateChannelName(channel: Channel, channelName: String, onChanged: ((String) -> Unit)) {
        val controller = ChatSharedClient.INSTANCE.client?.channel(channel.cid)
        var extraData = channel.extraData
        extraData.remove("name")
        extraData.put(
            "name", channelName
        )
        extraData.put(
            "image", channel.image
        )
        controller?.update(
            extraData = extraData,
        )?.enqueue() { result ->
            if (result.isSuccess) {
                onChanged(channelName)
            } else {
                onChanged(channelName)
//                    showErrorPrompt(message: "Error while Removing Participant, Please try again after some time.")
            }
        }
    }

    fun deleteChannel(channel: Channel, onResponse: ((Boolean) -> Unit)) {
        val controller = ChatSharedClient.INSTANCE.client?.channel(channel.cid)
        controller?.delete()?.enqueue() { result ->
            if (result.isSuccess) {
                onResponse(true)
            } else {
                onResponse(false)
//                    showErrorPrompt(message: "Error while Removing Participant, Please try again after some time.")
            }
        }
    }

    fun leaveChannel(channel: Channel?, onResponse: ((Boolean) -> Unit)) {
        channel?.let {
            val controller = ChatSharedClient.INSTANCE.client?.channel(channel.cid)

            controller?.removeMembers(listOf(UserInfoUtil.getChatId()))?.enqueue { result ->
                if (result.isSuccess) {
                    val channel: Channel = result.data()
                    onResponse(true)
                } else {
                    onResponse(false)
//                    showErrorPrompt(message: "Error while Removing Participant, Please try again after some time.")
                }
            }
        }
    }

    fun hideChannel(channel: Channel, onResponse: ((Boolean) -> Unit)) {
        val controller = ChatSharedClient.INSTANCE.client?.channel(channel.cid)
        controller?.hide(clearHistory = false)?.enqueue() { result ->
            if (result.isSuccess) {
                onResponse(true)
            } else {
                onResponse(false)
            }
        }
    }

    fun addParticipantsToChannel(
        channel: Channel, participants: List<String>, onChanged: ((Boolean) -> Unit)
    ) {
        val controller = ChatSharedClient.INSTANCE.client?.channel(channel.cid)
        controller?.addMembers(participants)?.enqueue() { result ->
            if (result.isSuccess) {
                val channel: Channel = result.data()
                onChanged(true)
            } else {
                onChanged(false)
//                    showErrorPrompt(message: "Error while Removing Participant, Please try again after some time.")
            }
        }
    }

    fun removeParticipantFromChannel(
        channel: Channel?, userId: String, onChanged: ((Boolean) -> Unit)
    ) {
        channel?.let {
            val controller = ChatSharedClient.INSTANCE.client?.channel(channel.cid)

            controller?.removeMembers(listOf(userId))?.enqueue { result ->
                if (result.isSuccess) {
                    val channel: Channel = result.data()
                    onChanged(true)
                } else {
                    onChanged(false)
//                    showErrorPrompt(message: "Error while Removing Participant, Please try again after some time.")
                }
            }
        }
    }

    fun isChannelMember(channel: Channel?): Boolean {
        channel?.let {
            if (channel.membership == null) {
                return false
            } else {
                channel.membership?.let {
                    return it.banned.not()
                }
            }
        }
        return false
    }

    var builder: AlertDialog? = null
    fun showYESNODialog(
        title: String? = "",
        message: String? = "",
        okButtonText: String? = "Ok",
        cancelButtonText: String? = "Cancel",
        context: Context? = BluesheetApplication.instance.activityLifeCycle.currentActivity,
        listenerPositive: DialogInterface.OnClickListener? = null,
        listenerNegative: DialogInterface.OnClickListener? = null
    ) {
        if (message == null || message.isNullOrBlank())
            return
        try {
            if (builder != null) {
                if (builder?.isShowing!!) {
                    builder?.dismiss()
                }
                builder = null
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        try {
            context ?. let {
                builder = AlertDialog.Builder(it).create()
                builder?.setTitle(title)
                builder?.setMessage(message)
                builder?.setButton(AlertDialog.BUTTON_POSITIVE, okButtonText, listenerPositive)
                builder?.setButton(AlertDialog.BUTTON_NEGATIVE, cancelButtonText, listenerNegative)
//        builder?.setPositiveButton(okButtonText, dialogClickListener)
                builder?.show()

            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }


    fun showOKDialog(
        title: String? = "",
        message: String? = "",
        okButtonText: String? = "Ok",
        context: Context? = BluesheetApplication.instance.activityLifeCycle.currentActivity,
        listener: DialogInterface.OnClickListener? = null
    ) {
        if (message == null || message.isNullOrBlank())
            return
        try {
            if (builder != null) {
                if (builder?.isShowing!!) {
                    builder?.dismiss()
                }
                builder = null
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        try {
            context ?. let {
                builder = AlertDialog.Builder(it).create()
                builder?.setTitle(title)
                builder?.setMessage(message)
                builder?.setButton(AlertDialog.BUTTON_POSITIVE, okButtonText, listener)
            }
//        builder?.setPositiveButton(okButtonText, dialogClickListener)
            builder?.show()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    fun getChannel(cid: String, onReceived: ((Channel) -> Unit)) {
        val filter = Filters.and(
            Filters.eq("cid", cid))
            val offset = 0
        val limit = 1
        val request = QueryChannelsRequest(filter, offset, limit)
        ChatSharedClient.INSTANCE.client?.queryChannels(request)?.enqueue { result ->
            if (result.isSuccess) {
                val channels = result.data()
                onReceived(channels[0])
            }
        }
    }

    fun getChannelMembers(cid: String,onReceived: ((List<Member>) -> Unit)) {
        val controller = ChatSharedClient.INSTANCE.client?.channel(cid)
        val filter = Filters.neutral()
        val offset = 0
        val limit = 100
        val sort = QuerySortByField<Member>()

        controller?.queryMembers(offset, limit, filter, sort)?.enqueue { result ->
            if (result.isSuccess) {
                val members: List<Member> = result.data()
                onReceived(members)
            } else {
            }
        }
    }
}