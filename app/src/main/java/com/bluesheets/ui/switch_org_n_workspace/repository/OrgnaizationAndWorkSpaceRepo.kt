package com.bluesheets.ui.switch_org_n_workspace.repository

import com.bluesheets.BluesheetApplication
import com.bluesheets.utils.ApiMethods
import com.bluesheets.utils.AppRequestParams
import com.bluesheets.utils.UserInfoUtil
import src.networkutil.network.NetworkBase
import src.networkutil.network.NetworkRequest
import src.networkutil.utilities.NetworkConstant
import src.networkutil.utilities.NetworkEnumAnnotation

class OrgnaizationAndWorkSpaceRepo {

    fun getWorkSpaceForUser(listener: NetworkRequest.IOnResponse?) {
        var baseNetwork = NetworkBase(context = BluesheetApplication.instance.applicationContext)
        baseNetwork.initializeService()
        var request = NetworkRequest(
            apiMethod = ApiMethods.GET_WORKSPACE,
            baseNetwork = baseNetwork,
            context = BluesheetApplication.instance.applicationContext,
            requestType = NetworkEnumAnnotation(NetworkConstant.REQUEST_TYPE_GET),
            params = AppRequestParams.getWorkSpaceForUser("{\"searchValue\":\"\"}")
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

    fun getOrganizationsForWorkspace(access_token: String, workspaceID: String, listener: NetworkRequest.IOnResponse?) {
        var baseNetwork = NetworkBase(context = BluesheetApplication.instance.applicationContext)
        baseNetwork.initializeService()
        var request = NetworkRequest(
            apiMethod = ApiMethods.GET_ORGANISATIONS_FOR_WORKSPACE,
            baseNetwork = baseNetwork,
            context = BluesheetApplication.instance.applicationContext,
            requestType = NetworkEnumAnnotation(NetworkConstant.REQUEST_TYPE_GET),
            params = AppRequestParams.getOrganizationsForWorkSpace(access_token, workspaceID)
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

    fun loginToWorkspace(access_token: String, workspaceID: String, listener: NetworkRequest.IOnResponse?) {
        var baseNetwork = NetworkBase(context = BluesheetApplication.instance.applicationContext)
        baseNetwork.initializeService()
        var request = NetworkRequest(
            apiMethod = ApiMethods.LOGIN_TO_WORKSPACE,
            baseNetwork = baseNetwork,
            context = BluesheetApplication.instance.applicationContext,
            requestType = NetworkEnumAnnotation(NetworkConstant.REQUEST_TYPE_POST),
            params = AppRequestParams.loginToWorkSpace(access_token, workspaceID)
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

    fun loginToOrganizations(access_token: String, organizationId: String, listener: NetworkRequest.IOnResponse?) {
        var baseNetwork = NetworkBase(context = BluesheetApplication.instance.applicationContext)
        baseNetwork.initializeService()
        var request = NetworkRequest(
            apiMethod = ApiMethods.LOGIN_TO_ORGANISATIONS,
            baseNetwork = baseNetwork,
            context = BluesheetApplication.instance.applicationContext,
            requestType = NetworkEnumAnnotation(NetworkConstant.REQUEST_TYPE_POST),
            params = AppRequestParams.loginToOrganizations(access_token, organizationId)
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