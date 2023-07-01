package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.filter

import android.graphics.Typeface
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heysrealprojcet.Event
import java.time.LocalDate
import java.time.YearMonth

class ContestExtracurricularFilterViewModel : ViewModel() {
   private val interestMax = 3
   var selectedView = mutableListOf<View>()
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
      val button = v as Button
      val item = button.text.toString()

      if (interestArray.size < interestMax) {
         // 이미 선택된 경우
         if (v.isSelected) {
            selectedView.remove(v)
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            interestArray.remove(item)
         } else {
            // 선택되지 않은 경우
            selectedView.add(v)
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            interestArray.add(item)
         }
      } else {
         if (v.isSelected) {
            selectedView.remove(v)
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            interestArray.remove(item)
         }
      }

      interestArray.forEach {
         Log.w("clicked interest: ", it)
      }
   }

   fun onClickInit() {
      for (i in selectedView.indices) {
         selectedView[i].isSelected = false
         (selectedView[i] as Button).setTypeface(null, Typeface.NORMAL)
      }
      interestArray.clear()
      selectedView.clear()

      selectedDate = null
      _calendarDate.value = selectedDate
      _isCalendarInit.value = Event(true)
   }
}