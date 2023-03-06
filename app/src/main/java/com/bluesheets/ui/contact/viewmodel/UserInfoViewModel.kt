package com.bluesheets.ui.contact.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bluesheets.BluesheetApplication
import com.bluesheets.ui.contact.repository.UserInfoRepo
import com.bluesheets.ui.signup.model.OrganizationItem
import com.bluesheets.ui.signup.model.SignInModel
import com.bluesheets.ui.signup.model.WorkspaceItem
import com.bluesheets.ui.signup.repository.SignInRepo
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo
import com.bluesheets.utils.UserInfoUtil
import com.google.gson.Gson
import org.json.JSONObject
import src.networkutil.model.NetworkErrorBModel
import src.networkutil.network.NetworkRequest
import src.wrapperutil.utilities.Toaster
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class UserInfoViewModel : ParentVM() {

    init {
        repository = UserInfoRepo()
    }

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun logOutClicked() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        UserInfoUtil.authToken?.let {
            (repository as UserInfoRepo).userLogOut(it, object : NetworkRequest.IOnResponse {
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

                    mProgressState.value =
                        WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)
                    NavigateTo.screen(
                        activityType = FragmentConstant.HOME_ACTIVITY
                    )
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
                        errorToastState.msg = "Logout Failed"
                    }

                    mProgressState.value =
                        WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
                }
            })
        }
    }

    fun deleteAccount() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        UserInfoUtil.authToken?.let {
            (repository as UserInfoRepo).userLogOut(it, object : NetworkRequest.IOnResponse {
                override fun onException(t: Throwable?) {
                    errorToastState.msg = "Login Failed With Exception"
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