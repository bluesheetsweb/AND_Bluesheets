package com.bluesheets.ui.documents.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bluesheets.ui.documents.repository.DocumentListRepo
import com.bluesheets.utils.UserInfoUtil
import src.networkutil.model.NetworkErrorBModel
import src.networkutil.network.NetworkRequest
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class DocumentListViewModel : ParentVM() {

    init {
        repository = DocumentListRepo()
    }

    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun getDocumentList() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        UserInfoUtil.authToken?.let { authToken ->
            UserInfoUtil.organizationToken?.let { orgToken ->
                (repository as DocumentListRepo).getDocumentListRepo(
                    authToken,
                    orgToken,
                    object : NetworkRequest.IOnResponse {
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
                                errorToastState.msg = "Failed to load Documents"
                            }

                            mProgressState.value =
                                WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
                        }
                    })
            }
        }
    }

}