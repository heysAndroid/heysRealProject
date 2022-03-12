package com.example.heysrealprojcet.api

import com.example.heysrealprojcet.model.LoginResponse
import com.example.heysrealprojcet.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/*
* 회원가입 api
 */

interface SignUpApi {
   // suspend function : 실행되다가 중지되었다가, 다시 실행될 수 있음
   // => blocking 없이 시간이 오래 걸리는 작업도 수행 가능함
   @POST("auth/signup")
   suspend fun signup(@Body user: User): Response<LoginResponse>
}