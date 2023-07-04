package com.example.heysrealprojcet.api

import com.example.heysrealprojcet.model.network.Phone
import com.example.heysrealprojcet.model.network.User
import com.example.heysrealprojcet.model.network.response.CheckPhoneNumberResponse
import com.example.heysrealprojcet.model.network.response.PhoneResponse
import com.example.heysrealprojcet.model.network.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.*

/*
* 회원가입 api
 */

interface SignUpApi {
   // suspend function : 실행되다가 중지되었다가, 다시 실행될 수 있음
   // => blocking 없이 시간이 오래 걸리는 작업도 수행 가능함
   @POST("user/signUp")
   suspend fun signUp(@Body user: User): Response<SignUpResponse>

   @GET("user/login")
   suspend fun login(@Query("username") username: String, @Query("password") password: String): Response<Void>

   @PUT("user/check-member")
   suspend fun checkPhoneNumber(@Body phone: Phone): Response<CheckPhoneNumberResponse>

   @POST("app/code")
   suspend fun postPhoneVerification(@Body phone: Phone): Response<PhoneResponse>
}