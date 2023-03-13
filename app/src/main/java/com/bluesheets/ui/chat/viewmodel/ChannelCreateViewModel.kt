package com.bluesheets.ui.chat.viewmodel

import android.content.DialogInterface
import androidx.lifecycle.MutableLiveData
import com.bluesheets.BluesheetApplication
import com.bluesheets.ui.chat.model.ConnectionUserModel
import com.bluesheets.ui.chat.model.OrgTagModel
import com.bluesheets.ui.chat.model.TagsModel
import com.bluesheets.ui.chat.repo.ChatRepo
import com.bluesheets.ui.chat.view.ChannelFollowUpFragment
import com.bluesheets.ui.chat.view.ChannelGroupFragment
import com.bluesheets.ui.chat.view.ChannelOneToOneFragment
import com.bluesheets.ui.signup.model.OrganizationItem
import com.bluesheets.ui.signup.model.SignInModel
import com.bluesheets.ui.signup.model.WorkspaceItem
import com.bluesheets.ui.signup.repository.SignInRepo
import com.bluesheets.utils.*
import com.google.gson.Gson
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.Member
import io.getstream.chat.android.client.models.User
import org.json.JSONObject
import src.networkutil.model.NetworkErrorBModel
import src.networkutil.network.NetworkRequest
import src.networkutil.utilities.NetworkConstant
import src.networkutil.utilities.NetworkSharedPrefUtils
import src.wrapperutil.utilities.FragmentTransaction
import src.wrapperutil.utilities.Toaster
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM
import java.util.*
import kotlin.collections.HashMap

class ChannelCreateViewModel : ParentVM() {

    var isDisabled: MutableLiveData<Boolean> = MutableLiveData(true)
    var listNewUsers: MutableLiveData<MutableList<ConnectionUserModel>> = MutableLiveData()
    var tempMembers: MutableList<ConnectionUserModel> = mutableListOf()
    var refreshChannel: MutableLiveData<Boolean> = MutableLiveData(false)
    private var listSelected: MutableSet<String> = mutableSetOf()
    private var selectedUser: ConnectionUserModel? = null
    var selectedOrg: MutableLiveData<OrgTagModel?> = MutableLiveData(null)
    var selectedTag: MutableLiveData<TagsModel?> = MutableLiveData(null)
    private var groupName: String? = null
    private var isGroup: Boolean = false
    var listOrgTags: MutableList<OrgTagModel> = mutableListOf()
    var listTag: MutableList<TagsModel> = mutableListOf()

    fun initData(
        selectedOrg: OrgTagModel? = null,
        selectedTag: TagsModel? = null,
        groupName: String? = null
    ) {
        this.selectedOrg.value = selectedOrg
        this.selectedTag.value = selectedTag
        this.groupName = groupName
        isGroup = isValidGroupName()
        repository = ChatRepo()
        getList()
    }

