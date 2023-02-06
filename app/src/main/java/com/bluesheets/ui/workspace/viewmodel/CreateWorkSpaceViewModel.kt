package com.bluesheets.ui.workspace.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bluesheets.BluesheetApplication
import com.bluesheets.ui.organisation.model.OrganizationModel
import com.bluesheets.ui.organisation.repository.CreateOrgRepo
import com.bluesheets.ui.signup.repository.SignInRepo
import com.bluesheets.ui.workspace.model.WorkSpaceModel
import com.bluesheets.ui.workspace.repository.CreateWorkRepo
import com.bluesheets.utils.AppUtils
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

class CreateWorkSpaceViewModel : ParentVM() {

    private var workName: String = ""
    private var description: String = ""

    var buttonEnabled = MutableLiveData<Boolean>()

    init {
        repository = CreateWorkRepo()
    }

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun onNameTextChanged(s: CharSequence) {
        workName = s.toString()
        description = s.toString()
        checkSignInButtonEnablity()
    }

    private fun checkSignInButtonEnablity() {
        buttonEnabled.value = AppUtils.isValidNameLength(workName)
    }

    fun createWorkSpaceClicked() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        (repository as CreateWorkRepo).createWorkRepo(workName, description, object : NetworkRequest.IOnResponse{
            override fun onException(t: Throwable?) {
                errorToastState.msg = "Workspace Creation Failed With Exception"
                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
            }

            override fun onSuccess(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                Toaster.show(BluesheetApplication.instance.applicationContext, "Workspace Creation Success")
                var workSpace = Gson().fromJson(rawResponse, WorkSpaceModel::class.java)
                UserInfoUtil.workSpaceId = workSpace.id
                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)
            }

            override fun onFailed(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                if (data!=null && data is NetworkErrorBModel) {
                    data.message?.let {
                        errorToastState.msg = it
                    }
                } else {
                    errorToastState.msg = "Workspace Creation Failed"
                }
                mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
            }
        })
    }

}