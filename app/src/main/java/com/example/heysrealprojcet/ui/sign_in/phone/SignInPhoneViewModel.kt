package com.example.heysrealprojcet.ui.sign_in.phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.heysrealprojcet.model.Phone
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.CheckPhoneNumberResponse
import com.example.heysrealprojcet.repository.SignupRepository
import com.example.heysrealprojcet.ui.base.BaseViewModel
import com.example.heysrealprojcet.util.UserPreference
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

   private var _showSnackBarEvent = MutableLiveData<Boolean>(false)
   val showSnackBarEvent: LiveData<Boolean> = _showSnackBarEvent

   /*
   * 네트워크 호출
    */
   private val _response: MutableLiveData<NetworkResult<CheckPhoneNumberResponse>> = MutableLiveData()
   val response: LiveData<NetworkResult<CheckPhoneNumberResponse>> = _response

   init {
      viewModelScope.launch {
         phoneNumber.asFlow().collect {
            if (phoneNumber.value?.contains('-') == true) {
               UserPreference.phoneNumber = phoneNumber.value?.replace("-", "").toString()
            }
            isElevenDigit()
         }
      }
   }

   private fun isElevenDigit() {
      _isEnabled.value = phoneNumber.value?.length == 13
   }

   fun checkPhoneNumber(phoneNumber: Phone) = viewModelScope.launch {
      signupRepository.checkPhoneNumber(phoneNumber).collect { values ->
         _response.value = values
      }
   }

   fun showSnackBar() {
      _showSnackBarEvent.value = true
   }
}