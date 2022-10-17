package com.example.heysrealprojcet.ui.channel.dialog.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChannelCapacityDialogViewModel : ViewModel() {
   private val _capacity = MutableLiveData(50)
   val capacity: LiveData<Int> = _capacity

   fun updateCapacity(value: Int) {
      _capacity.value = value
   }
}