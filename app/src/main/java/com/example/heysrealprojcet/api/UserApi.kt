package com.example.heysrealprojcet.api

import com.example.heysrealprojcet.model.network.response.MyPageResponse
import com.example.heysrealprojcet.model.network.response.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserApi {
   @GET("/app/me")
   suspend fun getMyInfo(@Header("Authorization") token: String): Response<MyPageResponse>

   @GET("/app/users/{userId}")
   suspend fun getUserInfo(@Header("Authorization") token: String, @Path(value = "userId") userId: Int): Response<UsersResponse>
}