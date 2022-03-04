package com.example.heysrealprojcet.ui.join.name

import androidx.lifecycle.*
import com.example.heysrealprojcet.util.UserPreference
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class JoinNameViewModel : ViewModel() {
   val name = MutableLiveData<String>()

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         name.asFlow().collect {
            UserPreference.name = it
            isCorrect()
         }
      }
   }

   private fun isCorrect() {
      _isEnabled.value = !name.value.isNullOrBlank()
   }
}