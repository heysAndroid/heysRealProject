package com.example.heysrealprojcet.repository

import com.example.heysrealprojcet.api.UserApi
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.MyPageResponse
import com.example.heysrealprojcet.model.network.response.UsersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MyPageRepository @Inject constructor(
   private val userApi: UserApi) : BaseApiResponse() {
   fun getMyInfo(token: String): Flow<NetworkResult<MyPageResponse>> {
      return flow {
         emit(safeApiCall { userApi.getMyInfo(token) })
      }.flowOn(Dispatchers.IO)
   }

   fun getUsers(token: String, userId: Int): Flow<NetworkResult<UsersResponse>> {
      return flow {
         emit(safeApiCall { userApi.getUserInfo(token, userId) })
      }.flowOn(Dispatchers.IO)
   }
}