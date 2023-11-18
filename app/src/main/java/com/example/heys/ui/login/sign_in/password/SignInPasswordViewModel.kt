package com.example.heys.ui.login.sign_in.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.heys.Event
import com.example.heys.repository.MyPageRepository
import com.example.heys.repository.SignupRepository
import com.example.heys.ui.base.BaseViewModel
import com.example.heys.util.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignInPasswordViewModel @Inject constructor(
   private val signupRepository: SignupRepository,
   private val myPageRepository: MyPageRepository
) : BaseViewModel() {
   val password = MutableStateFlow("")

   private val _isPasswordVisible = MutableLiveData(false)
   val isPasswordVisible: LiveData<Boolean> = _isPasswordVisible

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   private val _response: MutableLiveData<Event<Response<Void>>> = MutableLiveData()
   val response: LiveData<Event<Response<Void>>> = _response

   private val errorMessage = MutableLiveData<String>()
   private val loading = MutableLiveData<Boolean>()
   var job: Job? = null

   private var _showSnackBarEvent = MutableLiveData(false)
   val showSnackBarEvent: LiveData<Boolean> = _showSnackBarEvent

   init {
      viewModelScope.launch {
         password.collect {
            UserPreference.password = it
            isCorrect()
         }
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


   private fun isCorrect() {
      _isEnabled.value = password.value.length >= 8
   }

   fun togglePasswordVisible() {
      _isPasswordVisible.value?.let {
         _isPasswordVisible.value = !it
      }
   }

   fun showSnackBar() {
      _showSnackBarEvent.value = true
   }

   fun login(username: String, password: String) {
      job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
         _response.postValue(Event(signupRepository.loginApi(username, password)))
         /*
         withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
               Log.w("response header: ", response.headers().get("Authorization").toString())
            } else {
               onError("Error : ${response.message()} ")
            }
         }
          */
      }
   }

   fun postDeviceToken(token: String, deviceToken: String) =
      myPageRepository.postDeviceToken(token, deviceToken).asLiveData()
}