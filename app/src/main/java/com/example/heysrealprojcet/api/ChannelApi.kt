package com.example.heysrealprojcet.api

import com.example.heysrealprojcet.model.network.response.ChannelDetailResponse
import com.example.heysrealprojcet.model.network.response.MyChannelListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ChannelApi {
   @GET("channel/{channelId}")
   suspend fun getChannelDetail(@Header("Authorization") token: String, @Path(value = "channelId") channelId: Int): Response<ChannelDetailResponse>

   @GET("channel/my")
   suspend fun getMyChannelList(@Header("Authorization") token: String): Response<MyChannelListResponse>
}