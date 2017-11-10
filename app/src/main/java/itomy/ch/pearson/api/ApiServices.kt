package itomy.ch.pearson.api

import android.arch.lifecycle.LiveData
import itomy.ch.pearson.api.retrofit_utils.LiveDataCallAdapterFactory
import itomy.ch.pearson.model.AccessToken
import itomy.ch.pearson.model.AuthBody
import itomy.ch.pearson.model.Course
import itomy.ch.pearson.model.User
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


/**
 * Created by Yegor on 11/10/17.
 */
private const val BASE_URL = "http://auth.pulse.pearson.com"
private const val DEVICE_ID_KEY = "deviceid"
private const val DEVICE_ID_VALUE = "b5288239-b29b-4f4a-b231-618cdd6c776e"
private const val APP_VERSION_KEY = "appVersion"
private const val APP_VERSION_VALUE = "1.0"
private const val ACCEPT_LANGUAGE_KEY = "accept-language"
private const val ACCEPT_LANGUAGE_VALUE = "en"

interface PearsonApiService {

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("/user/authenticate")
    fun authenticate(@Body authBody: AuthBody): LiveData<ApiResponse<AccessToken>>

    @GET("/user")
    fun getUser(@Header("authorization") tokenHeader: String): LiveData<ApiResponse<User>>

    @GET("/user/logout")
    fun logout(@Header("authorization") tokenHeader: String): LiveData<ApiResponse<ResponseBody>>

    companion object {
        fun getInstance(): PearsonApiService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .baseUrl(BASE_URL)
                    .client(createClient())
                    .build()
            return retrofit.create(PearsonApiService::class.java)
        }
    }
}

interface PulseApiService {

    @GET("/coursesection")
    fun getCourseSection(@Header("authorization") tokenHeader: String, @Query("schoolId") schoolId: String): LiveData<ApiResponse<List<Course>>>

    companion object {
        fun getInstance(url: String): PulseApiService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .baseUrl(url)
                    .client(createClient())
                    .build()
            return retrofit.create(PulseApiService::class.java)

        }
    }
}

private fun createClient(): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
                .addHeader(DEVICE_ID_KEY, DEVICE_ID_VALUE)
                .addHeader(APP_VERSION_KEY, APP_VERSION_VALUE)
                .addHeader(ACCEPT_LANGUAGE_KEY, ACCEPT_LANGUAGE_VALUE)
                .method(original.method(), original.body())
        val request = requestBuilder.build()
        it.proceed(request)
    }.build()
}