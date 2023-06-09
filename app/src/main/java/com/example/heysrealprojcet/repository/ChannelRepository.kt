package com.example.heysrealprojcet.repository

import com.example.heysrealprojcet.api.ChannelApi
import com.example.heysrealprojcet.model.network.Study
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.ChannelDetailResponse
import com.example.heysrealprojcet.model.network.response.ChannelListResponse
import com.example.heysrealprojcet.model.network.response.CreateStudyResponse
import com.example.heysrealprojcet.model.network.response.MyChannelListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChannelRepository @Inject constructor(
   private val channelApi: ChannelApi
) : BaseApiResponse() {
   fun getChannelDetail(token: String, id: Int): Flow<NetworkResult<ChannelDetailResponse>> {
      return flow {
         emit(safeApiCall { channelApi.getChannelDetail(token, id) })
      }.flowOn(Dispatchers.IO)
   }

   fun getMyChannelList(token: String): Flow<NetworkResult<MyChannelListResponse>> {
      return flow {
         emit(safeApiCall { channelApi.getMyChannelList(token) })
      }.flowOn(Dispatchers.IO)
   }

   fun getContentChannelList(
      token: String,
      id: Int,
      interest: ArrayList<String>?,
      lastRecruitDate: String?,
      purposes: ArrayList<String>?,
      online: String?,
      location: String?,
      includeClosed: Boolean?,
      page: Int?,
      limit: Int?
   ): Flow<NetworkResult<ChannelListResponse>> {
      return flow {
         emit(safeApiCall { channelApi.getContentChannelList(token, id, interest, lastRecruitDate, purposes, online, location, includeClosed, page, limit) })
      }.flowOn(Dispatchers.IO)
   }

   fun createContentChannel(token: String, contentId: Int, channel: Study): Flow<NetworkResult<CreateStudyResponse>> {
      return flow {
         emit(safeApiCall { channelApi.createContentChannel(token, contentId, channel) })
      }.flowOn(Dispatchers.IO)
   }
}