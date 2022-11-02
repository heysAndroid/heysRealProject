package com.example.heysrealprojcet.ui.channel.dialog.period

import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChannelPeriodDialogViewModel : ViewModel() {
   private val _selectedPeriod = MutableLiveData<String>()
   val selectedPeriod: LiveData<String> = _selectedPeriod

   fun onClickPeriod(v: View) {
      val button = v as Button
      _selectedPeriod.value = button.text.toString()
   }
}