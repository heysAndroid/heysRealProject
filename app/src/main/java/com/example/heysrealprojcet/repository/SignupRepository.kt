package com.example.heysrealprojcet.repository

import com.example.heysrealprojcet.api.SignUpApi
import com.example.heysrealprojcet.model.LoginResponse
import com.example.heysrealprojcet.model.User
import com.example.heysrealprojcet.model.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignupRepository @Inject constructor(
   private val signUpApi: SignUpApi) : BaseApiResponse() {
   suspend fun signup(user: User): Flow<NetworkResult<LoginResponse>> {
      return flow {
         emit(safeApiCall { signUpApi.signup(user) })
      }.flowOn(Dispatchers.IO)
   }
}