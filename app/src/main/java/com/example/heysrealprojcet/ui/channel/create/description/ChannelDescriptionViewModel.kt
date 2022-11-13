package com.example.heysrealprojcet.ui.channel.create.description

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ChannelDescriptionViewModel : ViewModel() {
   val edtText = MutableLiveData<String>()

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   private val _letterCount = MutableLiveData(0)
   val letterCount: LiveData<Int> = _letterCount

   init {
      viewModelScope.launch {
         edtText.asFlow().collect {
            isCorrect()
         }
      }
   }

   private fun isCorrect() {
      _isEnabled.value = !edtText.value.isNullOrBlank()
   }

   val textWatcher = object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

      }

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
         _letterCount.value = p0?.length
      }

      override fun afterTextChanged(p0: Editable?) {

      }
   }
}