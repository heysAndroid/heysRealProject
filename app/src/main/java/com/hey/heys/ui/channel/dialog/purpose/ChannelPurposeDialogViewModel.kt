package com.hey.heys.ui.channel.dialog.purpose

import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.hey.heys.util.ChannelPreference
import kotlinx.coroutines.launch

class ChannelPurposeDialogViewModel : ViewModel() {
   private val _selectedPurpose = MutableLiveData<ArrayList<String>>(arrayListOf())
   val selectedPurpose: LiveData<ArrayList<String>> = _selectedPurpose

   private val _isEnabled = MutableLiveData(false)
   val isEnabled: LiveData<Boolean> = _isEnabled

   private val _selectedNum = MutableLiveData(ChannelPreference.channelPurposeArray.size)
   val selectedNum: LiveData<Int> = _selectedNum

   init {
      viewModelScope.launch {
         selectedPurpose.asFlow().collect {
            isSelected()
         }
      }
      _selectedPurpose.value = ChannelPreference.channelPurposeArray
   }

   private fun isSelected() {
      _isEnabled.value = selectedPurpose.value?.size != 0
   }

   fun onClickPurpose(v: View) {
      val button = v as Button
      val text = button.text.toString()

      if (selectedPurpose.value?.contains(text) == true) {
         _selectedPurpose.value?.remove(text)
         _selectedPurpose.apply { postValue(value) }
      } else {
         if (selectedNum.value!! < 2) {
            _selectedPurpose.value?.add(text)
            _selectedPurpose.apply { postValue(value) }
         }
      }
      _selectedNum.postValue(_selectedPurpose.value?.size)
   }
}