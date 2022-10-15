package com.example.heysrealprojcet.ui.channel.create.name

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ChannelNameViewModel: ViewModel() {
   var name = MutableLiveData<String>()

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         name.asFlow().collect {
            isCorrect()
         }
      }
   }

   private fun isCorrect() {
      _isEnabled.value = !name.value.isNullOrBlank()
   }
}