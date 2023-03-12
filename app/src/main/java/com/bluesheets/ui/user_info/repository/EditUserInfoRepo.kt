package com.bluesheets.ui.user_info.repository

import com.bluesheets.BluesheetApplication
import com.bluesheets.utils.ApiMethods
import com.bluesheets.utils.AppRequestParams
import src.networkutil.network.NetworkBase
import src.networkutil.network.NetworkRequest
import src.networkutil.utilities.NetworkConstant
import src.networkutil.utilities.NetworkEnumAnnotation

class EditUserInfoRepo {

    fun userLogOut(access_token: String, listener: NetworkRequest.IOnResponse?) {
        var baseNetwork = NetworkBase(context = BluesheetApplication.instance.applicationContext)
        baseNetwork.initializeService()
        var request = NetworkRequest(
            apiMethod = ApiMethods.USER_LOGOUT,
            baseNetwork = baseNetwork,
            context = BluesheetApplication.instance.applicationContext,
            requestType = NetworkEnumAnnotation(NetworkConstant.REQUEST_TYPE_POST),
            params = AppRequestParams.userLogOut(access_token)
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


    fun deleteAccount(listener: NetworkRequest.IOnResponse?) {
        var baseNetwork = NetworkBase(context = BluesheetApplication.instance.applicationContext)
        baseNetwork.initializeService()
        var request = NetworkRequest(
            apiMethod = ApiMethods.USER_DELETE_ACCOUNT,
            baseNetwork = baseNetwork,
            context = BluesheetApplication.instance.applicationContext,
            requestType = NetworkEnumAnnotation(NetworkConstant.REQUEST_TYPE_POST)
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