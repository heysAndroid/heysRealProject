package com.example.heys.ui.channel.create.description

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ChannelDescriptionViewModel : ViewModel() {
   val channelActivity = MutableLiveData<String>()
   val channelMember = MutableLiveData<String>()

   val link1 = MutableLiveData<String>()
   val link2 = MutableLiveData<String>()
   val linkCount = MutableLiveData(0)

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   private val _channelActivityLetterCount = MutableLiveData(0)
   val channelActivityLetterCount: LiveData<Int> = _channelActivityLetterCount

   private val _channelMemberLetterCount = MutableLiveData(0)
   val channelMemberLetterCount: LiveData<Int> = _channelMemberLetterCount

   init {
      // collect{} 는 한 viewmodelScope 에 하나씩
      viewModelScope.launch { channelActivity.asFlow().collect { isAllFilled() } }
      viewModelScope.launch { channelMember.asFlow().collect { isAllFilled() } }
      viewModelScope.launch {
         link1.asFlow().collect {
            isAllFilled()
            isLink1Filled()
         }
      }
      viewModelScope.launch { link2.asFlow().collect { isLink2Filled() } }
   }

   private fun isAllFilled() {
      _isEnabled.value = !channelActivity.value.isNullOrBlank()
              && channelActivityLetterCount.value!! >= 30
              && !channelMember.value.isNullOrBlank()
              && channelMemberLetterCount.value!! >= 30
              && !link1.value.isNullOrBlank()
   }

   private fun isLink1Filled() {
      if (!link1.value.isNullOrBlank() && linkCount.value == 0) {
         linkCount.value = linkCount.value?.plus(1)
      }
      if (link1.value.isNullOrBlank() && linkCount.value == 1) {
         linkCount.value = linkCount.value?.minus(1)
      }
   }

   private fun isLink2Filled() {
      if (!link2.value.isNullOrBlank() && linkCount.value == 1) {
         linkCount.value = linkCount.value?.plus(1)
      }
      if (link2.value.isNullOrBlank() && linkCount.value == 2) {
         linkCount.value = linkCount.value?.minus(1)
      }
   }

   val channelActivityTextWatcher = object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         _channelActivityLetterCount.value = p0?.length
      }

      override fun afterTextChanged(p0: Editable?) {}
   }

   val channelMemberTextWatcher = object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         _channelMemberLetterCount.value = p0?.length
      }

      override fun afterTextChanged(p0: Editable?) {}
   }
}