package src.networkutil.network

import src.networkutil.utilities.NetworkConstant

data class NetworkRequestHeader(
    var authorization: String? = "",
    var iso_code: String = "",
    var apiVersion: String = "",
    var appVersion: String = "",
    var lang: String = "",
    var deviceType: String = NetworkConstant.DEVICE_TYPE,
    var tokenId: String = "",
    var authToken: String = "",
    var udid: String = "",
    var timeZone: String = "",
    var campaignId: String = "",
    var sessionId: Long = 0L,
    var userSessionToken: String = "",
    var releaseVersion: Int = NetworkConstant.RELEASE_VERSION,
    var x_api_key: String = NetworkConstant.SQS_TOKEN,
    var version: Int = 2,
    var otpToken: String = ""
)
