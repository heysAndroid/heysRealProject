package com.hey.heys.ui.main.content.contestExtracurricular.filter

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hey.heys.Event
import java.time.LocalDate
import java.time.YearMonth

class ContentsFilterViewModel : ViewModel() {
   private val interestMax = 3

   var selectedDate: LocalDate? = null

   private val _calendarPosition = MutableLiveData(YearMonth.now().month.value)
   val calendarPosition: LiveData<Int> = _calendarPosition

   private val _calendarDate = MutableLiveData<LocalDate>()
   val calendarDate: LiveData<LocalDate> = _calendarDate

   private val _isCalendarInit = MutableLiveData<Event<Boolean>>()
   val isCalendarInit: LiveData<Event<Boolean>> = _isCalendarInit

   private val _selectedInterest = MutableLiveData<ArrayList<String>>(arrayListOf())
   val selectedInterest: LiveData<ArrayList<String>> = _selectedInterest

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
      val text = button.text.toString()

      if (selectedInterest.value?.size!! < interestMax) {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            _selectedInterest.value?.remove(text)
         } else {
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            _selectedInterest.value?.add(text)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            _selectedInterest.value?.remove(text)
         }
      }

      _selectedInterest.apply { postValue(value) }
   }

   fun init() {
      _selectedInterest.value?.clear()

      selectedDate = null
      _calendarDate.value = selectedDate
      _isCalendarInit.value = Event(true)

      _selectedInterest.value?.clear()
   }
}