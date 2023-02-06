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

   private var choiceInterest: View? = null
   private val interestMax = 1
   private var interestTotal = MutableStateFlow(0)
   private val interestArray = mutableListOf<String>()

   var selectedDate: LocalDate? = null

   private val _calendarPosition = MutableLiveData(YearMonth.now().month.value)
   val calendarPosition: LiveData<Int> = _calendarPosition

   private val _calendarDate = MutableLiveData<LocalDate>()
   val calendarDate: LiveData<LocalDate> = _calendarDate

   fun plusPosition() {
      _calendarPosition.value = _calendarPosition.value!! + 1
   }

   fun minusPosition() {
      _calendarPosition.value = _calendarPosition.value!! - 1
   }

   fun setPosition(value: Int) {
      _calendarPosition.value = value
   }

   fun setCalendarDate() {
      _calendarDate.value = selectedDate
   }

   fun onClickInterest(v: View) {
      val item = v.tag.toString()

      var button = v as Button

      if (interestTotal.value < interestMax) {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            interestTotal.value -= 1
            interestArray.remove(item)
         } else {
            choiceInterest = v
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            interestTotal.value += 1
            interestArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            interestTotal.value -= 1
            interestArray.remove(item)
         } else {
            choiceInterest?.isSelected = false
            (choiceInterest as Button).setTypeface(null, Typeface.NORMAL)
            interestArray.remove(interestArray[0])
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            choiceInterest = v
            interestArray.add(item)
         }
      }
   }

   fun onClickInit() {
      if (choiceInterest != null) {
         (choiceInterest as Button).setTypeface(null, Typeface.NORMAL)
      }
      interestTotal.value = 0
      interestArray.clear()

      // TODO 달력, 선택해주세요
   }
}