package com.example.heysrealprojcet.ui.channel.create.interest

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ChannelInterestViewModel : ViewModel() {

   private val _isEnabled = MutableLiveData<Boolean>()
   val isEnabled: LiveData<Boolean> = _isEnabled

   private var choiceInterest = mutableListOf<View>()
   private val interestMax = 3
   private var interestTotal = MutableStateFlow(0)
   val interestString = MutableStateFlow("${interestTotal.value}/3")
   private val interestArray = mutableListOf<String>()

   init {
      viewModelScope.launch { interestTotal.collect { isSelected() } }
   }

   private fun isSelected() {
      _isEnabled.value = interestTotal.value > 0
   }

   fun onClickInterest(v: View) {
      val item = v.tag.toString()

      var button = v as Button

      if (interestTotal.value < interestMax) {
         if (v.isSelected) {
            choiceInterest.remove(v)
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            interestTotal.value -= 1
            interestArray.remove(item)
         } else {
            choiceInterest.add(v)
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            interestTotal.value += 1
            interestArray.add(item)
         }
      } else {
         if (v.isSelected) {
            choiceInterest.remove(v)
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            interestTotal.value -= 1
            interestArray.remove(item)
         }
      }
      interestString.value = "${interestTotal.value}/3"
   }
}