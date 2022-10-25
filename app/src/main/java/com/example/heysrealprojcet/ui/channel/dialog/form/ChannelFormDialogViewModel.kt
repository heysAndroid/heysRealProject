package com.example.heysrealprojcet.ui.channel.dialog.form

import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChannelFormDialogViewModel : ViewModel() {
   private val _selectedForm = MutableLiveData<String>()
   val selectedForm: LiveData<String> = _selectedForm

   private val _selectedRegion = MutableLiveData<String>()
   val selectedRegion: LiveData<String> = _selectedRegion

   fun onClickForm(v: View) {
      val button = v as Button
      _selectedForm.value = button.text.toString()
   }

   fun onClickRegion(v: View) {
      val button = v as Button
      _selectedRegion.value = button.text.toString()
   }
}