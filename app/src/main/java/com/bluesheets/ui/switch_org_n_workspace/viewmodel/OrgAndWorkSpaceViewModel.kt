package com.bluesheets.ui.switch_org_n_workspace.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bluesheets.ui.switch_org_n_workspace.repository.OrgnaizationAndWorkSpaceRepo
import com.bluesheets.utils.UserInfoUtil
import src.networkutil.model.NetworkErrorBModel
import src.networkutil.network.NetworkRequest
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class OrgAndWorkSpaceViewModel : ParentVM() {

    init {
        repository = OrgnaizationAndWorkSpaceRepo()
    }

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun getWorkSpaceForUser() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        UserInfoUtil.authToken?.let {
            (repository as OrgnaizationAndWorkSpaceRepo).getWorkSpaceForUser(object :
                NetworkRequest.IOnResponse {
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

    fun getOrganizationsForWorkSpace() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        UserInfoUtil.authToken?.let {
            UserInfoUtil.workSpaceId?.let { workspaceID ->
                (repository as OrgnaizationAndWorkSpaceRepo).getOrganizationsForWorkspace(it,
                    workspaceID, object : NetworkRequest.IOnResponse {
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

    fun loginToWorkSpace() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        UserInfoUtil.authToken?.let {
            UserInfoUtil.workSpaceId?.let { workspaceID ->
                (repository as OrgnaizationAndWorkSpaceRepo).loginToWorkspace(it,
                    workspaceID, object : NetworkRequest.IOnResponse {
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

    fun loginToOrganization() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        UserInfoUtil.authToken?.let {
            UserInfoUtil.organizationId?.let { orgID ->
                (repository as OrgnaizationAndWorkSpaceRepo).loginToOrganizations(it,
                    orgID, object : NetworkRequest.IOnResponse {
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


}