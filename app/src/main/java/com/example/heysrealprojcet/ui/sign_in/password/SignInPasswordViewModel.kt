package com.example.heysrealprojcet.ui.sign_in.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heysrealprojcet.util.UserPreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SignInPasswordViewModel : ViewModel() {
   val password = MutableStateFlow("")

   private val _isPasswordVisible = MutableLiveData(true)
   val isPasswordVisible: LiveData<Boolean> = _isPasswordVisible

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         password.collect {
            UserPreference.password = it
            isCorrect()
         }
      }
   }

   private fun isCorrect() {
      _isEnabled.value = password.value?.length!! >= 8
   }

   fun togglePasswordVisible() {
      _isPasswordVisible.value?.let {
         _isPasswordVisible.value = !it
      }
   }
}