package src.networkutil.network

import android.content.Context
import android.util.Log
import src.networkutil.utilities.*
import java.util.*

class NetworkBase(
    var requestingServer: NetworkEnumAnnotation = NetworkEnumAnnotation(NetworkConstant.REQUESTING_SERVER_BASE),
    var context: Context
) {
    var service: NetworkService? = null
    var commonParams: HashMap<String, String> = HashMap()
    var preDefineResponse: NetworkPreDefineResponse = NetworkPreDefineResponse()
    var requestUrl: String = ""
    var requestHeader: NetworkRequestHeader
    var customHeaderKey: String = ""
    var customHeaderValue: String = ""

    init {
        @src.networkutil.utilities.NetworkEnumAnnotation.RequestingServer val server =
            requestingServer.state
        requestHeader = when (server) {
            NetworkConstant.REQUESTING_SERVER_BASE -> {
                NetworkRequestHeader(
                    authorization = InitNetworkUtils.userAuth
                )
            }

//            NetworkConstant.REQUESTING_SERVER_BASE_LATEST -> {
//
//                NetworkRequestHeader(
//                        authKey = NetworkGlobalDataHolder.getMyAuthKey()!!,
//                        apiVersion = NetworkApiManager.GENRIC_API_VERSION,
//                        appVersion = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.appVersion),
//                        lang = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.currentLang),
//                        udid = NetworkSupportUtils.generateRandomUUID(),
//                        timeZone = Calendar.getInstance().timeZone.id,
//                        campaignId = NetworkGlobalDataHolder.campaignId,
//                        sessionId = NetworkSharedPrefUtils.INSTANCE.getFromPreferencesLongval(NetworkConstant.PREF_KEY_SESSION_BACKGROUND_TIME),
//                        userSessionToken = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_KEY_SESSION_USER_TOKEN),
//                        otpToken = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_OTP_TOKEN)
//                )
//            }
//
//            NetworkConstant.REQUESTING_SERVER_PUBLICATION -> {
//                NetworkRequestHeader(
//                        authKey = NetworkGlobalDataHolder.getMyAuthKey()!!,
//                        apiVersion = NetworkApiManager.GENRIC_API_VERSION,
//                        appVersion = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.appVersion),
//                        lang = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.currentLang),
//                        udid = NetworkSupportUtils.generateRandomUUID(),
//                        timeZone = Calendar.getInstance().timeZone.id,
//                        campaignId = NetworkGlobalDataHolder.campaignId,
//                        sessionId = NetworkSharedPrefUtils.INSTANCE.getFromPreferencesLongval(NetworkConstant.PREF_KEY_SESSION_BACKGROUND_TIME),
//                        userSessionToken = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_KEY_SESSION_USER_TOKEN))
//            }
//
//            NetworkConstant.REQUESTING_SERVER_PUBLICATION_OTHER -> {
//                NetworkRequestHeader(
//                        authKey = NetworkGlobalDataHolder.getMyAuthKey()!!,
//                        apiVersion = NetworkApiManager.GENRIC_API_VERSION,
//                        appVersion = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.appVersion),
//                        lang = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.currentLang),
//                        udid = NetworkSupportUtils.generateRandomUUID(),
//                        timeZone = Calendar.getInstance().timeZone.id,
//                        campaignId = NetworkGlobalDataHolder.campaignId,
//                        sessionId = NetworkSharedPrefUtils.INSTANCE.getFromPreferencesLongval(NetworkConstant.PREF_KEY_SESSION_BACKGROUND_TIME),
//                        userSessionToken = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_KEY_SESSION_USER_TOKEN))
//            }
//
//            NetworkConstant.REQUESTING_SERVER_NOTIFY_OLD -> {
//                NetworkRequestHeader(
//                        authKey = NetworkGlobalDataHolder.getMyAuthKey()!!,
//                        apiVersion = NetworkApiManager.GENRIC_API_VERSION,
//                        appVersion = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.appVersion),
//                        lang = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.currentLang),
//                        udid = NetworkSupportUtils.generateRandomUUID(),
//                        timeZone = Calendar.getInstance().timeZone.id,
//                        campaignId = NetworkGlobalDataHolder.campaignId,
//                        sessionId = NetworkSharedPrefUtils.INSTANCE.getFromPreferencesLongval(NetworkConstant.PREF_KEY_SESSION_BACKGROUND_TIME),
//                        userSessionToken = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_KEY_SESSION_USER_TOKEN),
//                        otpToken = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_OTP_TOKEN))
//            }
//
//            NetworkConstant.REQUESTING_SERVER_ANALYTICS -> {
//                NetworkRequestHeader(
//                        authKey = NetworkGlobalDataHolder.getMyAuthKey()!!,
//                        apiVersion = NetworkApiManager.GENRIC_API_VERSION,
//                        appVersion = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.appVersion),
//                        lang = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.currentLang),
//                        udid = NetworkSupportUtils.generateRandomUUID(),
//                        timeZone = Calendar.getInstance().timeZone.id,
//                        campaignId = NetworkGlobalDataHolder.campaignId,
//                        sessionId = NetworkSharedPrefUtils.INSTANCE.getFromPreferencesLongval(NetworkConstant.PREF_KEY_SESSION_BACKGROUND_TIME),
//                        userSessionToken = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_KEY_SESSION_USER_TOKEN),
//                        otpToken = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_OTP_TOKEN))
//            }
//            NetworkConstant.REQUESTING_SERVER_4_1 -> {
//                NetworkRequestHeader(
//                        authKey = NetworkGlobalDataHolder.getMyAuthKey()!!,
//                        apiVersion = NetworkApiManager.GENRIC_API_VERSION_4_3,
//                        appVersion = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.appVersion),
//                        lang = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.currentLang),
//                        udid = NetworkSupportUtils.generateRandomUUID(),
//                        timeZone = Calendar.getInstance().timeZone.id,
//                        campaignId = NetworkGlobalDataHolder.campaignId,
//                        sessionId = NetworkSharedPrefUtils.INSTANCE.getFromPreferencesLongval(NetworkConstant.PREF_KEY_SESSION_BACKGROUND_TIME),
//                        userSessionToken = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_KEY_SESSION_USER_TOKEN),
//                        otpToken = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_OTP_TOKEN))
//            }
//            NetworkConstant.REQUESTING_SERVER_SOCKET -> {
//                NetworkRequestHeader(
//                        authKey = NetworkGlobalDataHolder.getMyAuthKey()!!,
//                        apiVersion = NetworkApiManager.GENRIC_API_VERSION_4_1,
//                        appVersion = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.appVersion),
//                        lang = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.currentLang),
//                        udid = NetworkSupportUtils.generateRandomUUID(),
//                        timeZone = Calendar.getInstance().timeZone.id,
//                        campaignId = NetworkGlobalDataHolder.campaignId,
//                        sessionId = NetworkSharedPrefUtils.INSTANCE.getFromPreferencesLongval(NetworkConstant.PREF_KEY_SESSION_BACKGROUND_TIME),
//                        otpToken = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_OTP_TOKEN))
//            }
//            NetworkConstant.REQUESTING_SERVER_SOCKET_2 -> {
//                NetworkRequestHeader(
//                        authKey = NetworkGlobalDataHolder.getMyAuthKey()!!,
//                        apiVersion = NetworkApiManager.GENRIC_API_VERSION_4_1,
//                        appVersion = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.appVersion),
//                        lang = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.currentLang),
//                        udid = NetworkSupportUtils.generateRandomUUID(),
//                        timeZone = Calendar.getInstance().timeZone.id,
//                        campaignId = NetworkGlobalDataHolder.campaignId,
//                        sessionId = NetworkSharedPrefUtils.INSTANCE.getFromPreferencesLongval(NetworkConstant.PREF_KEY_SESSION_BACKGROUND_TIME),
//                        otpToken = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_OTP_TOKEN))
//            }
//
//            NetworkConstant.REQUESTING_SERVER_WITHOUT_BASE_URL_SQS -> {
//
//                NetworkRequestHeader(
//                        authKey = NetworkGlobalDataHolder.getMyAuthKey()!!,
//                        apiVersion = NetworkApiManager.GENRIC_API_VERSION_4_3,
//                        appVersion = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.appVersion),
//                        lang = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.currentLang),
//                        udid = NetworkSupportUtils.generateRandomUUID(),
//                        timeZone = Calendar.getInstance().timeZone.id,
//                        campaignId = NetworkGlobalDataHolder.campaignId,
//                        sessionId = NetworkSharedPrefUtils.INSTANCE.getFromPreferencesLongval(NetworkConstant.PREF_KEY_SESSION_BACKGROUND_TIME),
//                        userSessionToken = NetworkSharedPrefUtils.INSTANCE.getFromPreferences(NetworkConstant.PREF_KEY_SESSION_USER_TOKEN),
//                        x_api_key = NetworkConstant.SQS_TOKEN)
//            }

            else -> {
                NetworkRequestHeader(
                    authorization = NetworkGlobalDataHolder.getMyAuthKey(),
                    organizationToken = NetworkGlobalDataHolder.getOrgAuthKey(),
                    workspaceToken = NetworkGlobalDataHolder.getWorkAuthKey())
            }
        }
    }

    fun initializeService() {
        Log.e("initializeService", "request header" + requestHeader.toString())
        @src.networkutil.utilities.NetworkEnumAnnotation.RequestingServer val server =
            requestingServer.state
        service = when (server) {
            NetworkConstant.REQUESTING_SERVER_BASE -> {
                requestUrl = InitNetworkUtils.server_path
                NetworkManager.getNewClient(requestHeader)
            }
//            NetworkConstant.REQUESTING_SERVER_LOCATION -> {
//                requestUrl = InitNetworkUtils.locationPath
//                NetworkManager.getThirdPartyClient()
//            }
//            NetworkConstant.REQUESTING_SERVER_BASE_WITHOUTINTERCEPTOR -> {
//                requestUrl = InitNetworkUtils.server_path
//                NetworkManager.getOldClientWithoutInterceptor(requestHeader)
//            }
//            NetworkConstant.REQUESTING_SERVER_BASE_LATEST, NetworkConstant.REQUESTING_SERVER_4_1 -> {
//                requestUrl = InitNetworkUtils.server_path2
//                NetworkManager.getNewClient(requestHeader)
//            }
//            NetworkConstant.REQUESTING_SERVER_WITHOUT_BASE_URL,
//            NetworkConstant.REQUESTING_SERVER_WITHOUT_BASE_URL_SQS-> {
//                requestUrl = ""
//                NetworkManager.getNewClient(requestHeader)
//            }
//
//            NetworkConstant.REQUESTING_SERVER_SOCKET -> {
//                requestUrl = InitNetworkUtils.socket_path
//                NetworkManager.getNewClient(requestHeader)
//            }
//            NetworkConstant.REQUESTING_SERVER_SOCKET_2 -> {
//                requestUrl = InitNetworkUtils.socket_path2
//                NetworkManager.getNewClient(requestHeader)
//            }
//
//            NetworkConstant.REQUESTING_SERVER_PUBLICATION_OTHER,
//            NetworkConstant.REQUESTING_SERVER_NOTIFY_OLD,
//            NetworkConstant.REQUESTING_SERVER_ANALYTICS,
//            NetworkConstant.REQUESTING_SERVER_PUBLICATION-> {
//                requestUrl = InitNetworkUtils.server_path
//                NetworkManager.getNewClient(requestHeader)
//            }
//
//
//            NetworkConstant.REQUESTING_SERVER_WITH_CUSTOM_HEADER -> {
//                requestUrl = InitNetworkUtils.server_path2
//                NetworkManager.getNewCustomClient(customHeaderKey, customHeaderValue, requestHeader)
//            }
//            NetworkConstant.REQUESTING_SERVER_NODE_API -> {
//                requestUrl = InitNetworkUtils.server_path_node_api
//                NetworkManager.getNewClient(requestHeader)
//            }

            else -> {
                return
            }
        }
    }
}
