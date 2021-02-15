package com.andriiginting.core_network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class MuviNetworkModule(private val url: String) {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    @Named("MuviHomeService")
    fun providesRetrofit(@Named("MuviHttpCliet") okHttpClient: OkHttpClient): Retrofit {
        return retrofitFactory(okHttpClient, url)
    }

    @Provides
    @Singleton
    fun provideHomeServices(@Named("MuviHomeService") retrofit: Retrofit): MuviHomeService {
        return retrofit.create(MuviHomeService::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailServices(@Named("MuviHomeService") retrofit: Retrofit): MuviDetailService {
        return retrofit.create(MuviDetailService::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchServices(@Named("MuviHomeService") retrofit: Retrofit): MuviSearchService {
        return retrofit.create(MuviSearchService::class.java)
    }

    @Provides
    @Singleton
    @Named("MuviHttpCliet")
    fun provideHttpClient(): OkHttpClient = okHttpClientFactory()

    private fun retrofitFactory(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
    }

    private fun okHttpClientFactory(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(defaultHTTPClient())
            .addInterceptor(serviceHTTPClient())
            .addInterceptor(httpLoggingInterceptor())
            .certificatePinner(addCertificate())
            .readTimeout(25, TimeUnit.SECONDS)
            .connectTimeout(25, TimeUnit.SECONDS)
            .build()
    }

    @Throws(IOException::class)
    private fun defaultHTTPClient(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
            return@Interceptor chain.proceed(request)
        }
    }

    @Throws(IOException::class)
    private fun serviceHTTPClient(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val requestUrl = original
                .url()
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            val requestBuilder = original.newBuilder().url(requestUrl)
                .build()
            return@Interceptor chain.proceed(requestBuilder)
        }
    }

    private fun addCertificate(): CertificatePinner {
        return CertificatePinner.Builder()
            .add(BuildConfig.HOST_BASE_URL, "+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .add(BuildConfig.IMAGE_BASE_URL, "dkkdrQXG/soxr05PnqVNAas2Cl7nDyOp2iRkEHQ+wk0=")
            .build()
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

}