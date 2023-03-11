package com.bluesheets.ui.contact.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bluesheets.BluesheetApplication
import com.bluesheets.ui.contact.repository.UserInfoRepo
import com.bluesheets.ui.signup.model.SignInModel
import com.bluesheets.utils.FragmentConstant
import com.bluesheets.utils.NavigateTo
import com.bluesheets.utils.UserInfoUtil
import com.google.gson.Gson
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
    var userName: String? = UserInfoUtil.userName
    var navigateToEditProfile = MutableLiveData<Boolean>()

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun enableNavigationToEditProfile() {
        navigateToEditProfile.value = true
    }

    fun getUserInfo() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        UserInfoUtil.userId?.let {
            (repository as UserInfoRepo).userInfoRepo(it, object : NetworkRequest.IOnResponse {
                override fun onException(t: Throwable?) {
                    errorToastState.msg = "Something went wrong!"
                    mProgressState.value =
                        WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
                }

                override fun onSuccess(
                    code: Int?,
                    message: String?,
                    data: Any?,
                    rawResponse: String?
                ) {

                    var user = Gson().fromJson(rawResponse, SignInModel::class.java)
                    UserInfoUtil.userEmail = user.email
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
                        errorToastState.msg = message
                    }

                    mProgressState.value =
                        WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
                }
            })
        }
    }

}