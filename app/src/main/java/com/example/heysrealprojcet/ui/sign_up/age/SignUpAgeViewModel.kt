package com.example.heysrealprojcet.ui.sign_up.age

import androidx.lifecycle.*
import com.example.heysrealprojcet.util.UserPreference
import kotlinx.coroutines.launch

class SignUpAgeViewModel : ViewModel() {
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