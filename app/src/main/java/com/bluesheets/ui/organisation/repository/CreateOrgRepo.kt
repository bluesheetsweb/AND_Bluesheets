package com.bluesheets.ui.organisation.repository

import com.bluesheets.BluesheetApplication
import com.bluesheets.utils.ApiMethods
import com.bluesheets.utils.AppRequestParams
import com.bluesheets.utils.UserInfoUtil
import src.networkutil.network.NetworkBase
import src.networkutil.network.NetworkRequest
import src.networkutil.utilities.NetworkConstant
import src.networkutil.utilities.NetworkEnumAnnotation

class CreateOrgRepo {

    fun createOrg(name: String, description: String, workspaceId: String, listener: NetworkRequest.IOnResponse?) {
        var baseNetwork = NetworkBase(context = BluesheetApplication.instance.applicationContext)
        baseNetwork.initializeService()
        var request = NetworkRequest(
            apiMethod = ApiMethods.CREATE_ORGANIZATION,
            baseNetwork = baseNetwork,
            context = BluesheetApplication.instance.applicationContext,
            requestType = NetworkEnumAnnotation(NetworkConstant.REQUEST_TYPE_POST),
            params = AppRequestParams.registerOrg(name, description, workspaceId)
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

    fun editOrg(name: String, description: String, workspaceId: String, listener: NetworkRequest.IOnResponse?) {
        var baseNetwork = NetworkBase(context = BluesheetApplication.instance.applicationContext)
        baseNetwork.initializeService()
        var request = NetworkRequest(
            apiMethod = ApiMethods.CREATE_ORGANIZATION+"/"+UserInfoUtil.organizationId,
            baseNetwork = baseNetwork,
            context = BluesheetApplication.instance.applicationContext,
            requestType = NetworkEnumAnnotation(NetworkConstant.REQUEST_TYPE_POST),
            params = AppRequestParams.registerOrg(name, description, workspaceId)
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