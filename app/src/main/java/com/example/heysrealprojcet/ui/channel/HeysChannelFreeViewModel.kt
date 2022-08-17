package com.example.heysrealprojcet.ui.channel

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class HeysChannelFreeViewModel: ViewModel() {
   val edtText = MutableLiveData<String>()

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         edtText.asFlow().collect {
            isCorrect()
         }
      }
   }

   private fun isCorrect() {
      _isEnabled.value = !edtText.value.isNullOrBlank()
   }
}