    fun initOrg(isGroup: Boolean, selectedUser: ConnectionUserModel?) {
        repository = ChatRepo()
        this.selectedUser = selectedUser
        this.isGroup = isGroup
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        (repository as ChatRepo).getOrg(object : NetworkRequest.IOnResponse {
            override fun onException(t: Throwable?) {
                errorToastState.msg = "Failed With Exception"
                mProgressState.value =
                    WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
            }

            override fun onSuccess(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)
                val listOrgTag: List<OrgTagModel> =
                    Gson().fromJson(rawResponse, Array<OrgTagModel>::class.java).toList()
                listOrgTags = listOrgTag.toMutableList()
            }

            override fun onFailed(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                if (data != null && data is NetworkErrorBModel) {
                    data.message?.let {
                        errorToastState.msg = it
                    }
                } else {
                    errorToastState.msg = "Failed to load users."
                }

                mProgressState.value =
                    WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
            }
        })
    }

    private fun getList() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        (repository as ChatRepo).getUsers(object : NetworkRequest.IOnResponse {
            override fun onException(t: Throwable?) {
                errorToastState.msg = "Failed With Exception"
                mProgressState.value =
                    WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
            }

            override fun onSuccess(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)
                val listUsers: List<ConnectionUserModel> =
                    Gson().fromJson(rawResponse, Array<ConnectionUserModel>::class.java).toList()
                tempMembers = listUsers.toMutableList()
                listNewUsers.value = tempMembers
            }

            override fun onFailed(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                if (data != null && data is NetworkErrorBModel) {
                    data.message?.let {
                        errorToastState.msg = it
                    }
                } else {
                    errorToastState.msg = "Failed to load users."
                }

                mProgressState.value =
                    WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
            }
        })
    }

    fun enterGroupName(s: CharSequence) {
        groupName = s.toString()
        checkForEnablingButton()
    }

    fun searchParticipant(s: CharSequence) {
        if (s.isEmpty()) {
            listNewUsers.value = tempMembers
        } else {
            val filter = tempMembers.filter {
                it.username.lowercase().contains(s.toString().lowercase())
            }
            listNewUsers.value = filter?.toMutableList()
        }
    }

    fun selectParticipant(member: ConnectionUserModel) {
        if (isGroup) {
            if (listSelected.contains(member.getStreamId())) {
                listSelected.remove(member.getStreamId())
            } else {
                listSelected.add(member.getStreamId())
            }
            isDisabled.value = listSelected.isEmpty()
        } else {
            selectedUser = member
            FragmentTransaction.add(
                type = FragmentConstant.CHAT_OTHER_ACTIVITY,
                fragment = ChannelFollowUpFragment(false, selectedUser),
                true
            )
        }
    }

    fun checkForEnablingButton() {
        if (isGroup) {
            isDisabled.value =
                (selectedOrg.value == null || selectedTag.value == null || !isValidGroupName())
        } else {
            isDisabled.value = (selectedOrg.value == null || selectedTag.value == null)
        }
    }

    private fun isValidGroupName(): Boolean {
        if (groupName != null) {
            if (groupName?.trim()?.length!! >= 4) {
                return true
            }
        }
        return false
    }

    fun selectOrg(selectedOrg: Any) {
        if ((selectedOrg as OrgTagModel).id != (this.selectedOrg.value?.id)) {
            this.selectedOrg.value = selectedOrg
            this.selectedTag.value = null
            selectedOrg.userDocumentTypes?.let {
                listTag = it
            }
            checkForEnablingButton()
        }
    }

    fun selectTag(selectedTag: Any) {
        if ((selectedTag as TagsModel).id != (this.selectedTag.value?.id)) {
            this.selectedTag.value = selectedTag
            checkForEnablingButton()
        }
    }

    fun getSelectedList(): MutableSet<String> {
        return listSelected
    }

    fun createGroup() {
        if (isGroup) {
            FragmentTransaction.add(
                type = FragmentConstant.CHAT_OTHER_ACTIVITY,
                fragment = ChannelGroupFragment(selectedOrg.value, selectedTag.value, groupName),
                true
            )
        } else {
            mProgressState.value =
                WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
            var map: HashMap<String, Any> = hashMapOf()
            selectedTag.value?.id ?. let {
                map.put("TagId", it)
            }
            selectedTag.value?.name ?. let {
                map.put("TagName", it)
            }
            selectedOrg.value?.id ?. let {
                map.put("OrgId", it)
            }
            selectedOrg.value?.name ?. let {
                map.put("OrgName", it)
            }
            map.put("channelType", AppGlobalConstant.CHANNEL_TYPE_ONE_O_ONE)
            UserInfoUtil.userName ?. let {
                map.put("CreatorName", it)
            }
            UserInfoUtil.userId ?. let {
                map.put("CreatorId", it)
            }
            UserInfoUtil.userProfileImage ?. let {
                map.put("CreatorImage", it)
            }

            map.put("name", selectedUser!!.username)
            selectedUser?.profileimageurl ?. let {
                map.put("image", it)
            }

            listSelected.add(selectedUser!!.getStreamId())
            listSelected.add(UserInfoUtil.getChatId())
            ChatSharedClient.INSTANCE.client?.createChannel(
                channelType = "messaging",
                channelId = "",
                memberIds = listSelected.toList(),
                extraData = map
            )?.enqueue { result ->

                mProgressState.value =
                    WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)
                if (result.isSuccess) {
                    val channel = result.data()
                    refreshChannel.value = true
                } else {
                    SharedUtils.showOKDialog(message = "Unable to create channel with the following members. please try again")
                    // Handle result.error()
                }
            }
        }
    }

    fun processGroup() {
        mProgressState.value =
            WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        var map: HashMap<String, Any> = hashMapOf()
        selectedTag.value?.id ?. let {
            map.put("TagId", it)
        }
        selectedTag.value?.name ?. let {
            map.put("TagName", it)
        }
        selectedOrg.value?.id ?. let {
            map.put("OrgId", it)
        }
        selectedOrg.value?.name ?. let {
            map.put("OrgName", it)
        }
        map.put("channelType", AppGlobalConstant.CHANNEL_TYPE_GROUP)
        map.put("name", groupName!!)
        map.put("image", "https://t4.ftcdn.net/jpg/04/14/11/09/360_F_414110983_lH04zN9sFmiHFOpD2W80r32QGn0meiaF.jpg")
        listSelected.add(UserInfoUtil.getChatId())

        ChatSharedClient.INSTANCE.client?.createChannel(
            channelType = "messaging",
            channelId = UUID.randomUUID().toString(),
            memberIds = listSelected.toList(),
            extraData = map
        )?.enqueue { result ->

            mProgressState.value =
                WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)
            if (result.isSuccess) {
                val channel = result.data()
                refreshChannel.value = true
            } else {
                SharedUtils.showOKDialog(message = "Unable to create channel with the following members. please try again")
            }
        }
        Toaster.show(BluesheetApplication.instance.applicationContext, "Create Group")
    }

    fun createGroupProcess() {
        FragmentTransaction.add(
            type = FragmentConstant.CHAT_OTHER_ACTIVITY,
            fragment = ChannelFollowUpFragment(true),
            true
        )
    }

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }
}