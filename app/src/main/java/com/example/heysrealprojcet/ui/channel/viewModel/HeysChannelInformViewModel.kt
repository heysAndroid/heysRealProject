package com.example.heysrealprojcet.ui.channel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HeysChannelInformViewModel : ViewModel() {
   private val _isPurpose = MutableLiveData<String>()
   val isPurpose: LiveData<String> = _isPurpose

   init {
      _isPurpose.value = "역량강화"
   }
}