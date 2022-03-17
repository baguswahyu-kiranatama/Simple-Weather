package studio.koerniax.simpleweatherapps.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import studio.koerniax.simpleweatherapps.BuildConfig
import studio.koerniax.simpleweatherapps.data.network.ApiService
import studio.koerniax.simpleweatherapps.utils.Constants

/**
 * Created by KOERNIAX at 16/03/22
 */
object NetworkModule {

    val modules = module {
        single { provideOkHttpClient() }
        single { provideRetrofit(get()) }
        single { provideAPI(get()) }
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient().newBuilder()
            .addInterceptor { chain ->
                val request = chain.request()
                val url =
                    request.url.newBuilder().addQueryParameter("appid", BuildConfig.API_KEY).build()
                chain.proceed(request.newBuilder().url(url).build())
            }
            .addInterceptor(interceptor)
            .build()
    }

    private fun provideAPI(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}