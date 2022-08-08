package com.example.heysrealprojcet

import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ChannelPurposeDialogViewModel : ViewModel() {

   var choicePurpose: View? = null

   private val purposeMax = 1

   private var purposeTotal = MutableStateFlow(0)

   private val purposeArray = mutableListOf<String>()

   var btnText = "역량강화"

   fun onClickPurpose(v: View) {
      val button = v as Button
      btnText = button.text.toString()

      choicePurpose?.isSelected = false
      val item = v.tag.toString()

      if (purposeTotal.value < purposeMax) {
         if (v.isSelected) {
            v.isSelected = false
            purposeTotal.value -= 1
            purposeArray.remove(item)
         } else {
            choicePurpose = v
            v.isSelected = true
            purposeTotal.value += 1
            purposeArray.add(item)
         }
      } else {
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