package com.vanillaplacepicker.domain.repository

import com.vanillaplacepicker.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by mind on 07/09/18.
 */
class WebApiClient {
    companion object {
        private const val BASE_URL = "https://maps.googleapis.com/maps/api/place/"
        val webApi: WebApi by lazy {
            val logging = getLoggingInstance()

            // here we are defining client and making configuration for read/connection time out.
            // also adding interceptor which we use for api logging.
            val client = OkHttpClient.Builder()
                .readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .build()
                    chain.proceed(request)
                }.addInterceptor(logging).build()

            // preparing retrofit builder and adding
            val retrofit = retrofitInstance(client)

            retrofit.create(WebApi::class.java)
        }

        private fun getLoggingInstance(): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.NONE
            }
            return logging
        }

        private fun retrofitInstance(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
