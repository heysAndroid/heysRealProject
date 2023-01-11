package com.example.heysrealprojcet.repository

import com.example.heysrealprojcet.api.SettingApi
import com.example.heysrealprojcet.model.network.response.SignUpResponse
import com.example.heysrealprojcet.model.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SettingRepository @Inject constructor(
   private val settingApi: SettingApi) : BaseApiResponse() {
   suspend fun getCategory(): Flow<NetworkResult<SignUpResponse>> {
      return flow {
         emit(safeApiCall { settingApi.getCategory() })
      }.flowOn(Dispatchers.IO)
   }
}