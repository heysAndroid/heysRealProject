package com.example.heysrealprojcet.ui.join.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class JoinPasswordViewModel : ViewModel() {
   val password = MutableStateFlow("")
   val description = MutableStateFlow("아직 8자리가 아니에요")

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch { password.collect { isCorrect() } }
   }

   private fun isCorrect() {
      if (password.value?.length == 8) {
         _isEnabled.value = true
         description.value = "알맞은 비밀번호에요"
      }
   }


}