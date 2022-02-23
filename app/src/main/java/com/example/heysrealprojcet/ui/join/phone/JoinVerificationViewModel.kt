package com.example.heysrealprojcet.ui.join.phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class JoinVerificationViewModel : ViewModel() {
   val phoneNumber = MutableLiveData<String>()
   val verificationNumber = MutableStateFlow("")

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         verificationNumber.collect {
            isCorrectVerificationNumber()
         }
      }
   }

   private fun isCorrectVerificationNumber() {
      _isEnabled.value = verificationNumber.value?.length == 6
   }
}