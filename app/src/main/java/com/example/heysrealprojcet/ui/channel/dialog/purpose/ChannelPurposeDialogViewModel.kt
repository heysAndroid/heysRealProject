package com.example.heysrealprojcet.ui.channel.dialog.purpose

import android.view.View
import android.widget.Button
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ChannelPurposeDialogViewModel : ViewModel() {
   private val _selectedPurpose = MutableLiveData<ArrayList<String>>(arrayListOf())
   val selectedPurpose: LiveData<ArrayList<String>> = _selectedPurpose

   private val _isEnabled = MutableLiveData(false)
   val isEnabled: LiveData<Boolean> = _isEnabled

   private val _selectedNum = MutableLiveData(0)
   val selectedNum: LiveData<Int> = _selectedNum

   init {
      viewModelScope.launch {
         selectedPurpose.asFlow().collect {
            isSelected()
         }
      }
   }

   private fun isSelected() {
      _isEnabled.value = selectedPurpose.value?.size != 0
   }

   fun onClickPurpose(v: View) {
      val button = v as Button
      val text = button.text.toString()

      if (selectedPurpose.value?.contains(text) == true) {
         _selectedPurpose.value?.remove(text)
         _selectedPurpose.value = _selectedPurpose.value
         _selectedNum.value = _selectedNum.value?.minus(1)
      } else {
         if (selectedNum.value!! < 2) {
            _selectedPurpose.value?.add(text)
            _selectedPurpose.value = _selectedPurpose.value
            _selectedNum.value = _selectedNum.value?.plus(1)
         }
      }
   }
}