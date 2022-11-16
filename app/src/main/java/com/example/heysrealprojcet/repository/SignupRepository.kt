package com.example.heysrealprojcet.repository

import com.example.heysrealprojcet.api.SignUpApi
import com.example.heysrealprojcet.model.User
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.LoginResponse
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
   suspend fun signUp(user: User): Flow<NetworkResult<LoginResponse>> {
      return flow {
         // api 응답을 방출한다.
         emit(safeApiCall { signUpApi.signUp(user) })
      }.flowOn(Dispatchers.IO)
   }
}