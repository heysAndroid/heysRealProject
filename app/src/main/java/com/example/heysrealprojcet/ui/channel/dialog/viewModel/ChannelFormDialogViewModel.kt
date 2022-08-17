package com.example.heysrealprojcet.ui.channel.dialog.viewModel

import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ChannelFormDialogViewModel : ViewModel() {
   var selectedView: View? = null
   private val formMax = 1
   private var formTotal = MutableStateFlow(0)
   var btnText = "온, 오프라인"

   fun onClickForm(v: View) {
      val button = v as Button
      btnText = button.text.toString()
      selectedView?.isSelected = false

      if (formTotal.value < formMax) {
         if (v.isSelected) {
            v.isSelected = false
            formTotal.value -= 1
         } else {
            selectedView = v
            v.isSelected = true
            formTotal.value += 1
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            formTotal.value -= 1
         } else {
            selectedView?.isSelected = false
            v.isSelected = true
            selectedView = v
         }
      }
   }
}