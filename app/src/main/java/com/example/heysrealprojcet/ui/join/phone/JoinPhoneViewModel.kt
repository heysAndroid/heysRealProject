package com.example.heysrealprojcet.ui.join.phone

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class JoinPhoneViewModel : ViewModel() {
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
}