package com.hey.heys.ui.user.setting.password

import android.text.InputType
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hey.heys.model.network.Password
import com.hey.heys.repository.MyPageRepository
import com.hey.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingPasswordChangeViewModel @Inject constructor(
   private val myPageRepository: MyPageRepository
) : BaseViewModel() {
   val password = MutableStateFlow("")
   private val isPasswordVisible = MutableLiveData(true)
   val passwordInputType = MutableLiveData(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)

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
      isPasswordVisible.value?.let {
         isPasswordVisible.value = !it
      }

      if (isPasswordVisible.value == true) {
         passwordInputType.value = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
      } else {
         passwordInputType.value = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
      }
   }

   fun changePassword(password: Password) = myPageRepository.changePassword(password).asLiveData()
}