package com.bluesheets.ui.documents.repository

import com.bluesheets.BluesheetApplication
import com.bluesheets.utils.ApiMethods
import com.bluesheets.utils.AppRequestParams
import com.bluesheets.utils.UserInfoUtil
import src.networkutil.network.NetworkBase
import src.networkutil.network.NetworkRequest
import src.networkutil.utilities.NetworkConstant
import src.networkutil.utilities.NetworkEnumAnnotation

class DocumentListRepo {

    fun getDocumentListRepo(authToken: String, orgToken: String, listener: NetworkRequest.IOnResponse?) {
        var baseNetwork = NetworkBase(context = BluesheetApplication.instance.applicationContext)
        baseNetwork.initializeService()
        var request = NetworkRequest(
            apiMethod = ApiMethods.GET_DOCUMENTS,
            baseNetwork = baseNetwork,
            context = BluesheetApplication.instance.applicationContext,
            requestType = NetworkEnumAnnotation(NetworkConstant.REQUEST_TYPE_GET),
            params = AppRequestParams.getAllDocuments(
                authToken,
                orgToken,
                "{\"where\":{},\"include\":[\"bulkFilesAuthParams\"],\"fields\":[\"id\",\"filename\",\"exportPreviewUrl\",\"statusExport\",\"userId\"]}"
            )
        )

        request.callService(listener = object : NetworkRequest.IOnResponse{
            override fun onException(t: Throwable?) {
                listener?.onException(t)
            }

            override fun onSuccess(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                listener?.onSuccess(code,message,data,rawResponse)
            }

            override fun onFailed(code: Int?, message: String?, data: Any?, rawResponse: String?) {
                listener?.onFailed(code,message,data,rawResponse)
            }
        })
    }
}