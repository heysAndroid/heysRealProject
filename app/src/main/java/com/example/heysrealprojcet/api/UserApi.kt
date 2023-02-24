package com.example.heysrealprojcet.api

import com.example.heysrealprojcet.model.network.response.MyPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {
   @GET("/app/me")
   suspend fun getMyInfo(@Header("Authorization") token: String): Response<MyPageResponse>
}