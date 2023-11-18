package com.example.heys.repository

import com.example.heys.api.UserApi
import com.example.heys.model.network.MyPageEdit
import com.example.heys.model.network.NetworkResult
import com.example.heys.model.network.response.DeviceTokenResponse
import com.example.heys.model.network.response.MyPageResponse
import com.example.heys.model.network.response.NotificationResponse
import com.example.heys.model.network.response.SimpleResponse
import com.example.heys.model.network.response.UsersResponse
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

   fun editMyInfo(token: String, myPageEdit: MyPageEdit): Flow<NetworkResult<MyPageResponse>> {
      return flow {
         emit(safeApiCall { userApi.editMyInfo(token, myPageEdit) })
      }.flowOn(Dispatchers.IO)
   }

   fun getUsers(token: String, userId: Int): Flow<NetworkResult<UsersResponse>> {
      return flow {
         emit(safeApiCall { userApi.getUserInfo(token, userId) })
      }.flowOn(Dispatchers.IO)
   }

   fun postDeviceToken(token: String, deviceToken: String): Flow<NetworkResult<DeviceTokenResponse>> {
      return flow {
         emit(safeApiCall { userApi.postDeviceToken(token, deviceToken) })
      }.flowOn(Dispatchers.IO)
   }

   fun deleteDeviceToken(token: String, deviceToken: String): Flow<NetworkResult<DeviceTokenResponse>> {
      return flow {
         emit(safeApiCall { userApi.deleteDeviceToken(token, deviceToken) })
      }.flowOn(Dispatchers.IO)
   }

   fun getNotifications(token: String): Flow<NetworkResult<NotificationResponse>> {
      return flow {
         emit(safeApiCall { userApi.getNotifications(token) })
      }.flowOn(Dispatchers.IO)
   }

   fun withdrawal(token: String, reason: String): Flow<NetworkResult<SimpleResponse>> {
      return flow {
         emit(safeApiCall { userApi.withdrawal(token, reason) })
      }.flowOn(Dispatchers.IO)
   }
}