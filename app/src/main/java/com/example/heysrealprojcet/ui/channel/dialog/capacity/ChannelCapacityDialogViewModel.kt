package com.example.heysrealprojcet.ui.channel.dialog.capacity

import androidx.lifecycle.*
import com.example.heysrealprojcet.enums.ChannelForm
import com.example.heysrealprojcet.util.ChannelPreference
import kotlinx.coroutines.launch

class ChannelCapacityDialogViewModel : ViewModel() {
   var capacity = MutableLiveData<String>()

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   private val _isInRange = MutableLiveData<Boolean>()
   val isInRange: LiveData<Boolean> = _isInRange

   init {
      if (ChannelPreference.channelCapacity != 0) {
         ChannelPreference.channelCapacity?.let { capacity.value = it.toString() }
      }

      viewModelScope.launch {
         capacity.asFlow().collect {
            checkValidate()
         }
      }
   }

   private fun checkValidate() {
      _isEnabled.value = isFilled() && isInRange()
   }

   private fun isFilled(): Boolean {
      return !capacity.value.isNullOrBlank()
   }

   private fun isInRange(): Boolean {
      val intCapacity = capacity.value?.toInt()
      intCapacity?.let {
         if (ChannelPreference.channelForm == ChannelForm.Offline.form) {
            return it <= 50
         } else {
            return it <= 500
         }
      }
      return false
   }
}