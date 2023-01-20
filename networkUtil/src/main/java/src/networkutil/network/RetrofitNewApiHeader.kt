package src.networkutil.network

import okhttp3.Interceptor
import okhttp3.Response
import src.networkutil.utilities.NetworkConstant
import java.io.IOException

class RetrofitNewApiHeader(val header: NetworkRequestHeader) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val header = if (header.authorization != null)
            header.authorization!!
        else
            ""
        val builder = original.newBuilder()
            .header(NetworkConstant.HEADER_AUTHORIZATION, header)
            .method(original.method, original.body)

        val request = builder.build()
        return chain.proceed(request)
    }
}
