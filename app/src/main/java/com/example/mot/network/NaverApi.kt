package com.example.mot.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NaverApi {
    //요청변수
    @FormUrlEncoded
    @POST("v1/papago/n2mt")
    fun translatePapago(
        @Field("source") source: String,
        @Field("target") target: String,
        @Field("text") text: String
    ): Call<NaverApiResponse>

    companion object {
        private const val BASE_URL_NAVER_API = "https://openapi.naver.com/"
        private const val CLIENT_ID = "WEo167O4mKdBaVkB7qdI"
        private const val CLIENT_SECRET = "uBmxIN5rQs"

        fun create(): NaverApi {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("X-Naver-Client-Id",
                        CLIENT_ID
                    )
                    .addHeader("X-Naver-Client-Secret",
                        CLIENT_SECRET
                    )
                    .build()
                return@Interceptor it.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL_NAVER_API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NaverApi::class.java)
        }
    }
}