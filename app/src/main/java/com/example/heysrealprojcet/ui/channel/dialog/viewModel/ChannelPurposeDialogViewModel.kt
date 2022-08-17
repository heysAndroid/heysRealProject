package com.example.heysrealprojcet.ui.channel.dialog.viewModel

import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ChannelPurposeDialogViewModel : ViewModel() {
   var selectedView: View? = null
   private val selectMax = 1
   private var selectedTotal = MutableStateFlow(0)
   var btnText = "역량강화"

   fun onClickPurpose(v: View) {
      val button = v as Button
      btnText = button.text.toString()
      selectedView?.isSelected = false

      if (selectedTotal.value < selectMax) {
         if (v.isSelected) {
            v.isSelected = false
            selectedTotal.value -= 1
         } else {
            selectedView = v
            v.isSelected = true
            selectedTotal.value += 1
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            selectedTotal.value -= 1
         } else {
            selectedView?.isSelected = false
            v.isSelected = true
            selectedView = v
         }
      }
   }
}