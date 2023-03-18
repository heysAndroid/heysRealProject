package com.example.heysrealprojcet.di

import com.example.heysrealprojcet.api.*
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

/*
 * 네트워크 설정 파일
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
   const val base_url = "http://heys-dev-public-alb-1078245957.ap-northeast-2.elb.amazonaws.com/"

   @Provides
   @Singleton
   fun provideHttpClient(): OkHttpClient {
      return OkHttpClient.Builder()
         .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
         .readTimeout(15, TimeUnit.SECONDS)
         .connectTimeout(15, TimeUnit.SECONDS)
         .writeTimeout(15, TimeUnit.SECONDS)
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

   @Provides
   @Singleton
   fun provideUserApiService(retrofit: Retrofit): UserApi {
      return retrofit.create(UserApi::class.java)
   }

   @Provides
   @Singleton
   fun provideStudyApiService(retrofit: Retrofit): StudyApi {
      return retrofit.create(StudyApi::class.java)
   }

   @Provides
   @Singleton
   fun provideChannelApiService(retrofit: Retrofit): ChannelApi {
      return retrofit.create(ChannelApi::class.java)
   }
}