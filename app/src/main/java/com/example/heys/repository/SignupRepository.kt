package com.example.heys.repository

import com.example.heys.api.SignUpApi
import com.example.heys.model.network.NetworkResult
import com.example.heys.model.network.Phone
import com.example.heys.model.network.PhoneVerification
import com.example.heys.model.network.User
import com.example.heys.model.network.response.CheckPhoneNumberResponse
import com.example.heys.model.network.response.PhoneResponse
import com.example.heys.model.network.response.PhoneVerificationResponse
import com.example.heys.model.network.response.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/*
* Hilt 로 Api 클래스 생성자로 전달 => 객체 간 종속성 줄이기
 */

class SignupRepository @Inject constructor(
   private val signUpApi: SignUpApi) : BaseApiResponse() {
   // BaseApiResponse() 의 safeApiCall 에 signup() 을 인자로 전달
   suspend fun signUp(user: User): Flow<NetworkResult<SignUpResponse>> {
      return flow {
         // api 응답을 방출한다.
         emit(safeApiCall { signUpApi.signUp(user) })
      }.flowOn(Dispatchers.IO)
   }

   fun checkPhoneNumber(phoneNumber: Phone): Flow<NetworkResult<CheckPhoneNumberResponse>> {
      return flow {
         emit(safeApiCall { signUpApi.checkPhoneNumber(phoneNumber) })
      }.flowOn(Dispatchers.IO)
   }

   /*
   suspend fun loginApi(username: String, password: String): Flow<NetworkResult<Void>> {
      return flow {
         emit(safeApiCall { signUpApi.login(username, password) })
      }.flowOn(Dispatchers.IO)
   }
   */

   suspend fun loginApi(username: String, password: String) = signUpApi.login(username, password)

   fun postPhoneVerification(phone: Phone): Flow<NetworkResult<PhoneResponse>> {
      return flow {
         emit(safeApiCall { signUpApi.postPhoneVerification(phone) })
      }.flowOn(Dispatchers.IO)
   }

   fun deletePhoneVerification(phoneVerification: PhoneVerification): Flow<NetworkResult<PhoneVerificationResponse>> {
      return flow {
         emit(safeApiCall { signUpApi.deletePhoneVerification(phoneVerification) })
      }.flowOn(Dispatchers.IO)
   }
}