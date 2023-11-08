package com.example.heysrealprojcet.api

import com.example.heysrealprojcet.model.network.MyPageEdit
import com.example.heysrealprojcet.model.network.response.DeviceTokenResponse
import com.example.heysrealprojcet.model.network.response.MyPageResponse
import com.example.heysrealprojcet.model.network.response.NotificationResponse
import com.example.heysrealprojcet.model.network.response.SimpleResponse
import com.example.heysrealprojcet.model.network.response.UsersResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
   @GET("/app/me")
   suspend fun getMyInfo(@Header("Authorization") token: String): Response<MyPageResponse>

   @PUT("/app/me")
   suspend fun editMyInfo(@Header("Authorization") token: String, @Body myPageEdit: MyPageEdit): Response<MyPageResponse>

   @GET("/app/users/{userId}")
   suspend fun getUserInfo(@Header("Authorization") token: String, @Path(value = "userId") userId: Int): Response<UsersResponse>

   @POST("/app/device/{token}")
   suspend fun postDeviceToken(@Header("Authorization") token: String, @Path(value = "token") deviceToken: String): Response<DeviceTokenResponse>

   @DELETE("/app/device/{token}")
   suspend fun deleteDeviceToken(@Header("Authorization") token: String, @Path(value = "token") deviceToken: String): Response<DeviceTokenResponse>

   @GET("/app/notifications")
   suspend fun getNotifications(@Header("Authorization") token: String): Response<NotificationResponse>

   @PUT("/user/withDrawal")
   suspend fun withdrawal(@Header("Authorization") token: String, @Body reason: String): Response<SimpleResponse>
}