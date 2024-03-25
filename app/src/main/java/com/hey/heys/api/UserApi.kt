package com.hey.heys.api

import com.hey.heys.model.network.response.DeviceTokenResponse
import com.hey.heys.model.network.response.NotificationResponse
import com.hey.heys.model.network.response.UserEditResponse
import com.hey.heys.model.network.response.UsersResponse
import com.hey.heys.model.network.MyPageEdit
import com.hey.heys.model.network.Password
import com.hey.heys.model.network.UserEdit
import com.hey.heys.model.network.WithdrawalReason
import com.hey.heys.model.network.response.DefaultResponse
import com.hey.heys.model.network.response.MyPageResponse
import com.hey.heys.model.network.response.NotificationExistResponse
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

   @GET("/app/notifications/new")
   suspend fun getNotificationsExist(@Header("Authorization") token: String): Response<NotificationExistResponse>

   @PUT("/user/withDrawal")
   suspend fun withdrawal(@Header("Authorization") token: String, @Body reason: WithdrawalReason): Response<DefaultResponse>

   @PUT("app/me/phone")
   suspend fun changePhoneNumber(@Header("Authorization") token: String, @Body user: UserEdit): Response<UserEditResponse>

   @PUT("user/password")
   suspend fun changePassword(@Body password: Password): Response<DefaultResponse>
}