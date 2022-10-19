package com.example.heysrealprojcet.repository

import com.example.heysrealprojcet.api.CommonApi
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.PingResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PingRepository @Inject constructor(
   private val commonApi: CommonApi) : BaseApiResponse() {
   suspend fun ping(): Flow<NetworkResult<PingResponse>> {
      return flow {
         emit(safeApiCall { commonApi.ping() })
      }.flowOn(Dispatchers.IO)
   }
}