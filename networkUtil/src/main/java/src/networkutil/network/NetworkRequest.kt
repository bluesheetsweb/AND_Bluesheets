package src.networkutil.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import src.networkutil.listener.OnGenericUpdateListener
import src.networkutil.model.NetworkErrorBModel
import src.networkutil.utilities.*
import java.util.*

class NetworkRequest(
    var context: Context,
    var baseNetwork: NetworkBase,
    var apiMethod: String,
    var requestType: src.networkutil.utilities.NetworkEnumAnnotation,
    var params: HashMap<String, Any?> = HashMap(),
    var isShowErrorAlert: Boolean = false
) {

    companion object {
        var TAG = NetworkRequest::class.java.simpleName
    }

    private var requestUrl = baseNetwork.requestUrl

    init {
        if (!baseNetwork.commonParams.isEmpty())
            baseNetwork.commonParams.forEach { (key, value) -> params[key] = value }
        requestUrl += apiMethod
    }

    fun callService(listener: IOnResponse) {

        if (!isConnectingToInternet(context)) {
            listener?.onFailed(0, NetworkConstant.TEXT_MESSAGE_NO_INTERNET, "")
            NetworkGlobalCallBack.registerNetworkCallBack?.onFailed(
                0,
                NetworkConstant.TEXT_MESSAGE_NO_INTERNET,
                ""
            )
            return
        }

        var rquestTime = Calendar.getInstance().timeInMillis
        var call: Call<ResponseBody>
        @src.networkutil.utilities.NetworkEnumAnnotation.RequestType val status = requestType.state

        call = when (status) {
            NetworkConstant.REQUEST_TYPE_POST -> baseNetwork.service!!.postApi(
                requestUrl,
                params
            )
            NetworkConstant.REQUEST_TYPE_POST_MUTIPART -> baseNetwork.service!!.postMultiPartApi(
                requestUrl,
                params as HashMap<String, RequestBody?>
            )

            NetworkConstant.REQUEST_TYPE_GET -> baseNetwork.service!!.getApi(
                requestUrl,
                params as HashMap<String, Any?>
            )
            else -> {
                listener?.onFailed(
                    NetworkConstant.RESPONSE_CODE_INVALID,
                    NetworkConstant.TEXT_MESSAGE_INVALID_REQUEST_TYPE,
                    ""
                )
                return
            }
        }
//        var mRequest = "Date :- ${Date(Calendar.getInstance().timeInMillis)}\nUrl :- $requestUrl\nParams :-$params"

        Log.e(
            TAG,
            "$requestUrl callService userSessionToken ${baseNetwork.requestHeader.authorization} requestUrl $requestUrl"
        )
        var preDefineResponse = baseNetwork.preDefineResponse
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                try {
                    var difference = Calendar.getInstance().timeInMillis - rquestTime
                    Log.e(TAG, "$requestUrl onResponse called ${getTimeDiffrence(difference)}")

                    var rawResponse = response.body()?.string()
                    // send response on server
//                    if (rawResponse != null)
//                        Log.i(rawResponse.toString(), "Api Log - " + requestUrl)

                    if (android.text.TextUtils.isEmpty(rawResponse))
                        rawResponse = response.errorBody()?.string()
//                    FirebaseCrashlytics.getInstance().log("Request :- $mRequest\n\nResponse :- $rawResponse")

//                    if (android.text.TextUtils.isEmpty(rawResponse)) {
//                        listener?.onFailed(
//                            -1,
//                            NetworkConstant.TEXT_MESSAGE_INVALID_RESPONSE,
//                            null,
//                            rawResponse
//                        )
//                        return
//                    }
//                    var jsonBody = JSONObject(rawResponse)
                    var status: Int = response.code()
//
//                    var message: String = "Success"
//                    var responseData = ""
//                    var action: JSONObject? = null
//                    var toopTips: MutableList<Any> = mutableListOf()
//                    var parent = jsonBody
//                    var isSuccess = false
//                    if (!android.text.TextUtils.isEmpty(preDefineResponse.parentKey)) {
//                        parent = jsonBody.optJSONObject(preDefineResponse.parentKey)
//                    }
//                    Log.e(
//                        TAG,
//                        "preDefineResponse.isToCheckStatusValueType)" + preDefineResponse.isToCheckStatusValueType
//                    )
//                    /*if(preDefineResponse.isToCheckStatusValueType){
//                       var key= parent.opt(preDefineResponse.statusKey)
//                        Log.e(TAG,"statuskey"+key)
//                        if(key is Boolean) {
//                            status = when (key) {
//                                true -> 1
//                                false -> 0
//                            }
//                        }
//                    }
//                    else*/
//                    try {
//                        action = parent.optJSONObject(preDefineResponse.actionKey)
//                    } catch (ex: java.lang.Exception) {
//                        Log.e(TAG, "ex", ex)
//                    }
//
//                    try {
//                        var jsonToolTipArray = parent.optJSONArray(preDefineResponse.tootTips)
//                        NetworkGlobalDataHolder.toolTipsJsonArray = jsonToolTipArray
//                    } catch (e: Throwable) {
//                        e.printStackTrace()
//                    }
//
////                    try {
////                        responseData = when (preDefineResponse.dataKeyTypeObject) {
////                            true -> {
////                                parent.optJSONObject(preDefineResponse.dataKey).toString()
////                            }
////                            false -> {
////                                parent.optJSONArray(preDefineResponse.dataKey).toString()
////                            }
////                        }
////                    } catch (e: Throwable) {
////                        e.printStackTrace()
////                        Log.e(TAG, "responseData ex", e)
////                    }
//
//                    var isAction = false
//
//                    if (action != null) {
//                        NetworkGlobalCallBack.onNetworkAlertListener?.callAlert(action)
////                        isSuccess = true
//                        isAction = true
//                    }
//                    if (preDefineResponse.isThirdPartyApi) {
//                        Log.e(TAG, "isThirdPartyApi called")
//                        isSuccess = true
//                    } else if (preDefineResponse.successArrayCode != null && preDefineResponse.successArrayCode.isNotEmpty()) {
//                        if (preDefineResponse.successArrayCode.contains(status))
//                            isSuccess = true
//                    } else if (preDefineResponse.isToCheckStatusValueType) {
//                        Log.e(TAG, "else if preDefineResponse")
//                        isSuccess = parent.optJSONObject(preDefineResponse.dataKey)
//                            .getBoolean(preDefineResponse.statusKey)
//                    } else {
//                        if (status == NetworkConstant.RESPONSE_CODE_SUCCESS)
//                            isSuccess = true
//                    }
                    var isSuccess = false
                    if (status in 200..300){
                        isSuccess = true
                    }

                    if (isSuccess) {
                        Log.e(TAG, "isSuccess")
//                        if (isAction)
//                            code = NetworkConstant.RESPONSE_CODE_ACTION

                        listener?.onSuccess(status, null, null, rawResponse)
                        NetworkGlobalCallBack.registerNetworkCallBack?.onSuccess(
                            status,
                            null,
                            null,
                            rawResponse
                        )
                    } else {
//                        if (status == NetworkConstant.RESPONSE_CODE_ERROR) {
//                            var jsonError = parent.getJSONObject(preDefineResponse.errorKey)
//                            message = jsonError.optString(preDefineResponse.messageKey)
//                            code = jsonError.optInt(preDefineResponse.codeKey)
//                            Log.e(TAG, "session expired code $code")
////                            if (code == NetworkConstant.RESPONSE_CODE_TOKEN_EXPIRED) {
////                                Log.e(
////                                    TAG,
////                                    "session expired" + NetworkGlobalDataHolder.autoRefreshNetworkListener.size
////                                )
////                                Log.e(
////                                    TAG,
////                                    "session isSessionCreationInProgress" + NetworkGlobalDataHolder.isSessionCreationInProgress
////                                )
////                                Handler(Looper.getMainLooper()).post {
////                                    if (NetworkGlobalDataHolder.isSessionCreationInProgress) {
////                                        val currentTimeUtils = Calendar.getInstance().timeInMillis
////                                        Log.e(
////                                            TAG,
////                                            "session expired currentTimeUtils $currentTimeUtils"
////                                        )
//////                                        NetworkGlobalDataHolder.autoRefreshNetworkListener.put(
//////                                            currentTimeUtils,
//////                                            object : OnGenericUpdateListener {
//////                                                override fun onUpdate(data: Any?) {
//////                                                    if (data != null && data is String) {
//////                                                        baseNetwork.requestHeader.userSessionToken =
//////                                                            data
//////                                                        baseNetwork.requestHeader.sessionId =
//////                                                            NetworkSharedPrefUtils.INSTANCE.getFromPreferencesLongval(
//////                                                                NetworkConstant.PREF_KEY_SESSION_BACKGROUND_TIME
//////                                                            )
//////                                                        Log.e(
//////                                                            TAG,
//////                                                            "session onUpdate listener called." + requestUrl
//////                                                        )
//////                                                        callService(listener)
//////                                                    }
//////                                                }
//////
//////                                                override fun onUpdateList() {
//////                                                }
//////                                            }
//////                                        )
////                                    } else {
////                                        NetworkGlobalDataHolder.isSessionCreationInProgress = true
////                                        pullNewSession(
////                                            context,
////                                            object : IOnResponse {
////                                                override fun onException(t: Throwable?) {
////                                                }
////
////                                                override fun onSuccess(
////                                                    code: Int?,
////                                                    message: String?,
////                                                    data: Any?,
////                                                    rawResponse: String?
////                                                ) {
////                                                    Log.e(TAG, "session successfully created")
////                                                    callService(listener)
////                                                }
////
////                                                override fun onFailed(
////                                                    code: Int?,
////                                                    message: String?,
////                                                    data: Any?,
////                                                    rawResponse: String?
////                                                ) {
////                                                }
////                                            }
////                                        )
////                                    }
////                                }
////                                return
////                            }
//                            var errorBModel = NetworkErrorBModel()
//                            errorBModel.code = code
//                            errorBModel.message = message
//                            try {
//                                responseData = when (preDefineResponse.dataKeyErrorTypeObject) {
//                                    true -> {
//                                        jsonError.optJSONObject(preDefineResponse.dataKey)
//                                            .toString()
//                                    }
//                                    false -> {
//                                        jsonError.optJSONArray(preDefineResponse.dataKey).toString()
//                                    }
//                                }
//                            } catch (ex: Throwable) {
//                                ex.printStackTrace()
//                            }
//                            errorBModel.data = responseData
//                            Log.e(TAG, "isSuccess failed" + message)
//                            listener?.onFailed(code, message, errorBModel, rawResponse)
//                            NetworkGlobalCallBack.registerNetworkCallBack?.onFailed(
//                                code,
//                                message,
//                                errorBModel,
//                                rawResponse
//                            )
//
//                            if (!message.isNullOrBlank() && isShowErrorAlert && !isAction) {
//                                showAlert(message)
//                            }
//                        } else if (status == NetworkConstant.RESPONSE_CODE_EXIT_APP) {
//                            // TODO: Exit App
////                        ExitApp().exitApp(context, msg)
//                        } else if (status == NetworkConstant.RESPONSE_CODE_SHOW_DIALOG) {
//                            // TODO: show dialog
////                        UserStatus.setReadOnlyAndOpenDialog(context)
//                        } else {
                            Log.e(TAG, "else failed")
                            rawResponse = response.errorBody()?.string()
                            var jsonError = JSONObject(rawResponse)
                           var  message = jsonError.optString("message")
                        var errorBModel = NetworkErrorBModel()
                            errorBModel.code = status
                            errorBModel.message = message
                        listener?.onFailed(status, message,errorBModel, rawResponse)
                        NetworkGlobalCallBack.registerNetworkCallBack?.onFailed(
                                status,
                                message,
                                rawResponse
                            )

//                            if (!message.isNullOrBlank() && isShowErrorAlert && !isAction) {
//                                showAlert(message)
//                            }
//                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    listener?.onFailed(-1, NetworkConstant.TEXT_MESSAGE_UN_HANDLED_RESPONSE, null)
                    NetworkGlobalCallBack.registerNetworkCallBack?.onException(e)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, "onFailure called")
                // send incase of error
                Log.i(t.message.toString(), "Api Log - " + requestUrl)

                t.printStackTrace()
                if (!isConnectingToInternet(context)) {
                    listener?.onFailed(status, NetworkConstant.TEXT_MESSAGE_NO_INTERNET, "")
                    NetworkGlobalCallBack.registerNetworkCallBack?.onFailed(
                        status,
                        NetworkConstant.TEXT_MESSAGE_NO_INTERNET,
                        ""
                    )
                } else {
                    listener?.onException(t)
                    NetworkGlobalCallBack.registerNetworkCallBack?.onException(t)
                }
            }
        })
    }

    fun callService2(listener: IOnResponse) {
        var rquestTime = Calendar.getInstance().timeInMillis
        var call: Call<ResponseBody>
        @src.networkutil.utilities.NetworkEnumAnnotation.RequestType val status = requestType.state

        call = when (status) {
            NetworkConstant.REQUEST_TYPE_POST -> baseNetwork.service!!.postApi(
                requestUrl,
                params as HashMap<String, Any?>
            )
            NetworkConstant.REQUEST_TYPE_POST_MUTIPART -> baseNetwork.service!!.postMultiPartApi(
                requestUrl,
                params as HashMap<String, RequestBody?>
            )

            NetworkConstant.REQUEST_TYPE_GET -> baseNetwork.service!!.getApi(
                requestUrl,
                params as HashMap<String, Any?>
            )
            else -> {
                listener?.onFailed(
                    NetworkConstant.RESPONSE_CODE_INVALID,
                    NetworkConstant.TEXT_MESSAGE_INVALID_REQUEST_TYPE,
                    ""
                )
                return
            }
        }
//        var mRequest = "Date :- ${Date(Calendar.getInstance().timeInMillis)}\nUrl :- $requestUrl\nParams :-$params"

        Log.e(
            TAG,
            "$requestUrl callService userSessionToken ${baseNetwork.requestHeader.authorization} requestUrl $requestUrl"
        )
        var preDefineResponse = baseNetwork.preDefineResponse
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                try {
                    var difference = Calendar.getInstance().timeInMillis - rquestTime
                    Log.e(TAG, "$requestUrl onResponse called ${getTimeDiffrence(difference)}")

                    var rawResponse = response?.body()?.string()
                    // send response on server
                    if (rawResponse != null)
                        Log.i(rawResponse.toString(), "Api Log - " + requestUrl)

                    if (android.text.TextUtils.isEmpty(rawResponse))
                        rawResponse = response?.errorBody()?.string()
//                    FirebaseCrashlytics.getInstance().log("Request :- $mRequest\n\nResponse :- $rawResponse")

                    if (android.text.TextUtils.isEmpty(rawResponse)) {
                        listener?.onFailed(
                            -1,
                            NetworkConstant.TEXT_MESSAGE_INVALID_RESPONSE,
                            null,
                            rawResponse
                        )
                        return
                    }
                    var jsonBody = JSONObject(rawResponse)
                    var status: Int = NetworkConstant.RESPONSE_CODE_INVALID
                    var code: Int = NetworkConstant.RESPONSE_CODE_ERROR

                    var message: String
                    var responseData = ""
                    var action: JSONObject? = null
                    var toopTips: MutableList<Any> = mutableListOf()
                    var parent = jsonBody
                    var isSuccess = false
                    if (!android.text.TextUtils.isEmpty(preDefineResponse.parentKey)) {
                        parent = jsonBody.optJSONObject(preDefineResponse.parentKey)
                    }
                    Log.e(
                        TAG,
                        "preDefineResponse.isToCheckStatusValueType)" + preDefineResponse.isToCheckStatusValueType
                    )
                    /*if(preDefineResponse.isToCheckStatusValueType){
                       var key= parent.opt(preDefineResponse.statusKey)
                        Log.e(TAG,"statuskey"+key)
                        if(key is Boolean) {
                            status = when (key) {
                                true -> 1
                                false -> 0
                            }
                        }
                    }
                    else*/
                    status = parent.optInt(preDefineResponse.statusKey)

                    message = parent.optString(preDefineResponse.messageKey)
                    code = parent.optInt(preDefineResponse.codeKey)
                    try {
                        action = parent.optJSONObject(preDefineResponse.actionKey)
                    } catch (ex: java.lang.Exception) {
                        Log.e(TAG, "ex", ex)
                    }

                    try {
                        var jsonToolTipArray = parent.optJSONArray(preDefineResponse.tootTips)
                        NetworkGlobalDataHolder.toolTipsJsonArray = jsonToolTipArray
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }

                    try {
                        responseData = when (preDefineResponse.dataKeyTypeObject) {
                            true -> {
                                parent.optJSONObject(preDefineResponse.dataKey).toString()
                            }
                            false -> {
                                parent.optJSONArray(preDefineResponse.dataKey).toString()
                            }
                        }
                    } catch (e: Throwable) {
                        e.printStackTrace()
                        Log.e(TAG, "responseData ex", e)
                    }

//                    var isAction = false

//                    if (action != null) {
//                        DCGlobalCallBack.alertListner?.callAlert(action)
// //                        isSuccess = true
//                        isAction = true
//                    }
                    if (preDefineResponse.isThirdPartyApi) {
                        Log.e(TAG, "isThirdPartyApi called")
                        isSuccess = true
                    } else if (preDefineResponse.successArrayCode != null && preDefineResponse.successArrayCode.isNotEmpty()) {
                        if (preDefineResponse.successArrayCode.contains(status))
                            isSuccess = true
                    } else if (preDefineResponse.isToCheckStatusValueType) {
                        Log.e(TAG, "else if preDefineResponse")
                        isSuccess = parent.optJSONObject(preDefineResponse.dataKey)
                            .getBoolean(preDefineResponse.statusKey)
                    } else {
                        if (status == NetworkConstant.RESPONSE_CODE_SUCCESS)
                            isSuccess = true
                    }

                    if (isSuccess) {
                        Log.e(TAG, "isSuccess")
//                        if (isAction)
//                            code = DCConstant.RESPONSE_CODE_ACTION

                        listener?.onSuccess(code, message, responseData, rawResponse)
//                        DCGlobalCallBack.registerNetworkCallBack?.onSuccess(code, message, responseData, rawResponse)
                    } else {
                        if (status == NetworkConstant.RESPONSE_CODE_ERROR) {
                            var jsonError = parent.getJSONObject(preDefineResponse.errorKey)
                            message = jsonError.optString(preDefineResponse.messageKey)
                            code = jsonError.optInt(preDefineResponse.codeKey)
                            Log.e(TAG, "session expired code $code")
//                            if (code == DCConstant.RESPONSE_CODE_TOKEN_EXPIRED) {
//                                Log.e(TAG, "session expired" + DCGlobalDataHolder.autoRefreshNetworkListener.size)
//                                Log.e(TAG, "session isSessionCreationInProgress" + DCGlobalDataHolder.isSessionCreationInProgress)
//                                Handler(Looper.getMainLooper()).post {
//                                    if (DCGlobalDataHolder.isSessionCreationInProgress) {
//                                        val currentTimeUtils = Calendar.getInstance().timeInMillis
//                                        Log.e(TAG, "session expired currentTimeUtils $currentTimeUtils")
//                                        DCGlobalDataHolder.autoRefreshNetworkListener.put(currentTimeUtils,
//                                                object : OnGenericUpdateListener {
//                                                    override fun onUpdate(data: Any?) {
//                                                        if (data != null && data is String) {
//                                                            baseNetwork.requestHeader.userSessionToken = data
//                                                            baseNetwork.requestHeader.sessionId = DCSharedPrefUtils.instance.getFromPreferencesLongval(DCConstant.PREF_KEY_SESSION_BACKGROUND_TIME)
//                                                            Log.e(TAG, "session onUpdate listener called." + requestUrl)
//                                                            callService(listener)
//                                                        }
//                                                    }
//                                                })
//
//                                    } else {
//                                        DCGlobalDataHolder.isSessionCreationInProgress = true
//                                        pullNewSession(context, object : IOnResponse {
//                                            override fun onException(t: Throwable?) {
//
//                                            }
//
//                                            override fun onSuccess(code: Int?, message: String?, data: Any?, rawResponse: String?) {
//                                                Log.e(TAG, "session successfully created")
//                                                callService(listener)
//                                            }
//
//                                            override fun onFailed(code: Int?, message: String?, data: Any?, rawResponse: String?) {
//                                            }
//
//                                        })
//                                    }
//                                }
//                                return
//                            }
                            var errorBModel = NetworkErrorBModel()
                            errorBModel.code = code
                            errorBModel.message = message
                            try {
                                responseData = when (preDefineResponse.dataKeyErrorTypeObject) {
                                    true -> {
                                        jsonError.optJSONObject(preDefineResponse.dataKey)
                                            .toString()
                                    }
                                    false -> {
                                        jsonError.optJSONArray(preDefineResponse.dataKey).toString()
                                    }
                                }
                            } catch (ex: Throwable) {
                                ex.printStackTrace()
                            }
                            errorBModel.data = responseData
                            Log.e(TAG, "isSuccess failed" + message)
                            listener?.onFailed(code, message, errorBModel, rawResponse)
//                            DCGlobalCallBack.registerNetworkCallBack?.onFailed(code, message, errorBModel, rawResponse)

//                            if (!message.isNullOrBlank() && isShowErrorAlert && !isAction) {
//                                showAlert(message)
//                            }
                        }
//                        else if (status == DCConstant.RESPONSE_CODE_EXIT_APP) {
//                            //TODO: Exit App
// //                        ExitApp().exitApp(context, msg)
//                        } else if (status == DCConstant.RESPONSE_CODE_SHOW_DIALOG) {
//                            //TODO: show dialog
// //                        UserStatus.setReadOnlyAndOpenDialog(context)
//                        }
                        else {
                            Log.e(TAG, "else failed")
                            listener?.onFailed(status, message, rawResponse)
//                            DCGlobalCallBack.registerNetworkCallBack?.onFailed(status, message, rawResponse)

//                            if (!message.isNullOrBlank() && isShowErrorAlert && !isAction) {
//                                showAlert(message)
//                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    listener?.onFailed(-1, NetworkConstant.TEXT_MESSAGE_UN_HANDLED_RESPONSE, null)
//                    DCGlobalCallBack.registerNetworkCallBack?.onException(e)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, "onFailure called")
                // send incase of error
                Log.i(t.message.toString(), "Api Log - " + requestUrl)

                t.printStackTrace()
                if (!isConnectingToInternet(context)) {
                    listener?.onFailed(status, NetworkConstant.TEXT_MESSAGE_NO_INTERNET, "")
//                    DCGlobalCallBack.registerNetworkCallBack?.onFailed(status, DCConstant.TEXT_MESSAGE_NO_INTERNET, "")
                } else {
                    listener?.onException(t)
//                    DCGlobalCallBack.registerNetworkCallBack?.onException(t)
                }
            }
        })
    }

