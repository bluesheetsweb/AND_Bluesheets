package src.networkutil.network

import okhttp3.CertificatePinner
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import src.networkutil.utilities.NetworkConstant
import java.util.concurrent.TimeUnit

object NetworkManager {

    private val TAG = NetworkManager::class.java.simpleName

    fun getOldClient(header: NetworkRequestHeader): NetworkService {
        val authToken = Credentials.basic(InitNetworkUtils.app_auth, "")
        header.authToken = authToken
        val okHttpClientG = getHttClientBuilder()
            .connectTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)

        val builder = Retrofit.Builder().baseUrl(InitNetworkUtils.server_path)
        //                .addConverterFactory(GsonConverterFactory.create());

//        val interceptor = HeaderOldAPI(authToken, authkey, apiVersion, app_version, lang, DeviceType, token_id)
        val interceptor = RetrofitOldApiHeader(header)
//        val interceptor = DCRetrofitOldApiHeader(
//                DCNetworkRequestHeader(
//                        authToken = authToken,
//                        authKey = authkey
//                        apiVersion = apiVersion,
//                        appVersion = app_version,
//                        lang = lang,
//                        tokenId = token_id))

        if (!okHttpClientG.interceptors().contains(interceptor)) {
            okHttpClientG.addInterceptor(interceptor)
            builder.client(okHttpClientG.build())
        }

        return builder.build().create(NetworkService::class.java)
    }

    fun getThirdPartyClient(): NetworkService {
        val okHttpClientG = getHttClientBuilder()
            .connectTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)

        val builder = Retrofit.Builder().baseUrl(InitNetworkUtils.server_path)
        //                .addConverterFactory(GsonConverterFactory.create());

//        val interceptor = HeaderOldAPI(authToken, authkey, apiVersion, app_version, lang, DeviceType, token_id)
        val interceptor = ThirdPartyClient()
//        val interceptor = DCRetrofitOldApiHeader(
//                DCNetworkRequestHeader(
//                        authToken = authToken,
//                        authKey = authkey,
//                        apiVersion = apiVersion,
//                        appVersion = app_version,
//                        lang = lang,
//                        tokenId = token_id))

        if (!okHttpClientG.interceptors().contains(interceptor)) {
            okHttpClientG.addInterceptor(interceptor)
            builder.client(okHttpClientG.build())
        }

        return builder.build().create(NetworkService::class.java)
    }

    fun getOldClientWithoutInterceptor(header: NetworkRequestHeader): NetworkService {
        val authToken = Credentials.basic(InitNetworkUtils.app_auth, "")
        header.authToken = authToken
        val okHttpClientG = getHttClientBuilderWithoutInterceptor()
            .connectTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)

        val builder = Retrofit.Builder().baseUrl(InitNetworkUtils.server_path)
        //                .addConverterFactory(GsonConverterFactory.create());

//        val interceptor = HeaderOldAPI(authToken, authkey, apiVersion, app_version, lang, DeviceType, token_id)
        val interceptor = RetrofitOldApiHeader(header)
//        val interceptor = DCRetrofitOldApiHeader(
//                DCNetworkRequestHeader(
//                        authToken = authToken,
//                        authKey = authkey,
//                        apiVersion = apiVersion,
//                        appVersion = app_version,
//                        lang = lang,
//                        tokenId = token_id))

        if (!okHttpClientG.interceptors().contains(interceptor)) {
            okHttpClientG.addInterceptor(interceptor)
            builder.client(okHttpClientG.build())
        }

        return builder.build().create(NetworkService::class.java)
    }

    fun getNewClient(header: NetworkRequestHeader): NetworkService {
//        val authToken = Credentials.basic(InitNetworkUtils.app_auth, "")
//        Log.e(TAG, "authToken is $authToken")
//        header.authToken = authToken

        val okHttpClientG = getHttClientBuilder()
            .connectTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)

//        val gson = GsonBuilder()
//                .setLenient()
//                .create()
        val builder = Retrofit.Builder().baseUrl(InitNetworkUtils.server_path)
