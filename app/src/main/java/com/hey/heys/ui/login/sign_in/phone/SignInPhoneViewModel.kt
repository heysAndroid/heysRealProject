package com.hey.heys.ui.login.sign_in.phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.hey.heys.Event
import com.hey.heys.model.network.Phone
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.response.CheckPhoneNumberResponse
import com.hey.heys.repository.SignupRepository
import com.hey.heys.ui.base.BaseViewModel
import com.hey.heys.util.UserPreference
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
   private val _response: MutableLiveData<com.hey.heys.Event<NetworkResult<CheckPhoneNumberResponse>>> = MutableLiveData()
   val response: LiveData<com.hey.heys.Event<NetworkResult<CheckPhoneNumberResponse>>> = _response

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
         _response.value = com.hey.heys.Event(values)
      }
   }

   fun showSnackBar() {
      _showSnackBarEvent.value = true
   }
}