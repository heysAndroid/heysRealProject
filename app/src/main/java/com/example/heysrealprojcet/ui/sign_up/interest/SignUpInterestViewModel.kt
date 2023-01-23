package com.example.heysrealprojcet.ui.sign_up.interest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.heysrealprojcet.model.User
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.SignUpResponse
import com.example.heysrealprojcet.repository.SignupRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Hilt 로 생성자에 repository 전달
@HiltViewModel
class SignUpInterestViewModel @Inject constructor(
   private val signupRepository: SignupRepository) : BaseViewModel() {

   /*
   * 네트워크 호출
    */
   private val _response: MutableLiveData<NetworkResult<SignUpResponse>> = MutableLiveData()
   val response: LiveData<NetworkResult<SignUpResponse>> = _response

   fun signUp(user: User) = viewModelScope.launch {
      // repository 의 함수를 호출해서 _response 에 api 응답을 저장
      signupRepository.signUp(user).collect { values ->
         _response.value = values
      }
   }
}