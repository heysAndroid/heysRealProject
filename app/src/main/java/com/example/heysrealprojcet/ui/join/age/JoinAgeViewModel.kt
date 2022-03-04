package com.example.heysrealprojcet.ui.join.age

import androidx.lifecycle.*
import com.example.heysrealprojcet.util.UserPreference
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class JoinAgeViewModel : ViewModel() {
   val age = MutableLiveData<String>()

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         age.asFlow().collect {
            UserPreference.age = it
            isCorrect()
         }
      }
   }

   private fun isCorrect() {
      _isEnabled.value = !age.value.isNullOrBlank()
   }
}