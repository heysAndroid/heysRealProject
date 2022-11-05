package com.example.heysrealprojcet.ui.channel.dialog.capacity

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ChannelCapacityDialogViewModel : ViewModel() {
   var capacity = MutableLiveData<String>()

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         capacity.asFlow().collect {
            isFilled()
         }
      }
   }

   private fun isFilled() {
      _isEnabled.value = !capacity.value.isNullOrBlank()

   }
}