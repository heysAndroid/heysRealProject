package com.example.heys.ui.user.myChannel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HeysChannelViewModel : ViewModel() {

   private val _isSelected = MutableLiveData(true)
   val isSelected: LiveData<Boolean> = _isSelected

   fun setMyChannel() {
      _isSelected.value = true
   }

   fun setChannel() {
      _isSelected.value = false
   }
}