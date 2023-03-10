package com.bluesheets.ui.documents.viewmodel

import androidx.lifecycle.MutableLiveData
import com.bluesheets.BluesheetApplication
import com.bluesheets.ui.documents.repository.DocumentListRepo
import com.bluesheets.utils.UserInfoUtil
import io.getstream.chat.android.client.models.Channel
import src.networkutil.model.NetworkErrorBModel
import src.networkutil.network.NetworkRequest
import src.wrapperutil.utilities.Toaster
import src.wrapperutil.utilities.WrapperConstant
import src.wrapperutil.utilities.WrapperEnumAnnotation
import src.wrapperutil.viewmodel.ParentVM

class DocumentListViewModel: ParentVM() {

    var allChannels: MutableLiveData<MutableList<Channel>> = MutableLiveData()
    var isSearching: Boolean = false
    private var lastSize = 0
    private var tempChannels: MutableLiveData<MutableList<Channel>> = MutableLiveData()

    init {
        repository = DocumentListRepo()
    }
    override fun getState(): MutableLiveData<WrapperEnumAnnotation> {
        return mProgressState
    }

    fun getDocumentList() {
        mProgressState.value = WrapperEnumAnnotation(WrapperConstant.STATE_SCREEN_LOADING)
        UserInfoUtil.authToken?.let {
            UserInfoUtil.organizationToken?.let { it1 ->
                (repository as DocumentListRepo).getDocumentListRepo(it1, it, object : NetworkRequest.IOnResponse {
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
                        if (message != null) {
                            Toaster.show(BluesheetApplication.instance.applicationContext, message)
                        }

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

}