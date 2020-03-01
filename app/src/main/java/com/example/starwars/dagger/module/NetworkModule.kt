package com.example.starwars.dagger.module

import com.example.starwars.api.ApiService
import com.example.starwars.api.SearchRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object{
        private const val SW_BASE_URL = "https://swapi.co/api/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val newBuilder = httpBuilder.build().newBuilder().addInterceptor(httpLoggingInterceptor)
        return newBuilder.build()
    }

    @Singleton
    @Provides
    fun providesRetrofitService(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SW_BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit): ApiService {
        return retrofit.create<ApiService>(ApiService::class.java)
    }

    @Provides @Singleton
    fun provideRepositoryAPI(apiService : ApiService): SearchRepository {
        return SearchRepository(apiService)
    }
}