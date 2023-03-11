package com.bluesheets.ui.user_info.viewmodel

import android.content.DialogInterface
import androidx.lifecycle.MutableLiveData
import com.bluesheets.BluesheetApplication
import com.bluesheets.ui.user_info.repository.EditUserInfoRepo
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo
import com.bluesheets.utils.SharedUtils
import com.bluesheets.utils.UserInfoUtil
import io.getstream.chat.android.client.models.Member
import src.networkutil.model.NetworkErrorBModel
import src.networkutil.network.NetworkRequest
import src.wrapperutil.utilities.Toaster
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class EditProfileViewModel : ParentVM() {

    init {
        repository = EditUserInfoRepo()
    }

    var userName: String? = UserInfoUtil.userName
    var userEmail: String? = UserInfoUtil.userEmail
    var navigateToEditProfile = MutableLiveData<Boolean>()

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun enableNavigationToEditProfile() {
        navigateToEditProfile.value = true
    }

    fun askForAccountDelete(){
        SharedUtils.showYESNODialog(
            title = " ",
            message = "Do you want to Delete the User",
            okButtonText = "Yes",
            listenerPositive = { dialog, which ->
                when(which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        deleteAccount()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        Toaster.show(BluesheetApplication.instance, "NO")
                    }
                }
            }
        )
    }

    fun askForLogOut(){
        SharedUtils.showYESNODialog(
            title = "Are you sure you want to log out?",
            message = "We are moving you to the Sign In screen if you log out...",
            okButtonText = "Log Out",
            listenerPositive = { dialog, which ->
                when(which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        logOutClicked()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        Toaster.show(BluesheetApplication.instance, "NO")
                    }
                }
            }
        )
    }

    fun logOutClicked() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        UserInfoUtil.authToken?.let {
            (repository as EditUserInfoRepo).userLogOut(it, object : NetworkRequest.IOnResponse {
                override fun onException(t: Throwable?) {
                    errorToastState.msg = "Logout Failed With Exception"
                    mProgressState.value =
                        WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
                }

                override fun onSuccess(
                    code: Int?,
                    message: String?,
                    data: Any?,
                    rawResponse: String?
                ) {
                    Toaster.show(BluesheetApplication.instance.applicationContext, "Logout Success")
                    clearPreferences()
                    mProgressState.value =
                        WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)
                    NavigateTo.screen(
                        activityType = FragmentConstant.SIGN_UP_ACTIVITY
                    )
                }

                override fun onFailed(
                    code: Int?,
                    message: String?,
                    data: Any?,
                    rawResponse: String?
                ) {
                    errorToastState.msg = "Log Out Failed"

                    mProgressState.value =
                        WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
                }
            })
        }
    }

    fun clearPreferences() {
        UserInfoUtil.isLogin = false
        UserInfoUtil.workSpaceId = ""
        UserInfoUtil.workSpaceToken = ""
        UserInfoUtil.workSpaceName = ""
        UserInfoUtil.organizationName = ""
        UserInfoUtil.organizationId = ""
        UserInfoUtil.organizationToken = ""
        UserInfoUtil.authToken = ""
        UserInfoUtil.userId = ""
        UserInfoUtil.userName = ""
        UserInfoUtil.chatToken = ""
        UserInfoUtil.workSpaceLOGO = ""
        UserInfoUtil.userProfileImage = ""
        UserInfoUtil.userEmail = ""
    }

    fun deleteAccount() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        UserInfoUtil.authToken?.let {
            (repository as EditUserInfoRepo).deleteAccount(object : NetworkRequest.IOnResponse {
                override fun onException(t: Throwable?) {
                    errorToastState.msg = "Something went wrong"
                    mProgressState.value =
                        WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
                }

                override fun onSuccess(
                    code: Int?,
                    message: String?,
                    data: Any?,
                    rawResponse: String?
                ) {
                    Toaster.show(
                        BluesheetApplication.instance.applicationContext,
                        message.toString()
                    )

                    mProgressState.value =
                        WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)

                }

                override fun onFailed(
                    code: Int?,
                    message: String?,
                    data: Any?,
                    rawResponse: String?
                ) {
                    if (data != null && data is NetworkErrorBModel) {
                        data.message?.let {
                            errorToastState.msg = it
                        }
                    } else {
                        errorToastState.msg = it
                    }

                    mProgressState.value =
                        WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
                }
            })
        }
    }
}