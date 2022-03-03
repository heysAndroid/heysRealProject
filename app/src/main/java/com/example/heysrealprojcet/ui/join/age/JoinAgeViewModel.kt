package com.example.heysrealprojcet.ui.join.age

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class JoinAgeViewModel : ViewModel() {
   val age = MutableLiveData<String>()

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch { age.asFlow().collect { isCorrect() } }
   }

   private fun isCorrect() {
      _isEnabled.value = !age.value.isNullOrBlank()
   }
}