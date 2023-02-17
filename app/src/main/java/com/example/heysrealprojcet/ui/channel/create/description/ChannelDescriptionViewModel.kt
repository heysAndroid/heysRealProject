package com.example.heysrealprojcet.ui.channel.create.description

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ChannelDescriptionViewModel : ViewModel() {
   val channelDescription = MutableLiveData<String>()
   val memberDescription = MutableLiveData<String>()

   val link1 = MutableLiveData<String>()
   val link2 = MutableLiveData<String>()
   val linkCount = MutableLiveData(0)

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   private val _channelDescriptionLetterCount = MutableLiveData(0)
   val channelDescriptionLetterCount: LiveData<Int> = _channelDescriptionLetterCount

   private val _memberDescriptionLetterCount = MutableLiveData(0)
   val memberDescriptionLetterCount: LiveData<Int> = _memberDescriptionLetterCount

   init {
      // collect{} 는 한 viewmodelScope 에 하나씩
      viewModelScope.launch { channelDescription.asFlow().collect { isAllFilled() } }
      viewModelScope.launch { memberDescription.asFlow().collect { isAllFilled() } }
      viewModelScope.launch {
         link1.asFlow().collect {
            isAllFilled()
            isLink1Filled()
         }
      }
      viewModelScope.launch { link2.asFlow().collect { isLink2Filled() } }
   }

   private fun isAllFilled() {
      _isEnabled.value = !channelDescription.value.isNullOrBlank()
              && channelDescriptionLetterCount.value!! >= 30
              && !memberDescription.value.isNullOrBlank()
              && memberDescriptionLetterCount.value!! >= 30
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

   val channelDescriptionTextWatcher = object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         _channelDescriptionLetterCount.value = p0?.length
      }

      override fun afterTextChanged(p0: Editable?) {}
   }

   val memberDescriptionTextWatcher = object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         _memberDescriptionLetterCount.value = p0?.length
      }

      override fun afterTextChanged(p0: Editable?) {}
   }
}