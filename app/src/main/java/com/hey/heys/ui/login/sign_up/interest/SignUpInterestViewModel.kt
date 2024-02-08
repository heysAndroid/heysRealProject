package com.hey.heys.ui.login.sign_up.interest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hey.heys.Event
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.User
import com.hey.heys.model.network.response.SignUpResponse
import com.hey.heys.repository.MyPageRepository
import com.hey.heys.repository.SignupRepository
import com.hey.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

// Hilt 로 생성자에 repository 전달
@HiltViewModel
class SignUpInterestViewModel @Inject constructor(
   private val signupRepository: SignupRepository, private val myPageRepository: MyPageRepository) : BaseViewModel() {

   private val errorMessage = MutableLiveData<String>()
   private val loading = MutableLiveData<Boolean>()
   var job: Job? = null

   /*
   * 네트워크 호출
    */
   private val _responseSignUp: MutableLiveData<NetworkResult<SignUpResponse>> = MutableLiveData()
   val responseSignUp: LiveData<NetworkResult<SignUpResponse>> = _responseSignUp

   private val _responseLogin: MutableLiveData<com.hey.heys.Event<Response<Void>>> = MutableLiveData()
   val responseLogin: LiveData<com.hey.heys.Event<Response<Void>>> = _responseLogin

   fun signUp(user: User) = viewModelScope.launch {
      // repository 의 함수를 호출해서 _response 에 api 응답을 저장
      signupRepository.signUp(user).collect { values ->
         _responseSignUp.value = values
      }
   }

   private fun onError(message: String) {
      errorMessage.value = message
      loading.value = false
   }

   override fun onCleared() {
      super.onCleared()
      job?.cancel()
   }

   fun login(username: String, password: String) {
      job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
         _responseLogin.postValue(com.hey.heys.Event(signupRepository.loginApi(username, password)))
      }
   }

   fun postDeviceToken(token: String, deviceToken: String) =
      myPageRepository.postDeviceToken(token, deviceToken).asLiveData()

}