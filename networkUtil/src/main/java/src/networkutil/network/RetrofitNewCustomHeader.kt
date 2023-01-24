package src.networkutil.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import src.networkutil.utilities.NetworkConstant
import java.io.IOException

class RetrofitNewCustomHeader(val key: String, val value: String, val header: NetworkRequestHeader) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder = original.newBuilder()
            .addHeader(key, value)
//                .addHeader(NetworkConstant.HEADER_AUTH_KEY, header.authorization.trim())
//            .addHeader(NetworkConstant.HEADER_VER, header.apiVersion)
//            .addHeader(NetworkConstant.HEADER_APPVERSION, header.appVersion)
//            .addHeader(NetworkConstant.HEADER_LANG, header.lang)
//            .addHeader(NetworkConstant.HEADER_DEVICETYPE, header.deviceType)
//            .addHeader(NetworkConstant.HEADER_TOKENID, header.tokenId)
//            .addHeader(NetworkConstant.HEADER_AUTHORIZATION, header.authToken)
//            .addHeader(NetworkConstant.HEADER_AUTH2, header.authToken)
//            .addHeader(NetworkConstant.HEADER_UDID, header.udid)
//            .addHeader(NetworkConstant.HEADER_TIME_ZONE, header.timeZone)
//                .addHeader(NetworkConstant.HEADER_CAMPAIGN_ID, header.campaignId)
//                .addHeader(NetworkConstant.HEADER_SESSION_ID, header.sessionId.toString())
//                .addHeader(NetworkConstant.HEADER_RELEASE_VERSION, header.releaseVersion.toString())
//                .addHeader(NetworkConstant.HEADER_USER_SESSION_TOKEN, header.userSessionToken)
//            .addHeader(NetworkConstant.HEADER_SQS_X_API_KEY, header.x_api_key)
//            .addHeader(NetworkConstant.HEADER_VERSION, NetworkConstant.HEADER_VERSION_VALUE.toString())

//        if (header.otpToken.trim().isNotEmpty() && header.otpToken.trim().isNotBlank())
//            builder.addHeader(NetworkConstant.HEADER_OTP_TOKEN, header.otpToken)

//        Log.d(NetworkConstant.HEADER_AUTH_KEY, header.authorization.trim())
//        Log.d(NetworkConstant.HEADER_VER, header.apiVersion)
//        Log.d(NetworkConstant.HEADER_APPVERSION, header.appVersion)
//        Log.d(NetworkConstant.HEADER_LANG, header.lang)
//        Log.d(NetworkConstant.HEADER_DEVICETYPE, header.deviceType)
//        Log.d(NetworkConstant.HEADER_TOKENID, header.tokenId)
//        Log.d(NetworkConstant.HEADER_AUTHORIZATION, header.authToken)
//        Log.d(NetworkConstant.HEADER_AUTH2, header.authToken)
//        Log.d(NetworkConstant.HEADER_UDID, header.udid)
//        Log.d(NetworkConstant.HEADER_TIME_ZONE, header.timeZone)
//        Log.d(NetworkConstant.HEADER_CAMPAIGN_ID, header.campaignId.toString())
//        Log.d(NetworkConstant.HEADER_SESSION_ID, header.sessionId.toString())
//        Log.d(NetworkConstant.HEADER_RELEASE_VERSION, header.releaseVersion.toString())
//        Log.d(NetworkConstant.HEADER_USER_SESSION_TOKEN, header.userSessionToken)
//        Log.d(NetworkConstant.HEADER_SQS_X_API_KEY, header.x_api_key)
//        Log.d(NetworkConstant.HEADER_OTP_TOKEN, header.otpToken)
        Log.d(key, value)

        val request = builder.build()
        return chain.proceed(request)
    }
}
