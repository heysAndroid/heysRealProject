package com.example.heysrealprojcet.ui.join.phone

import androidx.lifecycle.*
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class JoinPhoneViewModel : ViewModel() {
   val phoneNumber = MutableLiveData("")

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

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
}