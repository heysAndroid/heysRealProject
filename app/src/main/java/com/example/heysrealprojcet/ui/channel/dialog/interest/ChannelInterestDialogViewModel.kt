package com.example.heysrealprojcet.ui.channel.dialog.interest

import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChannelInterestDialogViewModel : ViewModel() {
   private val _selectedInterest = MutableLiveData<String>()
   val selectedInterest: LiveData<String> = _selectedInterest

   private val _isSelected = MutableLiveData<Boolean>()
   val isSelected: LiveData<Boolean> = _isSelected

   fun onClickInterest(v: View) {
      val button = v as Button
      _selectedInterest.value = button.text.toString()
      _isSelected.value = button.isSelected
   }
}