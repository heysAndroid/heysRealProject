package com.example.heysrealprojcet.ui.channel.dialog.purpose

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heysrealprojcet.enums.ChannelPurpose
import kotlinx.coroutines.flow.MutableStateFlow

class ChannelPurposeDialogViewModel : ViewModel() {
   var selectedView: View? = null

   private val selectMax = 1

   var selectedTotal = MutableStateFlow(1)
   var btnText = ChannelPurpose.Skill.purpose

   private val _selectedPurpose = MutableLiveData<String>()
   val selectedPurpose: LiveData<String> = _selectedPurpose


   fun onClickPurpose2(v: View) {
      val button = v as Button
      _selectedPurpose.value = button.text.toString()
   }

   fun onClickPurpose(v: View) {
      val button = v as Button
      btnText = button.text.toString()
      selectedView?.isSelected = false

      if (selectedTotal.value < selectMax) {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            selectedTotal.value -= 1
         } else {
            selectedView = v
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            selectedTotal.value += 1
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            selectedTotal.value -= 1
         } else {
            selectedView?.isSelected = false
            (selectedView as Button).setTypeface(null, Typeface.NORMAL)
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            selectedView = v
         }
      }
   }
}