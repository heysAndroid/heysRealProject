package com.example.heysrealprojcet

import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ChannelFormDialogViewModel: ViewModel() {

   var choiceForm: View? = null

   private val formMax = 1

   private var formTotal = MutableStateFlow(0)

   private val formArray = mutableListOf<String>()

   var btnText = "온, 오프라인"

   fun onClickForm(v: View) {
      val button = v as Button
      btnText = button.text.toString()

      choiceForm?.isSelected = false
      val item = v.tag.toString()

      if (formTotal.value < formMax) {
         if (v.isSelected) {
            v.isSelected = false
            formTotal.value -= 1
            formArray.remove(item)
         } else {
            choiceForm = v
            v.isSelected = true
            formTotal.value += 1
            formArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            formTotal.value -= 1
            formArray.remove(item)
         }
         else {
            choiceForm?.isSelected = false
            formArray.remove(formArray[0])
            v.isSelected = true
            choiceForm = v
            formArray.add(item)
         }
      }
   }
}