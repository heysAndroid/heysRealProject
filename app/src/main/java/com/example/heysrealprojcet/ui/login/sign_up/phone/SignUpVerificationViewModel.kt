package com.example.heysrealprojcet.ui.login.sign_up.phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.heysrealprojcet.model.network.Phone
import com.example.heysrealprojcet.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpVerificationViewModel @Inject constructor(
   private val signupRepository: SignupRepository) : ViewModel() {
   val phoneNumber = MutableLiveData<String>()
   val code = MutableStateFlow("")

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         code.collect {
            checkDigit()
         }
      }
   }

   private fun checkDigit() {
      _isEnabled.value = code.value?.length == 6
   }

   fun postPhoneVerification(phone: Phone) = signupRepository.postPhoneVerification(phone).asLiveData()
}