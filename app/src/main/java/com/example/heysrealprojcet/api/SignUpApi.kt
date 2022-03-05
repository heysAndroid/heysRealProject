package com.example.heysrealprojcet.api

import com.example.heysrealprojcet.model.Response
import com.example.heysrealprojcet.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApi {
   @POST("auth/signup")
   suspend fun signup(@Body user: User): retrofit2.Response<Response>
}