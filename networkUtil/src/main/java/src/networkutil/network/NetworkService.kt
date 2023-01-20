package src.networkutil.network

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface NetworkService {

    @FormUrlEncoded
    @POST
    fun postApi(@Url url: String, @FieldMap data: HashMap<String, Any?>): Call<ResponseBody>

    @Multipart
    @POST
    fun postMultiPartApi(
        @Url url: String,
        @PartMap data: HashMap<String, RequestBody?>
    ): Call<ResponseBody>

    @GET
    fun getApi(@Url url: String, @QueryMap data: HashMap<String, Any?>): Call<ResponseBody>
}
