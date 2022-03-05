package com.example.heysrealprojcet.di

import com.example.heysrealprojcet.api.SettingApi
import com.example.heysrealprojcet.api.SignUpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
   const val base_url = "http://35.208.91.78/api/"

   @Provides
   @Singleton
   fun provideHttpClient(): OkHttpClient {
      return OkHttpClient.Builder()
         .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
         .readTimeout(10, TimeUnit.SECONDS)
         .connectTimeout(10, TimeUnit.SECONDS)
         .writeTimeout(10, TimeUnit.SECONDS)
         .build()
   }

   @Singleton
   @Provides
   fun provideRetrofitInstance(
      okHttpClient: OkHttpClient,
      gsonConverterFactory: GsonConverterFactory): Retrofit {
      return Retrofit.Builder()
         .baseUrl(base_url)
         .client(okHttpClient)
         .client(provideHttpClient())
         .addConverterFactory(gsonConverterFactory).build()
   }

   @Provides
   @Singleton
   fun provideConverterFactory(): GsonConverterFactory {
      return GsonConverterFactory.create()
   }

   @Provides
   @Singleton
   fun provideSettingApiService(retrofit: Retrofit): SettingApi {
      return retrofit.create(SettingApi::class.java)
   }

   @Provides
   @Singleton
   fun provideSignUpApiService(retrofit: Retrofit): SignUpApi {
      return retrofit.create(SignUpApi::class.java)
   }
}