package com.example.heysrealprojcet.ui.channel.create.description

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ChannelDescriptionViewModel : ViewModel() {
   val channelDescription = MutableLiveData<String>()
   val memberDescription = MutableLiveData<String>()
   val link = MutableLiveData<String>()

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   private val _channelDescriptionLetterCount = MutableLiveData(0)
   val channelDescriptionLetterCount: LiveData<Int> = _channelDescriptionLetterCount

   private val _memberDescriptionLetterCount = MutableLiveData(0)
   val memberDescriptionLetterCount: LiveData<Int> = _memberDescriptionLetterCount

   init {
      viewModelScope.launch {
         channelDescription.asFlow().collect {
            isCorrect()
         }
      }
   }

   private fun isCorrect() {
      _isEnabled.value = !channelDescription.value.isNullOrBlank() && !memberDescription.value.isNullOrBlank()
   }

   val channelDescriptionTextWatcher = object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

      }

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         _channelDescriptionLetterCount.value = p0?.length
      }

      override fun afterTextChanged(p0: Editable?) {

      }
   }

   val memberDescriptionTextWatcher = object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

      }

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         _memberDescriptionLetterCount.value = p0?.length
      }

      override fun afterTextChanged(p0: Editable?) {

      }
   }
}