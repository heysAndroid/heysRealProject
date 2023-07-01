package com.example.heysrealprojcet.ui.channel.dialog.interest

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.heysrealprojcet.util.ChannelPreference
import kotlinx.coroutines.launch

class ChannelInterestDialogViewModel : ViewModel() {
   val interestMax = 3
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

      if (selectedInterest.value?.size!! < interestMax) {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            _selectedInterest.value?.remove(text)
         } else {
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            _selectedInterest.value?.add(text)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            _selectedInterest.value?.remove(text)
         }
      }
      _selectedInterest.apply { postValue(value) }
   }
}