package com.example.heys.ui.channel.dialog.recruitmentMethod

import android.view.View
import android.widget.Button
import androidx.lifecycle.*
import com.example.heys.enums.ChannelRecruitmentMethod
import kotlinx.coroutines.launch

class ChannelRecruitmentMethodDialogViewModel : ViewModel() {
   private val _selectedRecruitmentMethod = MutableLiveData<String>()
   val selectedRecruitmentMethod: LiveData<String> = _selectedRecruitmentMethod

   private val _isEnabled = MutableLiveData(false)
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         selectedRecruitmentMethod.asFlow().collect {
            isSelected()
         }
      }
   }

   private fun isSelected() {
      _isEnabled.value = !selectedRecruitmentMethod.value.isNullOrBlank()
   }

   fun onClickMethod(v: View) {
      val button = v as Button
      val buttonText = button.text.toString()
      if (buttonText.contains(ChannelRecruitmentMethod.Approval.method)) {
         _selectedRecruitmentMethod.value = ChannelRecruitmentMethod.Approval.method
      } else {
         _selectedRecruitmentMethod.value = ChannelRecruitmentMethod.Immediately.method
      }
   }
}