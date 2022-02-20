package com.example.heysrealprojcet.ui.join.phone

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class JoinPhoneViewModel : ViewModel() {
   val phone = MutableLiveData("")
   val isEnabled = MutableStateFlow(false)

   init {
      viewModelScope.launch {
         phone.asFlow().collect {
            isElevenDigit()
         }
      }
   }

   private fun isElevenDigit() {
      isEnabled.value = phone.value?.length == 11
      phone.value?.let { Log.w("aaaaa", it) }
      Log.w("bbbbb", phone.value?.length.toString())
      Log.w("ccccc", isEnabled.value.toString())
   }
}