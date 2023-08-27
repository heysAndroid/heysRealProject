package com.example.heysrealprojcet.ui.user.setting.delete

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class SettingWithdrawalViewModel : ViewModel() {
   private var choiceInterest = mutableListOf<View>()
   private val reasonMax = 3
   private var reasonTotal = MutableStateFlow(0)
   private val reasonArray = mutableListOf<String>()

   private val _isEnabled = MutableLiveData(false)
   val isEnabled: LiveData<Boolean> = _isEnabled

   private val _isSelected = MutableLiveData<Boolean>()
   val isSelected: LiveData<Boolean> = _isSelected

   fun onClickReason(v: View) {
      val item = v.tag.toString()

      if (reasonTotal.value < reasonMax) {
         if (v.isSelected) {
            choiceInterest.remove(v)
            v.isSelected = false
            reasonTotal.value -= 1
            reasonArray.remove(item)
         } else {
            choiceInterest.add(v)
            v.isSelected = true
            reasonTotal.value += 1
            reasonArray.add(item)
         }
      } else {
         if (v.isSelected) {
            choiceInterest.remove(v)
            v.isSelected = false
            reasonTotal.value -= 1
            reasonArray.remove(item)
         }
      }
      _isEnabled.value = reasonTotal.value > 0
      _isSelected.value = reasonArray.contains("write")
   }
}