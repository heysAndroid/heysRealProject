package com.example.heys.ui.login.sign_in.phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.heys.Event
import com.example.heys.model.network.Phone
import com.example.heys.model.network.NetworkResult
import com.example.heys.model.network.response.CheckPhoneNumberResponse
import com.example.heys.repository.SignupRepository
import com.example.heys.ui.base.BaseViewModel
import com.example.heys.util.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInPhoneViewModel @Inject constructor(
   private val signupRepository: SignupRepository,
) : BaseViewModel() {
   val phoneNumber = MutableLiveData("")

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   private var _showSnackBarEvent = MutableLiveData(false)
   val showSnackBarEvent: LiveData<Boolean> = _showSnackBarEvent

   /*
   * 네트워크 호출
    */
   private val _response: MutableLiveData<Event<NetworkResult<CheckPhoneNumberResponse>>> = MutableLiveData()
   val response: LiveData<Event<NetworkResult<CheckPhoneNumberResponse>>> = _response

   init {
      viewModelScope.launch {
         phoneNumber.asFlow().collect {
            if (phoneNumber.value?.contains('-') == true) {
               UserPreference.phoneNumber = phoneNumber.value?.replace("-", "").toString()
               isElevenDigit()
            }
         }
      }
   }

   private fun isElevenDigit() {
      _isEnabled.value = phoneNumber.value?.length == 13
   }

   fun checkPhoneNumber(phoneNumber: Phone) = viewModelScope.launch {
      signupRepository.checkPhoneNumber(phoneNumber).collect { values ->
         _response.value = Event(values)
      }
   }

   fun showSnackBar() {
      _showSnackBarEvent.value = true
   }
}