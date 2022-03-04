package com.example.heysrealprojcet.ui.join.interest

import android.view.View
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class JoinInterestViewModel : ViewModel() {
   private val totalMax = 3
   private var total = MutableStateFlow(0)
   val totalString = MutableStateFlow("${total.value}/3")
   private val interestList = mutableListOf<String>()

   fun onClickInterest(v: View) {
      val item = v.tag.toString()

      if (total.value < totalMax) {
         if (v.isSelected) {
            v.isSelected = false
            total.value -= 1
            interestList.remove(item)
         } else {
            v.isSelected = true
            total.value += 1
            interestList.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            total.value -= 1
            interestList.remove(item)
         }
      }
      totalString.value = "${total.value}/$totalMax"
   }
}