package com.example.heysrealprojcet.ui.channel.dialog.purpose

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ChannelPurposeDialogViewModel : ViewModel() {
   var selectedView: View? = null
   private val selectMax = 1
   var selectedTotal = MutableStateFlow(1)
   var btnText = "역량강화"

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