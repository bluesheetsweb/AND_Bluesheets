package src.networkutil.network

import okhttp3.Interceptor
import okhttp3.Response
import src.networkutil.utilities.NetworkConstant
import java.io.IOException

class RetrofitOldApiHeader(val header: NetworkRequestHeader) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder = original.newBuilder()
//                .addHeader(NetworkConstant.HEADER_AUTH_TOKEN, header.authorization)
//            .addHeader(NetworkConstant.HEADER_VERSION, header.apiVersion)
//            .addHeader(NetworkConstant.HEADER_APP_VERSION, header.appVersion)
//            .addHeader(NetworkConstant.HEADER_LANG, header.lang)
//            .addHeader(NetworkConstant.HEADER_DEVICE_TYPE, header.deviceType)
//            .addHeader(NetworkConstant.HEADER_TOKEN_ID, header.tokenId)
//            .addHeader(NetworkConstant.HEADER_AUTHORIZATION, header.authToken)
//            .addHeader(NetworkConstant.HEADER_AUTH2, header.authToken)
//            .addHeader(NetworkConstant.HEADER_TIME_ZONE, header.timeZone)
//                .addHeader(NetworkConstant.HEADER_CAMPAIGN_ID, header.campaignId)
//                .addHeader(NetworkConstant.HEADER_RELEASE_VERSION, header.releaseVersion.toString())
        val request = builder.build()
        return chain.proceed(request)
    }
}
