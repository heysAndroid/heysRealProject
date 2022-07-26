package com.example.heysrealprojcet

import android.view.View
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ChannelPurposeDialogViewModel : ViewModel() {
   private var choicePurpose: View? = null

   private val purposeMax = 1

   private var purposeTotal = MutableStateFlow(0)

   val purposeArray = mutableListOf<String>()

   fun onClickPurpose(v: View) {

      val item = v.tag.toString()

      if (purposeTotal.value < purposeMax) else {
         if (v.isSelected) {
            v.isSelected = false
            purposeTotal.value -= 1
            purposeArray.remove(item)
         }
         else {
            choicePurpose?.isSelected = false
            purposeArray.remove(purposeArray[0])
            v.isSelected = true
            choicePurpose = v
            purposeArray.add(item)
         }
      }
   }
}