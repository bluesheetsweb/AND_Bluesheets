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

class ChannelAddMoreViewModel: ParentVM() {

    private lateinit var channel: Channel

    var isDisabled: MutableLiveData<Boolean> = MutableLiveData(false)
    var listNewUsers: MutableLiveData<MutableList<ConnectionUserModel>> = MutableLiveData()
    var tempMembers: MutableList<ConnectionUserModel> = mutableListOf()
    var refreshChannel: MutableLiveData<Boolean> = MutableLiveData(false)
    private var listSelected: MutableSet<String> = mutableSetOf()

    fun getChannel(cid: String){
        SharedUtils.getChannel(cid){
            initChannel(it)
        }
    }

    fun initChannel(channel: Channel) {
        this.channel = channel
        repository = ChatRepo()
        getList()
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
                updateMembersList(listUsers)
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

    private fun updateMembersList(listUsers: List<ConnectionUserModel>){
        SharedUtils.getChannelMembers(channel.cid){
            tempMembers = mutableListOf()
            for (member in listUsers) {
                var isAlreadyIn: Boolean = false
                for (current in it) {
                    if (current.user.id == member.id.toString()) {
                        isAlreadyIn = true
                        break
                    }
                }
                if (!isAlreadyIn) {
                    tempMembers.add(member)
                }
            }
            listNewUsers.value = tempMembers
            mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)
            }
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

    fun isSelected(member: ConnectionUserModel): Boolean{
        return listSelected.contains(member.getStreamId())
    }

    fun addParticipants(){

    }

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }
}