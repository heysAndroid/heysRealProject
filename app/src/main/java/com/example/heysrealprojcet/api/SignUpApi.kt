package com.example.heysrealprojcet.api

import com.example.heysrealprojcet.model.Phone
import com.example.heysrealprojcet.model.User
import com.example.heysrealprojcet.model.network.response.CheckPhoneNumberResponse
import com.example.heysrealprojcet.model.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

/*
* 회원가입 api
 */

interface SignUpApi {
   // suspend function : 실행되다가 중지되었다가, 다시 실행될 수 있음
   // => blocking 없이 시간이 오래 걸리는 작업도 수행 가능함
   @POST("user/signUp")
   suspend fun signUp(@Body user: User): Response<LoginResponse>

//   @GET("users/{phone_number}/check-member")
//   suspend fun checkPhoneNumber(@Path("phone_number") phoneNumber: String): Response<CheckPhoneNumberResponse>

   @PUT("user/check-member")
   suspend fun checkPhoneNumber(@Body phone: Phone): Response<CheckPhoneNumberResponse>
}