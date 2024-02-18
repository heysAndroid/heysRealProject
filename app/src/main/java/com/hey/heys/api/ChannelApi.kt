package com.hey.heys.api

import com.hey.heys.model.network.Study
import com.hey.heys.model.network.response.ChannelDetailResponse
import com.hey.heys.model.network.response.ChannelFollowerListResponse
import com.hey.heys.model.network.response.ChannelListResponse
import com.hey.heys.model.network.response.CreateStudyResponse
import com.hey.heys.model.network.response.MyChannelListResponse
import com.hey.heys.model.network.response.SimpleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ChannelApi {
   @GET("channel/{channelId}")
   suspend fun getChannelDetail(@Header("Authorization") token: String, @Path(value = "channelId") channelId: Int): Response<ChannelDetailResponse>

   @GET("channel/my")
   suspend fun getMyChannelList(@Header("Authorization") token: String): Response<MyChannelListResponse>

   @GET("channel/extra/{contentId}")
   suspend fun getContentChannelList(
      @Header("Authorization") token: String,
      @Path(value = "contentId") contentId: Int,
      @Query("interests", encoded = true) interests: ArrayList<String>?,
      @Query("lastRecruitDate", encoded = true) lastRecruitDate: String?,
      @Query("purposes") purposes: ArrayList<String>?,
      @Query("online") online: String?,
      @Query("location") location: String?,
      @Query("includeClosed") includeClosed: Boolean?,
      @Query("page") page: Int?,
      @Query("limit") limit: Int?,
   ): Response<ChannelListResponse>

   @POST("channel/extra/{contentId}")
   suspend fun createContentChannel(
      @Header("Authorization") token: String,
      @Path(value = "contentId") contentId: Int,
      @Body channel: Study
   ): Response<CreateStudyResponse>

   @PUT("channel/view-count-up/{channelId}")
   suspend fun channelViewCountUp(@Header("Authorization") token: String, @Path(value = "channelId") id: Int): Response<SimpleResponse>

   @PUT("channel/add-bookmark/{channelId}")
   suspend fun channelAddBookmark(@Header("Authorization") token: String, @Path(value = "channelId") id: Int): Response<SimpleResponse>

   @PUT("channel/remove-bookmark/{channelId}")
   suspend fun channelRemoveBookmark(@Header("Authorization") token: String, @Path(value = "channelId") id: Int): Response<SimpleResponse>

   @POST("channel/join/{channelId}")
   suspend fun joinChannel(
      @Header("Authorization") token: String,
      @Path(value = "channelId") id: Int
   ): Response<SimpleResponse>

   @GET("channel/follow/{channelId}/{status}")
   suspend fun getChannelFollower(
      @Header("Authorization") token: String,
      @Path(value = "channelId") id: Int,
      @Path(value = "status") status: String): Response<ChannelFollowerListResponse>

   @PUT("channel/request-reject/{channelId}/{followerId}")
   suspend fun putRequestReject(
      @Header("Authorization") token: String,
      @Path(value = "channelId") channelId: Int,
      @Path(value = "followerId") followerId: Int,
      @Body message: SimpleResponse
   ): Response<SimpleResponse>

   @PUT("channel/request-allow/{channelId}/{followerId}")
   suspend fun putRequestAllow(
      @Header("Authorization") token: String,
      @Path(value = "channelId") channelId: Int,
      @Path(value = "followerId") followerId: Int,
      @Body message: SimpleResponse
   ): Response<SimpleResponse>

   @GET("channel/my/{status}")
   suspend fun getMyChannel(
      @Header("Authorization") token: String,
      @Path(value = "status") status: String): Response<MyChannelListResponse>

   // 채널 탈퇴
   @PUT("channel/member-exit-channel/{channelId}")
   suspend fun putExitChannel(
      @Header("Authorization") token: String,
      @Path(value = "channelId") channelId: Int,
      @Body message: SimpleResponse
   ): Response<SimpleResponse>

   // 채널 가입 요청 취소
   @PUT("channel/member-abort-request/{channelId}")
   suspend fun putMemberAbort(
      @Header("Authorization") token: String,
      @Path(value = "channelId") channelId: Int,
      @Body message: SimpleResponse
   ): Response<SimpleResponse>

   // 모든 채널 리스트
   @GET("channel")
   suspend fun getAllChannelList(
      @Header("Authorization") token: String,
      @Query("interests", encoded = true) interests: ArrayList<String>?,
      @Query("lastRecruitDate", encoded = true) lastRecruitDate: String?,
      @Query("purposes") purposes: ArrayList<String>?,
      @Query("online") online: String?,
      @Query("location", encoded = true) location: String?,
      @Query("includeClosed") includeClosed: Boolean?,
      @Query("page") page: Int?,
      @Query("limit") limit: Int?,
   ): Response<ChannelListResponse>
}