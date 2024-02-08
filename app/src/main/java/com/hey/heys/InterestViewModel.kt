package com.hey.heys

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class InterestViewModel : ViewModel() {
   /*
   * 관심 분야
   */
   private val totalMax = 3
   var total = MutableStateFlow(0)
   val totalString = MutableStateFlow("${total.value}/3")
   var interestList = mutableListOf<String>()

   /*
   * 시작하기 버튼
    */
   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   init {
      viewModelScope.launch { total.collect { isSelected() } }
   }

   private fun isSelected() {
      _isEnabled.value = total.value > 0
   }

   fun setInterest(arr: MutableList<String>) {
      interestList = arr
      total.value = interestList.size
   }

   fun onClickInterest(v: View) {
      var button = v as Button

      if (total.value < totalMax) {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            interestList.remove(button.text)
         } else {
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            interestList.add(button.text.toString())
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            interestList.remove(button.text)
         }
      }
      total.value = interestList.size
      totalString.value = "${total.value}/$totalMax"
   }
}