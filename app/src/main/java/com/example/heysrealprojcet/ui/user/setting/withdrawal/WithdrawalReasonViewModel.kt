package com.example.heysrealprojcet.ui.user.setting.withdrawal

import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WithdrawalReasonViewModel : ViewModel() {
   val reason = MutableLiveData<String>()

   private val _isEnabled = MutableLiveData(false)
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         reason.asFlow().collect { isSelect() }
      }
   }

   fun onClickReason(v: View) {
      val b = v as RadioButton
      reason.value = b.text.toString()
   }

   private fun isSelect() {
      _isEnabled.value = !reason.value.isNullOrBlank()
   }
}
