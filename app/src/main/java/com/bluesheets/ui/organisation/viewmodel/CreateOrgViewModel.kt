package com.bluesheets.ui.organisation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bluesheets.BluesheetApplication
import com.bluesheets.ui.organisation.model.OrganizationModel
import com.bluesheets.ui.organisation.repository.CreateOrgRepo
import com.bluesheets.ui.signup.model.OrganizationItem
import com.bluesheets.ui.signup.model.SignInModel
import com.bluesheets.ui.signup.repository.SignInRepo
import com.bluesheets.utils.AppUtils
import com.bluesheets.utils.UserInfoUtil
import com.google.gson.Gson
import org.json.JSONObject
import src.networkutil.model.NetworkErrorBModel
import src.networkutil.network.NetworkRequest
import src.wrapperutil.utilities.Toaster
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class CreateOrgViewModel : ParentVM() {

    private var orgName: String = ""
    private var description: String = ""
    private var workSpaceID: String? = UserInfoUtil.workSpaceId

    var buttonEnabled = MutableLiveData<Boolean>()

    init {
        repository = CreateOrgRepo()
    }

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun onNameTextChanged(s: CharSequence) {
        orgName = s.toString()
        checkSignInButtonEnablity()
    }

    private fun checkSignInButtonEnablity() {
        buttonEnabled.value = AppUtils.isValidNameLength(orgName)
    }

    fun createOrgClicked() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        workSpaceID?.let {
            (repository as CreateOrgRepo).createOrg(orgName, description, it, object : NetworkRequest.IOnResponse{
                override fun onException(t: Throwable?) {
                    errorToastState.msg = "Login Failed With Exception"
                    mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
                }

                override fun onSuccess(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                    Toaster.show(BluesheetApplication.instance.applicationContext, "Registration Success")
                    var organization = Gson().fromJson(rawResponse, OrganizationModel::class.java)
                    UserInfoUtil.organizationId = organization.id
                    UserInfoUtil.organizationName = organization.name
                    UserInfoUtil.organizationToken = organization.token

                    mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)
                }

                override fun onFailed(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                    if (data!=null && data is NetworkErrorBModel) {
                        data.message?.let {
                            errorToastState.msg = it
                        }
                    } else {
                        errorToastState.msg = "Organisation Creation Failed"
                    }
                    mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
                }
            })
        }
    }

}