package com.example.heys.ui.user.setting.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.heys.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SettingPasswordVerificationViewModel : BaseViewModel() {
   val password = MutableStateFlow("")

   private val _isPasswordVisible = MutableLiveData(true)
   val isPasswordVisible: LiveData<Boolean> = _isPasswordVisible

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         password.collect {
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