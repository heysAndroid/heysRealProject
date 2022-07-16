package com.example.heysrealprojcet

import android.view.View
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class HeysChannelTemplateViewModel: ViewModel() {
   private var choiceform: View? = null

   private val formMax = 1

   private var formTotal = MutableStateFlow(0)

   private val formArray = mutableListOf<String>()

   fun onClickForm(v: View) {
      val item = v.tag.toString()

      if(formTotal.value < formMax) {
         if(v.isSelected) {
            v.isSelected = false
            formTotal.value -= 1
            formArray.remove(item)
         } else {
            choiceform = v
            v.isSelected = true
            formTotal.value += 1
            formArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            formTotal.value -= 1
            formArray.remove(item)
         } else {
            choiceform?.isSelected =false
            formArray.remove(formArray[0])
            v.isSelected = true
            choiceform = v
            formArray.add(item)
         }
      }
   }
}