package src.networkutil.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ThirdPartyClient : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder = original.newBuilder()
        val request = builder.build()
        return chain.proceed(request)
    }
}
