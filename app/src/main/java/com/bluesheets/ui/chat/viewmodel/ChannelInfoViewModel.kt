package com.bluesheets.ui.chat.viewmodel

import android.content.DialogInterface
import androidx.lifecycle.MutableLiveData
import com.bluesheets.BluesheetApplication
import com.bluesheets.utils.SharedUtils
import com.bluesheets.utils.UserInfoUtil
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Member
import io.getstream.chat.android.client.models.User
import src.wrapperutil.utilities.Toaster
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class ChannelInfoViewModel: ParentVM() {

    private lateinit var channel: Channel

    var isEditing: MutableLiveData<Boolean> = MutableLiveData(false)
    var isSearch: MutableLiveData<Boolean> = MutableLiveData(false)
    var isToShowSearch: MutableLiveData<Boolean> = MutableLiveData(false)
    var isDisabled: MutableLiveData<Boolean> = MutableLiveData(false)
    var participants: MutableLiveData<String> = MutableLiveData("")
    var listUsers: MutableLiveData<MutableList<Member>> = MutableLiveData()
    var canEditing: Boolean = false
    var channelImage: String = ""
    var channelName: String = ""
    var channelSubDes: String = ""
    var isChannelAdmin: Boolean = false
    var isOneToOne: Boolean = false
    var deleteButtonText: String = "Delete"
    var editButtonText: String = "Edit"
    var channelThumb: Int = 0
    var adminId: String = ""
    private var tempMembers: MutableList<Member> = mutableListOf()
    var refreshChannel: MutableLiveData<Boolean> = MutableLiveData(false)
    private var newName: String = ""

    fun initChannel(channel: Channel) {
        this.channel = channel
        isChannelAdmin = channel.createdBy.id == UserInfoUtil.getChatId()
        adminId = channel.createdBy.id
        updateMembersList()
        if (SharedUtils.isTypeGroup(channel)) {
            isOneToOne = false
            canEditing = isChannelAdmin
            channelImage = channel.image
            channelName = channel.name
            participants.value = "${channel.memberCount} Participants"
            isToShowSearch.value = true
            deleteButtonText = if (isChannelAdmin) {
                "Delete"
            } else {
                "Leave"
            }
        } else {
            isToShowSearch.value = false
            isOneToOne = true
            canEditing = false
            channelName = SharedUtils.getChannelName(channel)
            channelImage = SharedUtils.getChannelImageUrl(channel)
        }
        channelSubDes = SharedUtils.getSubMessage(channel)
        channelThumb = SharedUtils.getChannelThumb(channel)
        newName = channelName
    }

    private fun updateMembersList(){
        tempMembers = mutableListOf()
        for (member in channel.members) {
            if (member.getUserId() == adminId) {
                tempMembers.add(0,member)
            } else if (member.getUserId() == UserInfoUtil.getChatId()) {
                tempMembers.add(0,member)
            } else {
                tempMembers.add(member)
            }
        }
        listUsers.value = tempMembers
    }

    fun editChannel(){
        isEditing.value ?.let {
            if (it) {
                if (newName == channelName) {
                    SharedUtils.showOKDialog(
                        message = "Please change Channel Name"
                    )
                } else {
                    SharedUtils.updateChannelName(channel, newName){
                        if (it.isNotEmpty()){
                            channelName = it
                            refreshChannel.value = true
                            cancelEditing()
                        }
                    }
                }
            } else {
                editButtonText = "Update"
                isEditing.value = true
            }
        }
    }

    fun cancelEditing() {
        editButtonText = "Edit"
        isEditing.value = false
    }

    fun searchParticipants(){
        isSearch.value ?.let {
            if (it) {
                isSearch.value = false
                isToShowSearch.value = true
            } else {
                isSearch.value = true
                isToShowSearch.value = false
            }
        }
    }

    fun changePic() {

    }

    fun editChannelName(s: CharSequence) {
        newName = s.toString()
        isDisabled.value = s.isEmpty()
    }

    fun searchParticipant(s: CharSequence) {
        if (s.isEmpty()) {
            listUsers.value = tempMembers
        } else {
                val filter = tempMembers.filter {
                    SharedUtils.getUserName(it.user).lowercase().contains(s.toString().lowercase())
                }
                listUsers.value = filter?.toMutableList()
            }
    }

    fun removeUserFromChannel(member: Member){
        SharedUtils.showYESNODialog(
            title = "Remove",
            message = "Do you sure want to remove this Participant",
            okButtonText = "Remove",
            listenerPositive = { dialog, which ->
                when(which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        Toaster.show(BluesheetApplication.instance, "YES")
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        Toaster.show(BluesheetApplication.instance, "NO")
                    }
                }
            }
        )
    }

    fun deleteOrLeaveChannel(){
        var title = if (isChannelAdmin) {
            "Delete"
        } else {
            "Leave"
        }
        var desc = if (isChannelAdmin) {
            "Do you sure want to delete this channel?"
        } else {
            "Do you sure want to leave this channel?"
        }
        SharedUtils.showYESNODialog(
            title = title,
            message = desc,
            okButtonText = title,
            listenerPositive = { dialog, which ->
                when(which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        Toaster.show(BluesheetApplication.instance, "YES")
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        Toaster.show(BluesheetApplication.instance, "NO")
                    }
                }
            }
        )
    }

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }
}