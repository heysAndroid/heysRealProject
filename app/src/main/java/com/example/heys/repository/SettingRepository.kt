package com.example.heys.repository

import com.example.heys.api.SettingApi
import com.example.heys.model.network.response.SignUpResponse
import com.example.heys.model.network.NetworkResult
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