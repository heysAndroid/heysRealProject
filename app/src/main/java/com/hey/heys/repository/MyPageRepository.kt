package com.hey.heys.repository

import com.example.heys.model.network.response.DeviceTokenResponse
import com.example.heys.model.network.response.NotificationResponse
import com.example.heys.model.network.response.UserEditResponse
import com.example.heys.model.network.response.UsersResponse
import com.hey.heys.api.UserApi
import com.hey.heys.model.network.MyPageEdit
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.Password
import com.hey.heys.model.network.UserEdit
import com.hey.heys.model.network.WithdrawalReason
import com.hey.heys.model.network.response.DefaultResponse
import com.hey.heys.model.network.response.MyPageResponse
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

   fun changePhoneNumber(token: String, user: UserEdit): Flow<NetworkResult<UserEditResponse>> {
      return flow {
         emit(safeApiCall { userApi.changePhoneNumber(token, user) })
      }.flowOn(Dispatchers.IO)
   }

   fun withdrawal(token: String, reason: WithdrawalReason): Flow<NetworkResult<DefaultResponse>> {
      return flow {
         emit(safeApiCall { userApi.withdrawal(token, reason) })
      }.flowOn(Dispatchers.IO)
   }

   fun changePassword(password: Password): Flow<NetworkResult<DefaultResponse>> {
      return flow {
         emit(safeApiCall { userApi.changePassword(password) })
      }.flowOn(Dispatchers.IO)
   }
}