//    fun pullNewSession(context: Context, listener: IOnResponse) {
//        Log.e(TAG, "pullNewSession")
//        var baseNetworkUtil = NetworkBase(
//            requestingServer = NetworkEnumAnnotation(NetworkConstant.REQUESTING_SERVER_BASE),
//            context = context
//        )
//        baseNetworkUtil.requestHeader.apiVersion = NetworkApiManager.GENRIC_API_VERSION_4_3
//        baseNetworkUtil.initializeService()
//        var request = NetworkRequest(
//            context = context,
//            baseNetwork = baseNetworkUtil,
//            apiMethod = NetworkConstant.GENERATE_SESSION_TOKEN,
//            requestType = NetworkEnumAnnotation(NetworkConstant.REQUEST_TYPE_POST),
//            params = hashMapOf()
//        )
//
//        Log.e(TAG, "usersessiontoken  ${request.requestUrl}")
//
//        request.callService(
//            listener = object : IOnResponse {
//                override fun onSuccess(
//                    code: Int?,
//                    message: String?,
//                    data: Any?,
//                    rawResponse: String?
//                ) {
//                    try {
//                        var jsonData = JSONObject(data.toString())
//                        var usersessiontoken = jsonData.optString("usersessiontoken")
//                        Log.e(TAG, "usersessiontoken is $usersessiontoken")
//                        NetworkSharedPrefUtils.INSTANCE.savePreferences(
//                            NetworkConstant.PREF_KEY_SESSION_USER_TOKEN,
//                            usersessiontoken
//                        )
//                        baseNetwork.requestHeader.userSessionToken = usersessiontoken
//                        baseNetwork.requestHeader.sessionId =
//                            NetworkSharedPrefUtils.INSTANCE.getFromPreferencesLongval(
//                                NetworkConstant.PREF_KEY_SESSION_BACKGROUND_TIME
//                            )
//
//                        NetworkGlobalDataHolder.autoCallAllPendingRequests(usersessiontoken)
//                        listener.onSuccess(code, message, data, rawResponse)
//                        NetworkGlobalDataHolder.isSessionCreationInProgress = false
//                    } catch (ex: Exception) {
//                        if (ex != null)
//                            Log.e(TAG, "" + ex.message)
//                        listener.onException(ex)
//                    }
//                }
//
//                override fun onFailed(
//                    code: Int?,
//                    message: String?,
//                    data: Any?,
//                    rawResponse: String?
//                ) {
//                    Log.e(TAG, "usersessiontoken onFailed $rawResponse")
//                    listener.onFailed(code, message, data, rawResponse)
//                }
//
//                override fun onException(t: Throwable?) {
//                    listener.onException(t)
//                }
//            }
//        )
//    }

    fun isConnectingToInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                // for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                // for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }

    fun getTimeDiffrence(millis: Long): String {
        val mins = (millis / (1000 * 60) % 60).toInt()
        val seconds = (millis / (1000) % 60).toInt()
//        val miliseconds = (millis / 1000).toInt()

        var displayMins = mins.toString()
        var displaySeconds = seconds.toString()
        var displayMMiliSecond = millis.toString()

        if (mins < 10)
            displayMins = "0$displayMins"
        if (seconds < 10)
            displaySeconds = "0$displaySeconds"

        return "$displayMins:$displaySeconds:$displayMMiliSecond"
    }

    interface IOnResponse {
        fun onException(t: Throwable?)

        fun onSuccess(code: Int? = 0, message: String? = "", data: Any?, rawResponse: String? = "")

        fun onFailed(code: Int? = -1, message: String? = "", data: Any?, rawResponse: String? = "")
    }

    private fun showAlert(message: String?) {
//        try {
//            Log.e(TAG, "showAlert called" + message)
//            Log.e(TAG, "DCGlobalDataHolder.globalOkButton called" + NetworkGlobalDataHolder.globalOkButton)
//            val builder = AlertDialog.Builder(context, R.style.NetworkAlertDialog)
//            //set title for alert dialog
//
//            //set message for alert dialog
//            builder.setMessage(message)
//
//
//            // Create the AlertDialog
//            var alertDialog: AlertDialog? = null
//
//            // Set other dialog properties
//            //performing positive action
//
//            builder.setPositiveButton(NetworkGlobalDataHolder.globalOkButton) { dialogInterface, which ->
//                alertDialog?.dismiss()
//            }
//
//            alertDialog = builder.create()
//            alertDialog?.setCancelable(true)
//            alertDialog?.show()
//
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
    }
}
