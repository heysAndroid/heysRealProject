package com.example.heysrealprojcet.ui.channel.dialog.recruitmentMethod

import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ChannelRecruitmentMethodDialogViewModel : ViewModel() {
   var selectedView: View? = null
   private val wayMax = 1
   private var wayTotal = MutableStateFlow(1)
   var btnText = "승인 결정"

   fun onClickWay(v: View) {
      val button = v as Button
      btnText = if (button.text.toString().contains("바로")) {
         "바로 승인"
      } else {
         "승인 결정"
      }
      selectedView?.isSelected = false

      if (wayTotal.value < wayMax) {
         if (v.isSelected) {
            v.isSelected = false
            wayTotal.value -= 1
         } else {
            selectedView = v
            v.isSelected = true
            wayTotal.value += 1
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            wayTotal.value -= 1
         } else {
            selectedView?.isSelected = false
            v.isSelected = true
            selectedView = v
         }
      }
   }
}