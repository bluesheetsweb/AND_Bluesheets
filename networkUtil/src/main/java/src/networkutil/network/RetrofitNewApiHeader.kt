package src.networkutil.network

import okhttp3.Interceptor
import okhttp3.Response
import src.networkutil.utilities.NetworkConstant
import java.io.IOException

class RetrofitNewApiHeader(val header: NetworkRequestHeader) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

//        val header = if (header.authorization != null)
//            header.authorization!!
//        else
//            ""
        val builder = original.newBuilder()
            .header(NetworkConstant.HEADER_AUTHORIZATION, header.authorization ?: "")
            .header(NetworkConstant.HEADER_X_WORKSPACE_TOKEN, header.workspaceToken?: "")
            .header(NetworkConstant.HEADER_X_ORGANISATION_TOKEN, header.organizationToken?: "")
            .header(NetworkConstant.HEADER_X_USER_AGENT, header.user_agent?: "")
            .header(NetworkConstant.HEADER_ACCEPT, header.accept?: "")
            .header(NetworkConstant.HEADER_CONTENT_TYPE, header.contentType?: "")
            .method(original.method, original.body)

        val request = builder.build()
        return chain.proceed(request)
    }
}
