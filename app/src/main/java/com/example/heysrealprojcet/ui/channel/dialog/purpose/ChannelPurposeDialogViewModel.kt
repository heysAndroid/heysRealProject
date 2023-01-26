package com.example.heysrealprojcet.ui.channel.dialog.purpose

import android.view.View
import android.widget.Button
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ChannelPurposeDialogViewModel : ViewModel() {
   private val _selectedPurpose = MutableLiveData<String>()
   val selectedPurpose: LiveData<String> = _selectedPurpose

   private val _isEnabled = MutableLiveData(false)
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         selectedPurpose.asFlow().collect {
            isSelected()
         }
      }
   }

   private fun isSelected() {
      _isEnabled.value = !selectedPurpose.value.isNullOrBlank()

   }

   fun onClickPurpose(v: View) {
      val button = v as Button
      _selectedPurpose.value = button.text.toString()
   }
}