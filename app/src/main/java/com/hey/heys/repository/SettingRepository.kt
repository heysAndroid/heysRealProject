package com.hey.heys.repository

import com.hey.heys.api.SettingApi
import com.hey.heys.model.network.response.SignUpResponse
import com.hey.heys.model.network.NetworkResult
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