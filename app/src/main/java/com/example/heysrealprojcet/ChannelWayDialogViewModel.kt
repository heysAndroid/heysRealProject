package com.example.heysrealprojcet

import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ChannelWayDialogViewModel: ViewModel() {

   var choiceWay: View? = null

   private val wayMax = 1

   private var wayTotal = MutableStateFlow(0)

   private val wayArray = mutableListOf<String>()

   var btnText = "승인 결정"

   fun onClickWay(v: View) {
      val button = v as Button
      btnText = button.text.toString()

      choiceWay?.isSelected = false
      val item = v.tag.toString()

      if (wayTotal.value < wayMax) {
         if (v.isSelected) {
            v.isSelected = false
            wayTotal.value -= 1
            wayArray.remove(item)
         } else {
            choiceWay = v
            v.isSelected = true
            wayTotal.value += 1
            wayArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            wayTotal.value -= 1
            wayArray.remove(item)
         }
         else {
            choiceWay?.isSelected = false
            wayArray.remove(wayArray[0])
            v.isSelected = true
            choiceWay = v
            wayArray.add(item)
         }
      }
   }
}