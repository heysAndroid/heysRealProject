package com.example.heysrealprojcet.repository

import com.example.heysrealprojcet.api.ChannelApi
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.ChannelDetailResponse
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
}