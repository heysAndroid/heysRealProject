package com.example.heysrealprojcet.ui.channel.dialog.purpose

import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heysrealprojcet.util.ChannelPreference

class ChannelPurposeDialogViewModel : ViewModel() {
   private val _selectedPurpose = MutableLiveData<String>()
   val selectedPurpose: LiveData<String> = _selectedPurpose

   fun onClickPurpose(v: View) {
      val button = v as Button
      _selectedPurpose.value = button.text.toString()
   }
}