//                .addConverterFactory(GsonConverterFactory.create(gson))

        val interceptor = RetrofitNewApiHeader(header)

        if (!okHttpClientG.interceptors().contains(interceptor)) {
            okHttpClientG.addInterceptor(interceptor)
            builder.client(okHttpClientG.build())
        }

        return builder.build().create(NetworkService::class.java)
    }

    fun getNewCustomClient(
        key: String,
        value: String,
        header: NetworkRequestHeader
    ): NetworkService {

        val okHttpClientG = getHttClientBuilder()
            .connectTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(NetworkConstant.NETWORK_REQUEST_TIME_OUT.toLong(), TimeUnit.SECONDS)

//        val gson = GsonBuilder()
//                .setLenient()
//                .create()
        val builder = Retrofit.Builder().baseUrl(InitNetworkUtils.server_path2)
//                .addConverterFactory(GsonConverterFactory.create(gson))

//        val interceptor = HeaderNewAPI(authToken, authkey, apiVersion,
//                app_version, lang, iso_code, device_type,
//                auth_token_onboarding)
        val interceptor = RetrofitNewCustomHeader(key, value, header)
//        val interceptor = DCRetrofitNewApiHeader(
//                DCNetworkRequestHeader(
//                        authToken = authToken,
//                        authKey = authkey,
//                        apiVersion = apiVersion,
//                        appVersion = app_version,
//                        lang = lang,
//                        tokenId = auth_token_onboarding))

        if (!okHttpClientG.interceptors().contains(interceptor)) {
            okHttpClientG.addInterceptor(interceptor)
            builder.client(okHttpClientG.build())
        }

        return builder.build().create(NetworkService::class.java)
    }

    private fun getHttClientBuilder(): OkHttpClient.Builder {
        val interceptor1 = HttpLoggingInterceptor()
        interceptor1.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor1)
            .certificatePinner(getCertificatePinner())
        // return new OkHttpClient.Builder();
    }

    private fun getHttClientBuilderWithoutInterceptor(): OkHttpClient.Builder {
        val interceptor1 = HttpLoggingInterceptor()
        // TODO changed for chunck upload
        interceptor1.level = HttpLoggingInterceptor.Level.NONE
        // interceptor1.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor1)
            .certificatePinner(getCertificatePinner())
        // return new OkHttpClient.Builder();
    }

    fun getCertificatePinner(): CertificatePinner {

        val certificatePinner = CertificatePinner.Builder()

//        if (BuildConfig.FLAVOR.contains("production")) {
//           // Log.e(TAG,"certificate pinner if")
//            try {
//                certificatePinner
//                        .add(URI(InitNetworkUtils.server_path).host,
//                                "sha256/wnEuMiTDF4z9dtG8PHSxJEHvwvBDKHfjgDl4IpOFbtE="
//                        ).add(URI(InitNetworkUtils.server_path).host,
//                                "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8="
//                        ).add(URI(InitNetworkUtils.server_path).host,
//                                "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jwXvHqVvQI0GS3GNdA="
//                        ).add(URI(InitNetworkUtils.server_path2).host,
//                                "sha256/wnEuMiTDF4z9dtG8PHSxJEHvwvBDKHfjgDl4IpOFbtE="
//                        ).add(URI(InitNetworkUtils.server_path2).host,
//                                "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8="
//                        ).add(URI(InitNetworkUtils.server_path2).host,
//                                "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jwXvHqVvQI0GS3GNdA="
//                        ).add(URI(InitNetworkUtils.analytics_path).host,
//                                "sha256/wnEuMiTDF4z9dtG8PHSxJEHvwvBDKHfjgDl4IpOFbtE="
//                        ).add(URI(InitNetworkUtils.analytics_path).host,
//                                "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8="
//                        ).add(URI(InitNetworkUtils.analytics_path).host,
//                                "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jwXvHqVvQI0GS3GNdA="
//                        )
//            }catch (ex:Exception){
//                ex.printStackTrace()
//            }
//        }
        return certificatePinner.build()
    }
}
