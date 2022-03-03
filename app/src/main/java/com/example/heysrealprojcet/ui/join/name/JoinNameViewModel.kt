package com.example.heysrealprojcet.ui.join.name

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class JoinNameViewModel : ViewModel() {
   val name = MutableLiveData<String>()

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch { name.asFlow().collect { isCorrect() } }
   }

   private fun isCorrect() {
      if (!name.value.isNullOrBlank()) {
         _isEnabled.value = true
      }
   }
}