package com.example.heysrealprojcet.ui.join.password

import android.text.InputType
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heysrealprojcet.util.UserPreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class JoinPasswordViewModel : ViewModel() {
   val password = MutableStateFlow("")
   private val isPasswordVisible = MutableLiveData(true)
   val passwordInputType = MutableLiveData(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)

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
      _isEnabled.value = password.value?.length == 8
   }

   fun togglePasswordVisible() {
      isPasswordVisible.value?.let {
         isPasswordVisible.value = !it
      }

      if (isPasswordVisible.value == true) {
         passwordInputType.value = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
      } else {
         passwordInputType.value = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
      }
   }
}