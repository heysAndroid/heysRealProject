package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.filter

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heysrealprojcet.Event
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.YearMonth

class ContestExtracurricularFilterViewModel : ViewModel() {

   var choiceInterest = mutableListOf<View>()
   private val interestMax = 3
   var interestTotal = MutableStateFlow(0)
   var interestArray = mutableListOf<String>()

   var selectedDate: LocalDate? = null

   private val _calendarPosition = MutableLiveData(YearMonth.now().month.value)
   val calendarPosition: LiveData<Int> = _calendarPosition

   private val _calendarDate = MutableLiveData<LocalDate>()
   val calendarDate: LiveData<LocalDate> = _calendarDate

   private val _isCalendarInit = MutableLiveData<Event<Boolean>>()
   val isCalendarInit: LiveData<Event<Boolean>> = _isCalendarInit

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

      val button = v as Button

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

      selectedDate = null
      _calendarDate.value = selectedDate
      _isCalendarInit.value = Event(true)
   }
}