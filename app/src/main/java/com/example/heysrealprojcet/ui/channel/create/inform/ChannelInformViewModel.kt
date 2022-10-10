package com.example.heysrealprojcet.ui.channel.create.inform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChannelInformViewModel : ViewModel() {
   private val _isPurpose = MutableLiveData<String>()
   val isPurpose: LiveData<String> = _isPurpose

   init {
      _isPurpose.value = "역량강화"
   }
}