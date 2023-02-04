package com.example.heysrealprojcet.ui.main.content.contestActivity.filter

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.YearMonth

class ContestActivityFilterViewModel : ViewModel() {

   private var choiceInterest = mutableListOf<View>()
   private val interestMax = 3
   private var interestTotal = MutableStateFlow(0)
   private val interestArray = mutableListOf<String>()

   var selectedDate: LocalDate? = null

   private val _calendarPosition = MutableLiveData(YearMonth.now().month.value)
   val calendarPosition: LiveData<Int> = _calendarPosition

   fun plusPosition() {
      _calendarPosition.value = _calendarPosition.value!! + 1
   }

   fun minusPosition() {
      _calendarPosition.value = _calendarPosition.value!! - 1
   }

   fun setPosition(value: Int) {
      _calendarPosition.value = value
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
   }

   fun onClickInit() {
      for (i in choiceInterest.indices) {
         choiceInterest[i].isSelected = false
         (choiceInterest[i] as Button).setTypeface(null, Typeface.NORMAL)
      }
      interestTotal.value = 0
      choiceInterest.clear()

      // TODO 달력, 선택해주세요
   }
}