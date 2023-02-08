package com.example.heysrealprojcet.ui.channel.dialog.interest

import android.view.View
import android.widget.Button
import androidx.lifecycle.*
import com.example.heysrealprojcet.util.ChannelPreference
import kotlinx.coroutines.launch

class ChannelInterestDialogViewModel : ViewModel() {
   private val _selectedInterest = MutableLiveData<ArrayList<String>>()
   val selectedInterest: LiveData<ArrayList<String>> = _selectedInterest

   private val _isEnabled = MutableLiveData(false)
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch {
         selectedInterest.asFlow().collect {
            isSelected()
         }
      }
      _selectedInterest.value = ChannelPreference.channelInterestArray
   }

   private fun isSelected() {
      _isEnabled.value = selectedInterest.value?.size != 0
   }

   fun onClickInterest(v: View) {
      val button = v as Button
      val text = button.text.toString()

      if (selectedInterest.value?.contains(text) == true) {
         _selectedInterest.value?.remove(text)
         _selectedInterest.apply { postValue(value) }
      } else {
         _selectedInterest.value?.add(text)
         _selectedInterest.apply { postValue(value) }
      }
   }
}