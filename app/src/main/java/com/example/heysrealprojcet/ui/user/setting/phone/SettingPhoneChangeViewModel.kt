package com.example.heysrealprojcet.ui.user.setting.phone

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class SettingPhoneChangeViewModel : ViewModel() {
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
      _isEnabled.value = phoneNumber.value?.length == 13
   }
}