package com.example.heysrealprojcet.ui.channel.dialog.form

import android.view.View
import android.widget.Button
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ChannelFormDialogViewModel : ViewModel() {
   private val _selectedForm = MutableLiveData<String>()
   val selectedForm: LiveData<String> = _selectedForm

   private val _selectedRegion = MutableLiveData<String>()
   val selectedRegion: LiveData<String> = _selectedRegion

   private val _isEnabled = MutableLiveData(false)
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         selectedForm.asFlow().collect {
            isSelected()
         }
      }
   }

   private fun isSelected() {
      _isEnabled.value = !selectedForm.value.isNullOrBlank()
   }

   fun onClickForm(v: View) {
      val button = v as Button
      _selectedForm.value = button.text.toString()
   }

   fun onClickRegion(v: View) {
      val button = v as Button
      _selectedRegion.value = button.text.toString()
   }
}