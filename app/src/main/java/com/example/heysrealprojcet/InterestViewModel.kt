package com.example.heysrealprojcet

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heysrealprojcet.util.UserPreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class InterestViewModel : ViewModel() {
   /*
   * 관심 분야
   */
   private val totalMax = 3
   private var total = MutableStateFlow(0)
   val totalString = MutableStateFlow("${total.value}/3")
   val interestList = mutableListOf<String>()

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

   fun onClickInterest(v: View) {
      var button = v as Button

      if (total.value < totalMax) {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            total.value -= 1
            interestList.remove(button.text)
         } else {
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            total.value += 1
            interestList.add(button.text.toString())
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            total.value -= 1
            interestList.remove(button.text)
         }
      }
      totalString.value = "${total.value}/$totalMax"
      UserPreference.interests = Json.encodeToString(interestList)
   }
}