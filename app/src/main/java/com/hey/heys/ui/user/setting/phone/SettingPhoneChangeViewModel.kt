package com.hey.heys.ui.user.setting.phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hey.heys.model.network.Phone
import com.hey.heys.repository.SignupRepository
import com.hey.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingPhoneChangeViewModel @Inject constructor(
   private val signupRepository: SignupRepository
) : BaseViewModel() {
   val phoneNumber = MutableLiveData("")

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         phoneNumber.asFlow().collect {
            isElevenDigit()
         }
      }
   }

   private fun isElevenDigit() {
      _isEnabled.value = phoneNumber.value?.length == 11
   }

   fun checkPhoneNumber(phone: Phone) = signupRepository.checkPhoneNumber(phoneNumber = phone).asLiveData()
   fun postPhoneVerification(phone: Phone) = signupRepository.postPhoneVerification(phone).asLiveData()
}