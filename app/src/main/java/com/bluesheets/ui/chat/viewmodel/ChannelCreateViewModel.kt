package com.bluesheets.ui.chat.viewmodel

import android.content.DialogInterface
import androidx.lifecycle.MutableLiveData
import com.bluesheets.BluesheetApplication
import com.bluesheets.ui.chat.model.ConnectionUserModel
import com.bluesheets.ui.chat.repo.ChatRepo
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
import src.wrapperutil.utilities.Toaster
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class ChannelCreateViewModel: ParentVM() {

    var isDisabled: MutableLiveData<Boolean> = MutableLiveData(true)
    var listNewUsers: MutableLiveData<MutableList<ConnectionUserModel>> = MutableLiveData()
    var tempMembers: MutableList<ConnectionUserModel> = mutableListOf()
    var refreshChannel: MutableLiveData<Boolean> = MutableLiveData(false)
    private var listSelected: MutableSet<String> = mutableSetOf()
    private var isGroup: Boolean = false

    fun initData() {
        repository = ChatRepo()
        getList()
    }

    fun initOrg(isGroup: Boolean) {
        this.isGroup = isGroup
    }

    private fun getList() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        (repository as ChatRepo).getUsers(object : NetworkRequest.IOnResponse{
            override fun onException(t: Throwable?) {
                errorToastState.msg = "Failed With Exception"
                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
            }

            override fun onSuccess(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                val listUsers: List<ConnectionUserModel> = Gson().fromJson(rawResponse , Array<ConnectionUserModel>::class.java).toList()
                tempMembers = listUsers.toMutableList()
                listNewUsers.value = tempMembers
            }

            override fun onFailed(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                if (data!=null && data is NetworkErrorBModel) {
                    data.message?.let {
                        errorToastState.msg = it
                    }
                } else {
                    errorToastState.msg = "Failed to load users."
                }

                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
            }
        })
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
        if (listSelected.contains(member.getStreamId())) {
            listSelected.remove(member.getStreamId())
        } else {
            listSelected.add(member.getStreamId())
        }

        isDisabled.value = listSelected.isEmpty()
    }

    fun getSelectedList(): MutableSet<String> {
        return listSelected
    }

    fun addParticipants(){

    }

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }
}