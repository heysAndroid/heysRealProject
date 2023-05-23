package com.example.heysrealprojcet.api

import com.example.heysrealprojcet.model.network.response.ChannelDetailResponse
import com.example.heysrealprojcet.model.network.response.ChannelListResponse
import com.example.heysrealprojcet.model.network.response.MyChannelListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ChannelApi {
   @GET("channel/{channelId}")
   suspend fun getChannelDetail(@Header("Authorization") token: String, @Path(value = "channelId") channelId: Int): Response<ChannelDetailResponse>

   @GET("channel/my")
   suspend fun getMyChannelList(@Header("Authorization") token: String): Response<MyChannelListResponse>

   @GET("channel/extra/{channelId}")
   suspend fun getContentChannelList(
      @Header("Authorization") token: String,
      @Path(value = "channelId") channelId: Int,
      @Query("interests") interests: ArrayList<String>?,
      @Query("lastRecruitDate") lastRecruitDate: String?,
      @Query("purposes") purposes: ArrayList<String>?,
      @Query("online") online: String?,
      @Query("location") location: String?,
      @Query("includeClosed") includeClosed: Boolean?,
      @Query("page") page: Int?,
      @Query("limit") limit: Int?,
   ): Response<ChannelListResponse>
}