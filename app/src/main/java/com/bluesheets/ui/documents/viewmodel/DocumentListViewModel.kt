package com.bluesheets.ui.documents.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bluesheets.ui.chat.model.ConnectionUserModel
import com.bluesheets.ui.chat.model.OrgTagModel
import com.bluesheets.ui.documents.model.DocumentListData
import com.bluesheets.ui.documents.repository.DocumentListRepo
import com.bluesheets.utils.AppLogger
import com.bluesheets.utils.UserInfoUtil
import com.google.gson.Gson
import src.networkutil.model.NetworkErrorBModel
import src.networkutil.network.NetworkRequest
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class DocumentListViewModel : ParentVM() {

    var isDisabled: MutableLiveData<Boolean> = MutableLiveData(true)
    var listNewUsers: MutableLiveData<MutableList<DocumentListData>> = MutableLiveData()
    var tempMembers: MutableList<DocumentListData> = mutableListOf()
    var refreshChannel: MutableLiveData<Boolean> = MutableLiveData(false)
    private var listSelected: MutableSet<String> = mutableSetOf()
    private var isGroup: Boolean = false
    var listOrgTags: MutableList<OrgTagModel> = mutableListOf()


    fun initData() {
        repository = DocumentListRepo()
        getDocumentList()
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
                            val listDocuments: List<DocumentListData> = Gson().fromJson(rawResponse , Array<DocumentListData>::class.java).toList()
                            mProgressState.value =
                                WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_SUCCESS)
                            AppLogger.e("listDocuments IS ", " $listDocuments");
                            tempMembers = listDocuments.toMutableList()
                            listNewUsers.value = tempMembers
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


    fun uploadFiles() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        (repository as DocumentListRepo).uploadFiles(
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
                        errorToastState.msg = "Failed to upload Documents"
                    }

                    mProgressState.value =
                        WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_ERROR_TOAST)
                }
            })
    }

}